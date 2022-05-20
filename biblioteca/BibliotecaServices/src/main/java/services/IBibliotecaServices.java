package services;

import bibliotecamodel.Abonat;
import bibliotecamodel.Bibliotecar;
import bibliotecamodel.Carte;
import bibliotecamodel.CarteDTO;

import java.util.List;

public interface IBibliotecaServices {
    public void loginB(String username, String parola, IBibliotecaObserver obs) throws BibliotecaException ;
    public void logoutB(Bibliotecar bibliotecar, IBibliotecaObserver client) throws BibliotecaException;

    public void loginA(String username, String parola, IBibliotecaObserver obs) throws BibliotecaException ;
    public void logoutA(Abonat abonat, IBibliotecaObserver client) throws BibliotecaException;

    public List<Carte> getToateCartile();
    public List<Carte> getToateCartileDisponibile();
    public List<CarteDTO> getToateCartileImprumutate(int idAbonat);

    public void adaugaCarte (String titlu, String autor) throws Exception;
    public void modificaCarte (Integer id,String titlu, String autor, Boolean stare) throws Exception;
    public void stergeCarte (Integer id);

    public Abonat abonatConectat(String username, String parola);

    public void imprumutaCarte(int idCarte, int idAbonat) throws Exception;
    public void returneazaCarte(int idCarte, String usernameAbonat) throws BibliotecaException;

    String infosImprumut(Carte carteSelectata);
}