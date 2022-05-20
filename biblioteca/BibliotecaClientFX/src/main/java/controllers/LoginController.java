package controllers;

import bibliotecamodel.Abonat;
import bibliotecamodel.Bibliotecar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.IBibliotecaServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginController extends UnicastRemoteObject {



    private IBibliotecaServices service;

    public LoginController() throws RemoteException {
    }

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwdField;

    @FXML
    Button buttonLogIn;

    @FXML
    public void initialize() {

    }


    public void setContext(IBibliotecaServices service) throws RemoteException {
        this.service = service;
    }




    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String parola = passwdField.getText();

        if (username.startsWith("admin")){

            Bibliotecar bibliotecar = new Bibliotecar("", username, parola, "");

            try{

                //bibliotecar
                FXMLLoader bLoader = new FXMLLoader(getClass().getResource("/bibliotecarView.fxml"));
                Parent bRoot = bLoader.load();
                BibliotecarController bibliotecarController = bLoader.getController();
                bibliotecarController.setContext(service);

                service.loginB(username, parola , bibliotecarController);

                Stage stage = new Stage();
                stage.setScene(new Scene(bRoot));


                stage.setOnCloseRequest(event -> {
                    bibliotecarController.logout();
                    System.exit(0);
                });
                bibliotecarController.setBibliotecarConectat(bibliotecar);
                stage.setTitle("Bibliotecar: " + bibliotecar.getUsername());
                stage.show();

                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

            } catch (Exception e) {
                MessageBox.showErrorMessage(null,e.getMessage());
            }
        }
        else {


            Abonat abonat = new Abonat("", username, parola, "");

            try{
                //abonat
                FXMLLoader aLoader = new FXMLLoader(getClass().getResource("/abonatView.fxml"));
                Parent aRoot = aLoader.load();
                AbonatController abonatController = aLoader.getController();
                abonatController.setContext(service);


                service.loginA(username, parola, abonatController);

                Stage stage=new Stage();
                stage.setScene(new Scene(aRoot));

                stage.setOnCloseRequest(event -> {
                    abonatController.logout();
                    System.exit(0);
                });
                abonatController.setAbonatConectat(abonat);
                stage.setTitle("Abonat: " + abonat.getUsername());
                stage.show();

                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

            } catch (Exception e) {
                MessageBox.showErrorMessage(null,e.getMessage());
            }
        }
    }
}

