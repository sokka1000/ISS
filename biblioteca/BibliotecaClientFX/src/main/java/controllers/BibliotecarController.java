package controllers;

import bibliotecamodel.Bibliotecar;
import bibliotecamodel.Carte;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.BibliotecaException;
import services.IBibliotecaObserver;
import services.IBibliotecaServices;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BibliotecarController  extends UnicastRemoteObject implements IBibliotecaObserver, Serializable {

    private IBibliotecaServices service;
    private Bibliotecar bibliotecarConectat;
    private ObservableList<Carte> modelCarti = FXCollections.observableArrayList();

    public BibliotecarController() throws RemoteException { }

    @FXML
    TableView<Carte> tabelCarti;

    @FXML
    TextField textFdCodCarte;

    @FXML
    TextField textFdTitlu;

    @FXML
    TextField textFdAutor;

    public void initialize() {
        tabelCarti.setItems(modelCarti);
        colorTable();
    }

    public void colorTable(){
        tabelCarti.setRowFactory(tv -> new TableRow<Carte>(){
            @Override
            protected void updateItem(Carte item, boolean empty) {
                super.updateItem(item,empty);
                if (empty || item == null) {
                    setStyle("");
                } else if (item.getDisponibila()){
                    setStyle("-fx-background-color: #AF7AC5");
                } else {
                    setStyle("");
                }
            }
        });
    }

    public void setContext(IBibliotecaServices service) throws RemoteException{
        this.service = service;
    }

    public void logout(){
        try {
            service.logoutB(bibliotecarConectat,this);
            System.exit(0);
        } catch (BibliotecaException e) {
            System.out.println("Logout error " + e);
        }
    }

    public void setBibliotecarConectat(Bibliotecar bibliotecarConectat) throws BibliotecaException {
        this.bibliotecarConectat = bibliotecarConectat;
        initModel();
    }

    public void initModel()  {
        modelCarti.setAll(service.getToateCartile());
    }

    public void selectCarte(MouseEvent mouseEvent) {
        Carte carteSelectata = tabelCarti.getSelectionModel().getSelectedItem();

        textFdCodCarte.setText(carteSelectata.getId().toString());
        textFdTitlu.setText(carteSelectata.getTitlu());
        textFdAutor.setText(carteSelectata.getAutor());

        if(!carteSelectata.getDisponibila()) {
            showAbonatImprumut(carteSelectata);
        }
    }

    public void showAbonatImprumut(Carte carteSelectata) {

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        Stage dialogStage = new Stage();
        dialog.initOwner(dialogStage);
        VBox dialogVbox = new VBox(20);
        String string = service.infosImprumut(carteSelectata);

        dialogVbox.getChildren().add(new Text(string));
        Scene dialogScene = new Scene(dialogVbox, 500, 200);
        dialog.setScene(dialogScene);
        dialog.setTitle("Info");
        dialog.show();

    }

    @FXML
    TextField textFdUsernameAbonatRestituire;

    @FXML
    TextField textFdCodCarteRestituire;

    public void returneazaCarte(MouseEvent mouseEvent) {
        int idCarte = 0;
        String usernameAbonat = textFdUsernameAbonatRestituire.getText();
        try{
            idCarte = Integer.parseInt(textFdCodCarteRestituire.getText());
        }catch (NumberFormatException e){
            MessageBox.showErrorMessage(null, "Introduceti un numar valid!");
        }

        if(idCarte != 0 && usernameAbonat.length() > 3){
            try{
                service.returneazaCarte(idCarte, usernameAbonat);
                textFdUsernameAbonatRestituire.setText("");
                textFdCodCarteRestituire.setText("");
                MessageBox.showMessage(null, Alert.AlertType.INFORMATION, "Yeey", "Restituire cu succes!");
            }catch (BibliotecaException e){
                MessageBox.showErrorMessage(null, e.getMessage());
            }
        }
        else{
            MessageBox.showErrorMessage(null, "Campuri necompletate corect!");
        }

    }

    public void stergeCarte(MouseEvent mouseEvent) {
        Carte carteSelectata = tabelCarti.getSelectionModel().getSelectedItem();

        if (carteSelectata != null){
            try{
                service.stergeCarte(carteSelectata.getId());
                MessageBox.showMessage(null, Alert.AlertType.INFORMATION,"Yeey!", "Stergere cu succes!");
                textFdCodCarte.setText("");
                textFdTitlu.setText("");
                textFdAutor.setText("");
            } catch (Exception e) {
                MessageBox.showErrorMessage(null, e.getMessage());
            }
        }
        else{
            MessageBox.showErrorMessage(null, "NIMIC SELECTAT!");
        }

    }

    public void modificaCarte(MouseEvent mouseEvent) {
        Carte carteSelectata = tabelCarti.getSelectionModel().getSelectedItem();

        if (carteSelectata != null){

            String titlu = textFdTitlu.getText();
            String autor = textFdAutor.getText();

            String err = "";

            if (titlu.length() < 3) {
                err += "Titlu invalid!\n";
            }
            if ( autor.length() < 3){
                err += "Autor invalid!\n";
            }

            if (err.length() == 0){
                try{
                    service.modificaCarte(carteSelectata.getId(), titlu, autor, carteSelectata.getDisponibila());
                    MessageBox.showMessage(null, Alert.AlertType.INFORMATION,"Yeey!", "Carte modificata cu succes!");
                    textFdCodCarte.setText("");
                    textFdTitlu.setText("");
                    textFdAutor.setText("");
                } catch (Exception e) {
                    MessageBox.showErrorMessage(null, e.getMessage());
                }
            }
            else{
                MessageBox.showErrorMessage(null, err);
            }

        }else{
            MessageBox.showErrorMessage(null, "NIMIC SELECTAT!");
        }

    }

    public void adaugaCarte(MouseEvent mouseEvent) {
        String titlu = textFdTitlu.getText();
        String autor = textFdAutor.getText();

        String err = "";

        if (titlu.length() < 3) {
            err += "Titlu invalid!\n";
        }
        if ( autor.length() < 3){
            err += "Autor invalid!\n";
        }

        if (err.length() == 0){
            try{
                service.adaugaCarte(titlu, autor);
                MessageBox.showMessage(null, Alert.AlertType.INFORMATION,"Yeey!", "Carte adaugata cu succes!");
                textFdCodCarte.setText("");
                textFdTitlu.setText("");
                textFdAutor.setText("");
            } catch (Exception e) {
                MessageBox.showErrorMessage(null, e.getMessage());
            }
        }
        else{
            MessageBox.showErrorMessage(null, err);
        }

    }

    @Override
    public void carteUpdated() throws BibliotecaException, RemoteException {
        Platform.runLater(()->{
            modelCarti.setAll(service.getToateCartile());
            tabelCarti.refresh();
            colorTable();
        });

    }

    @Override
    public void imprumutUpdated() throws BibliotecaException, RemoteException {
        Platform.runLater(()->{
            modelCarti.setAll(service.getToateCartile());
            tabelCarti.refresh();
            colorTable();
        });

    }

    public void logoutHandler(MouseEvent mouseEvent) {
        logout();
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
    }
}