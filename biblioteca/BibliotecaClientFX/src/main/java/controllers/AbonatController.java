package controllers;

import bibliotecamodel.Abonat;
import bibliotecamodel.Carte;
import bibliotecamodel.CarteDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import services.BibliotecaException;
import services.IBibliotecaObserver;
import services.IBibliotecaServices;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class AbonatController extends UnicastRemoteObject implements IBibliotecaObserver, Serializable {

    private IBibliotecaServices service;
    private Abonat abonatConectat;
    private ObservableList<Carte> modelCartiDisponibile = FXCollections.observableArrayList();
    private ObservableList<CarteDTO> modelCartiImprumutate = FXCollections.observableArrayList();

    public AbonatController() throws RemoteException { }

    @FXML
    TableView<Carte> tabelCartiDisponibile;

    @FXML
    TableView<CarteDTO> tabelCartiImprumutate;

    @FXML
    TextField textFieldCodCarte;

    @FXML
    TextField textFieldTitlu;

    @FXML
    TextField textFieldAutor;

    public void initialize() {
        tabelCartiDisponibile.setItems(modelCartiDisponibile);
        tabelCartiImprumutate.setItems(modelCartiImprumutate);
    }

    public void setContext(IBibliotecaServices service) throws RemoteException{
        this.service = service;
    }

    public void logout(){
        try {
            service.logoutA(abonatConectat,this);
            System.exit(0);
        } catch (BibliotecaException e) {
            System.out.println("Logout error " + e);
        }
    }

    public void setAbonatConectat(Abonat abonatDummyConectat) throws BibliotecaException {
        this.abonatConectat = service.abonatConectat(abonatDummyConectat.getUsername(), abonatDummyConectat.getParola());
        initModel();
    }

    public void initModel()  {

        modelCartiDisponibile.setAll(service.getToateCartileDisponibile());
        modelCartiImprumutate.setAll(service.getToateCartileImprumutate(abonatConectat.getId()));
    }

    public void selectCarte(MouseEvent mouseEvent) {
        Carte carteSelectata = tabelCartiDisponibile.getSelectionModel().getSelectedItem();

        textFieldCodCarte.setText(carteSelectata.getId().toString());
        textFieldTitlu.setText(carteSelectata.getTitlu());
        textFieldAutor.setText(carteSelectata.getAutor());
    }

    public void imprumutaCarte(MouseEvent mouseEvent) {
        Carte carteSelectata = tabelCartiDisponibile.getSelectionModel().getSelectedItem();

        if (carteSelectata != null){
            try{
                service.imprumutaCarte(carteSelectata.getId(), abonatConectat.getId());

                MessageBox.showMessage(null, Alert.AlertType.INFORMATION,"Yeey!", "Imprumut finalizat cu succes!");
                textFieldCodCarte.setText("");
                textFieldTitlu.setText("");
                textFieldAutor.setText("");
            } catch (Exception e) {
                MessageBox.showErrorMessage(null, e.getMessage());
            }
        } else {
            MessageBox.showErrorMessage(null, "NIMIC SELECTAT");
        }
    }


    @Override
    public void carteUpdated() throws BibliotecaException, RemoteException {
        Platform.runLater(()->{
            modelCartiDisponibile.setAll(service.getToateCartileDisponibile());
            tabelCartiDisponibile.refresh();
        });
    }

    @Override
    public void imprumutUpdated() throws BibliotecaException, RemoteException {
        Platform.runLater(()->{
            modelCartiDisponibile.setAll(service.getToateCartileDisponibile());
            tabelCartiDisponibile.refresh();

            modelCartiImprumutate.setAll(service.getToateCartileImprumutate(abonatConectat.getId()));
            tabelCartiImprumutate.refresh();
        });
    }

    public void logoutHandler(MouseEvent mouseEvent) {
        logout();
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
    }
}