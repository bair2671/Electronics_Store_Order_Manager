package app.client.controller;

import app.client.controller.dialogs.MessageAlert;
import app.client.wrraper.ComandaWrraper;
import app.client.wrraper.ProdusWrraper;
import app.server.model.AdministratorComenzi;
import app.server.model.Comanda;
import app.server.model.StatusComanda;
import app.server.services.AppException;
import app.server.services.IService;
import app.server.services.Observer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminComenziController extends UnicastRemoteObject implements Observer {
    private IService server;
    private Stage stage;
    private AdministratorComenzi admin;

    ObservableList<ProdusWrraper> modelProduse;
    ObservableList<ComandaWrraper> modelComenzi;

    @FXML
    AnchorPane anchorPane;

    @FXML
    Label labelAdmin;
    @FXML
    Label labelUsername;

    @FXML
    TableView<ComandaWrraper> tableComenzi;
    @FXML
    TableColumn<ComandaWrraper,Integer> columnId;
    @FXML
    TableColumn<ComandaWrraper,String> columnClient;
    @FXML
    TableColumn<ComandaWrraper,String> columnOperator;
    @FXML
    TableColumn<ComandaWrraper,String> columnData;
    @FXML
    TableColumn<ComandaWrraper,Float> columnValoare;


    @FXML
    TableView<ProdusWrraper> tableProduse;
    @FXML
    TableColumn<ProdusWrraper,String> columnNume;
    @FXML
    TableColumn<ProdusWrraper,String> columnProducator;
    @FXML
    TableColumn<ProdusWrraper,String> columnTip;
    @FXML
    TableColumn<ProdusWrraper,Float> columnPret;
    @FXML
    TableColumn<ProdusWrraper,Integer> columnNrExemplare;

    @FXML
    Button buttonLivrare;

    @FXML
    TextField textFieldNume;
    @FXML
    TextField textFieldPrenume;
    @FXML
    TextField textFieldTelefon;
    @FXML
    TextField textFieldEmail;
    @FXML
    TextField textFieldData;
    @FXML
    TextField textFieldValoare;

    public AdminComenziController() throws RemoteException {
    }

    @FXML
    private void initialize(){
        anchorPane.getStylesheets().add("app.css");

        modelProduse = FXCollections.observableArrayList();
        columnNume.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("nume"));
        columnProducator.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("producator"));
        columnTip.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("tip"));
        columnPret.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,Float>("pret"));
        columnNrExemplare.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,Integer>("nrExemplare"));
        tableProduse.setItems(modelProduse);

        modelComenzi = FXCollections.observableArrayList();
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnOperator.setCellValueFactory(new PropertyValueFactory<>("operator"));
        columnClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        columnValoare.setCellValueFactory(new PropertyValueFactory<>("valoare"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        tableComenzi.setItems(modelComenzi);

        textFieldEmail.setDisable(true);
        textFieldTelefon.setDisable(true);
        textFieldData.setDisable(true);
        textFieldNume.setDisable(true);
        textFieldPrenume.setDisable(true);
        textFieldValoare.setDisable(true);

        textFieldEmail.setStyle("-fx-opacity: 1;");
        textFieldTelefon.setStyle("-fx-opacity: 1;");
        textFieldData.setStyle("-fx-opacity: 1;");
        textFieldNume.setStyle("-fx-opacity: 1;");
        textFieldPrenume.setStyle("-fx-opacity: 1;");
        textFieldValoare.setStyle("-fx-opacity: 1;");
    }

    public void setStageServiceAndAngajat(Stage stage, IService service, AdministratorComenzi admin) {
        this.server = service;
        this.stage = stage;
        this.admin = admin;
        labelAdmin.setText("Administrator comenzi : " + admin.getNume()+" "+admin.getPrenume());
        labelUsername.setText("Username : " + admin.getUsername());
        initModel();
    }

    private void initModel(){
        List<ComandaWrraper> comenzi = new ArrayList<>();
        try {
            Arrays.asList(server.findComenziByStatus(StatusComanda.IN_ASTEPTARE)).forEach(x -> {
                comenzi.add(new ComandaWrraper(x));
            });

            comenzi.sort((o1, o2) -> Integer.compare(0, o2.getData().compareTo(o1.getData())));

        } catch (AppException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }

        modelComenzi.setAll(comenzi);

        tableComenzi.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                textFieldData.setText(newSelection.getDateString());
                textFieldEmail.setText(newSelection.getClient().getEmail());
                textFieldTelefon.setText(newSelection.getClient().getTelefon());
                textFieldNume.setText(newSelection.getClient().getNume());
                textFieldPrenume.setText(newSelection.getClient().getPrenume());
                textFieldValoare.setText(newSelection.getValoare()+"");

                List<ProdusWrraper> produse = new ArrayList<>();
                try {
                    Arrays.asList(server.findProduseComenziByComanda(newSelection.getId())).forEach(x -> produse.add(new ProdusWrraper(x.getProdus(),x.getCantitate())));
                } catch (AppException e) {
                    MessageAlert.showErrorMessage(null,e.getMessage());
                }

                modelProduse.setAll(produse);
            }
        });


    }

    @FXML
    private void buttonDeconectareClicked(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/login.fxml"));
            AnchorPane LoginLayout = fxmlLoader.load();
            stage.setScene(new Scene(LoginLayout));

            LoginController loginController = fxmlLoader.getController();
            loginController.setStageAndService(stage, server);
            stage.setTitle("Login");

            server.logout(this);
        }
        catch (Exception ex){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Eroare",ex.getMessage());
        }
    }

    @FXML
    private void buttonLivrareClicked() {
        if (tableComenzi.getSelectionModel().getSelectedItems().size() == 0)
        {
            MessageAlert.showMessage(null, Alert.AlertType.WARNING,"Eroare","Nu ati selectat nicio comanda!");
            return;
        }
        ComandaWrraper comandaWr = tableComenzi.getSelectionModel().getSelectedItem();
        Comanda comanda = comandaWr.getComanda();
        comanda.setStatus(StatusComanda.TRIMISA);
        try {
            server.updateComanda(comanda);
        } catch (AppException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        modelComenzi.remove(comandaWr);
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Informare","Comanda livrata!");

        clearTextFields();
    }

    private void clearTextFields(){
        textFieldValoare.clear();
        textFieldNume.clear();
        textFieldTelefon.clear();
        textFieldData.clear();
        textFieldEmail.clear();
        textFieldPrenume.clear();
        modelProduse.clear();
    }

    @Override
    public void update() throws AppException, RemoteException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        initModel();
                        if (tableComenzi.getSelectionModel().getSelectedItems().size() == 0)
                            clearTextFields();
                    }
                });
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            MessageAlert.showErrorMessage(null,"Eroare la sincronizare!");
        }
    }
}
