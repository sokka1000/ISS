package server;

import bibliotecamodel.*;
import persistence.RepositoryAbonat;
import persistence.RepositoryBibliotecar;
import persistence.RepositoryCarte;
import persistence.RepositoryImprumut;
import persistence.database.CarteORMRepository;
import services.BibliotecaException;
import services.IBibliotecaObserver;
import services.IBibliotecaServices;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceImplementation implements IBibliotecaServices {

    private RepositoryBibliotecar bibliotecarRepository;
    private RepositoryAbonat abonatRepository;
    private RepositoryCarte carteRepository;
    private RepositoryImprumut imprumutRepository;

    private Map<Integer,IBibliotecaObserver> bibliotecariLogati;
    private Map<Integer,IBibliotecaObserver> abonatiLogati;

    public ServiceImplementation(RepositoryBibliotecar bibliotecarRepository, RepositoryAbonat abonatRepository,
                                 RepositoryCarte carteRepository, RepositoryImprumut imprumutRepository) {

        this.bibliotecarRepository = bibliotecarRepository;
        this.abonatRepository = abonatRepository;
        this.carteRepository = carteRepository;
        this.imprumutRepository = imprumutRepository;

        bibliotecariLogati = new ConcurrentHashMap<>();
        abonatiLogati = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void loginB(String username, String parola, IBibliotecaObserver obs) throws BibliotecaException {
        Bibliotecar bibliotecarR = bibliotecarRepository.findBibliotecarByUsernameParola(username,
                parola);

        if(bibliotecarR != null) {
            if(bibliotecariLogati.get(bibliotecarR.getId()) != null){
                throw new BibliotecaException("Bibliotecarul deja s-a logat!");
            }
            bibliotecariLogati.put(bibliotecarR.getId(),obs);

        }else{
            throw new BibliotecaException("Autentificare esuata!");
        }
    }

    @Override
    public synchronized void logoutB(Bibliotecar bibliotecar, IBibliotecaObserver client) throws BibliotecaException {
        bibliotecariLogati.remove(bibliotecar.getId());
    }

    @Override
    public synchronized void loginA(String username, String parola, IBibliotecaObserver obs) throws BibliotecaException {
        Abonat abonatR = abonatRepository.findAbonatByUsernameParola(username,
                parola);

        if(abonatR != null) {
            if(abonatiLogati.get(abonatR.getId()) != null){
                throw new BibliotecaException("Abonatul deja s-a logat!");
            }
            abonatiLogati.put(abonatR.getId(),obs);

        }else{
            throw new BibliotecaException("Autentificare esuata!");
        }

    }

    @Override
    public synchronized Abonat abonatConectat(String username, String parola) {
        return abonatRepository.findAbonatByUsernameParola(username,parola);
    }

    @Override
    public synchronized void logoutA(Abonat abonat, IBibliotecaObserver client) throws BibliotecaException {
        abonatiLogati.remove(abonat.getId());
    }

    @Override
    public synchronized List<Carte> getToateCartile() {
        return carteRepository.findAll();
    }

    @Override
    public synchronized List<Carte> getToateCartileDisponibile() {
        return carteRepository.toateCartileDisponibile();
    }

    @Override
    public synchronized List<CarteDTO> getToateCartileImprumutate(int idAbonat) {
        List<CarteDTO> listaCartiDTO = new ArrayList<>();
        List<Imprumut> impumuturiActive = imprumutRepository.getImprumuturiActiveAbonat(idAbonat);
        impumuturiActive.forEach(imprumut->{
            Carte carte = carteRepository.findById(imprumut.getIdCarte());
            CarteDTO carteDTO = new CarteDTO(carte.getTitlu(), carte.getAutor(), carte.getDisponibila(), imprumut.getDataImprumut());
            carteDTO.setId(carte.getId());
            listaCartiDTO.add(carteDTO);
        });
        return listaCartiDTO;
    }

    @Override
    public synchronized void adaugaCarte(String titlu, String autor) throws Exception {
        Carte carteNoua = new Carte(titlu,autor,true);
        carteRepository.add(carteNoua);

        notifyCarteUpdated();
    }

    @Override
    public void imprumutaCarte(int idCarte, int idAbonat) throws Exception {
        Imprumut imprumut = new Imprumut(idCarte, idAbonat, new Date(), false); //nu au cum sa fie null: cartea e selectata, iar abonat e cel logat
        imprumutRepository.add(imprumut);

        Carte carte = carteRepository.findById(idCarte);
        modificaCarte(idCarte, carte.getTitlu(), carte.getAutor(), false);

        notifyImprumutUpdated();
    }

    @Override
    public void returneazaCarte(int idCarte, String usernameAbonat) throws BibliotecaException {
        //verifcare ca exista cartea si abonatul
        Carte carte = carteRepository.findById(idCarte);
        Abonat abonat = abonatRepository.findAbonatByUsername(usernameAbonat);

        String err = "";
        if (carte == null){
            err += "Cod carte inexistent!\n";
        }

        if (abonat == null){
            err += "Username abonat inexistent!\n";
        }

        if (err.length() > 0){
            throw new BibliotecaException(err);
        } else{
            //verificare acel imprumut exista ACTIV
            Imprumut imprumutActiv = imprumutRepository.getImprumutActivCarteAbonat(idCarte, abonat.getId());
            if(imprumutActiv == null){
                throw new BibliotecaException("Nu exista imprumut ACTIV intre datele introduse!\n");
            }else{
                imprumutRepository.update(imprumutActiv); //returnat = true

                carte.setDisponibila(true);
                carteRepository.update(carte);

                notifyImprumutUpdated();
            }
        }

    }

    @Override
    public String infosImprumut(Carte carteSelectata) {
        String string = "INFO:" + "\n";

        Imprumut imprumut = imprumutRepository.getImprumutActivCarte(carteSelectata.getId());
        Abonat abonat = abonatRepository.findById(imprumut.getIdAbonat());
        string += abonat;
        List<Imprumut> listaImprumuturi = imprumutRepository.getImprumuturiActiveAbonat(imprumut.getIdAbonat());

        string += "Lista cartilor imprumutate: \n";

        for (Imprumut x : listaImprumuturi){
            Carte carte = carteRepository.findById(x.getIdCarte());
            string += carte + "\n";
        }

        return string;
    }

    @Override
    public void modificaCarte(Integer id, String titlu, String autor, Boolean stare) throws Exception {
        Carte carteNoua = new Carte(titlu,autor,stare);
        carteNoua.setId(id);
        carteRepository.update(carteNoua);

        notifyCarteUpdated();
    }

    @Override
    public void stergeCarte(Integer id) {
        carteRepository.delete(id);

        notifyCarteUpdated();
    }

    private final int defaultThreadsNo=5;

    private void notifyCarteUpdated() {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(var o: bibliotecariLogati.entrySet()) {
            executor.execute(() -> {
                try {
                    o.getValue().carteUpdated();
                } catch (BibliotecaException | RemoteException e) {
                    System.err.println("Error notifying bibliotecar adaugare carte " + e);
                }
            });
        }
        for(var o: abonatiLogati.entrySet()) {
            executor.execute(() -> {
                try {
                    o.getValue().carteUpdated();
                } catch (BibliotecaException | RemoteException e) {
                    System.err.println("Error notifying abonat adaugare carte " + e);
                }
            });
        }
        executor.shutdown();
    }

    private void notifyImprumutUpdated() {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(var o: bibliotecariLogati.entrySet()) {
            executor.execute(() -> {
                try {
                    o.getValue().imprumutUpdated();
                } catch (BibliotecaException | RemoteException e) {
                    System.err.println("Error notifying bibliotecar adaugare carte " + e);
                }
            });
        }
        for(var o: abonatiLogati.entrySet()) {
            executor.execute(() -> {
                try {
                    o.getValue().imprumutUpdated();
                } catch (BibliotecaException | RemoteException e) {
                    System.err.println("Error notifying abonat adaugare carte " + e);
                }
            });
        }
        executor.shutdown();
    }


}