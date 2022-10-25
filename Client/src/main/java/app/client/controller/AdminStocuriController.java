package app.client.controller;

import app.client.controller.dialogs.ConfirmationDialog;
import app.client.controller.dialogs.MessageAlert;
import app.server.model.*;
import app.client.wrraper.ProdusWrraper;
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
import java.util.*;

public class AdminStocuriController extends UnicastRemoteObject implements Observer {
    private IService server;
    private Stage stage;
    private AdministratorStocuri admin;

    ObservableList<ProdusWrraper> modelStocuri;

    @FXML
    AnchorPane anchorPane;

    @FXML
    Label labelAdmin;
    @FXML
    Label labelUsername;

    @FXML
    TableView<ProdusWrraper> tableStocuri;
    @FXML
    TableColumn<ProdusWrraper,Integer> columnIdProdus;
    @FXML
    TableColumn<ProdusWrraper,String> columnNume;
    @FXML
    TableColumn<ProdusWrraper,String> columnProducator;
    @FXML
    TableColumn<ProdusWrraper,String> columnTip;
    @FXML
    TableColumn<ProdusWrraper,Float> columnPret;
    @FXML
    TableColumn<ProdusWrraper,Integer> columnStoc;

    @FXML
    TextField textFieldNume;
    @FXML
    TextField textFieldProducator;
    @FXML
    TextField textFieldTip;
    @FXML
    TextField textFieldPret;
    @FXML
    Spinner<Integer> spinnerStoc;

    public AdminStocuriController() throws RemoteException {
    }


    @FXML
    private void initialize(){
        anchorPane.getStylesheets().add("app.css");

        modelStocuri = FXCollections.observableArrayList();
        columnIdProdus.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,Integer>("id"));
        columnNume.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("nume"));
        columnProducator.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("producator"));
        columnTip.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("tip"));
        columnPret.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,Float>("pret"));
        columnStoc.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,Integer>("nrExemplare"));
        tableStocuri.setItems(modelStocuri);
        tableStocuri.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                textFieldNume.setText(newSelection.getNume());
                textFieldProducator.setText(newSelection.getProducator().toString());
                textFieldTip.setText(newSelection.getTip().toString());
                textFieldPret.setText(newSelection.getPret()+"");
                spinnerStoc.getValueFactory().setValue(newSelection.getNrExemplare());
            }
        });

        spinnerStoc.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 150));
    }

    public void setStageServiceAndAngajat(Stage stage, IService service, AdministratorStocuri admin) {
        this.server = service;
        this.stage = stage;
        this.admin = admin;
        labelAdmin.setText("Administrator stocuri : " + admin.getNume()+" "+admin.getPrenume());
        labelUsername.setText("Username : " + admin.getUsername());
        initModel();
    }

    private void initModel()  {
        List<ProdusWrraper> produse = new ArrayList<>();
        try {
            Arrays.asList(server.findAllProduse()).forEach(x -> {
                StocProdus stocProdus = null;
                try {
                    stocProdus = server.findOneStocByProdus(x.getId());
                } catch (AppException e) {
                    MessageAlert.showErrorMessage(null,e.getMessage());
                }
                if(stocProdus!=null) {
                    int stoc = 0;
                    try {
                        stoc = server.findOneStocByProdus(x.getId()).getStoc();
                    } catch (AppException e) {
                        MessageAlert.showErrorMessage(null,e.getMessage());
                    }
                    produse.add(new ProdusWrraper(x, stoc));
                }
            });
            produse.sort(Comparator.comparingInt(Entity::getId));
        } catch (AppException e) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Eroare",e.getMessage());
        }
        modelStocuri.setAll(produse);
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
    private void buttonAdaugareClicked(){
        try {
            validateInputs();
            Produs produs = server.findOneProdusByProducatorAndNume(textFieldProducator.getText(), textFieldNume.getText());
            if (produs != null)
                if(server.findOneStocByProdus(produs.getId())!=null)
                    throw new Exception("Acest produs este deja in stoc!\nDaca vreti sa modificati stocul folositi optiunea Actualizare!");
            Producator producator = server.findOneProducatorByName(textFieldProducator.getText());
            if(producator==null){
                Optional<ButtonType> result = ConfirmationDialog.showDialog("Producatorul introdus nu exista in baza de date","Doriti sa il adaugati?");
                if (result.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
                   return;
            }
            TipProdus tip = server.findOneTipByName(textFieldTip.getText());
            if(tip==null){
                Optional<ButtonType> result = ConfirmationDialog.showDialog("Tipul introdus nu exista in baza de date","Doriti sa il adaugati?");
                if (result.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
                    return;
            }
            if(producator==null){
                producator = server.addProducator(new Producator(textFieldProducator.getText()));
            }
            if(tip==null){
                tip = server.addTip(new TipProdus(textFieldTip.getText()));
            }
            if(produs==null)
                produs = server.addProdus(new Produs(textFieldNume.getText(),producator,tip,Float.parseFloat(textFieldPret.getText())));
            else {
                produs.setPret(Float.parseFloat(textFieldPret.getText()));
                server.updateProdus(produs);
            }
            server.addStoc(new StocProdus(produs,spinnerStoc.getValue()));
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Informare","Stoc adaugat!");
            initModel();
            clearTextFields();
        }
        catch (Exception ex){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Eroare",ex.getMessage());
        }

    }



    @FXML
    private void buttonActualizareClicked(){
        try{
            if (tableStocuri.getSelectionModel().getSelectedItems().size() == 0)
                throw new Exception("Nu ati selectat niciun stoc!");
            validateInputs();
            ProdusWrraper produsWr = tableStocuri.getSelectionModel().getSelectedItem();
            Produs produs = produsWr.getProdus();
            String error = "";
            if(!produs.getNume().equals(textFieldNume.getText()))
                error+="Nu se poate modifica numele produsului!\n";
            if(!produs.getProducator().getNume().equals(textFieldProducator.getText()))
                error+="Nu se poate modifica producatorul!\n";
            if(!produs.getTip().getNume().equals(textFieldTip.getText()))
                error+="Nu se poate modifica tipul produsului!\n";
            if(!error.equals("")) {
               throw new Exception(error);
            }
            produs.setPret(Float.parseFloat(textFieldPret.getText()));
            server.updateProdus(produs);
            StocProdus stoc = server.findOneStocByProdus(produs.getId());
            stoc.setStoc(spinnerStoc.getValue());
            server.updateStoc(stoc);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Informare","Stoc actualizat!");
            initModel();
            clearTextFields();
        }
        catch (Exception ex){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Eroare",ex.getMessage());
        }
    }

    @FXML
    private void buttonStergereClicked(){
        try {
            if (tableStocuri.getSelectionModel().getSelectedItems().size() == 0) {
                throw new Exception("Nu ati selectat niciun stoc!");
            }
            Produs produs = tableStocuri.getSelectionModel().getSelectedItem();
            StocProdus stoc = server.findOneStocByProdus(produs.getId());
            server.deleteStoc(stoc.getId());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Informare","Stoc sters!");
            initModel();
            clearTextFields();
        }
        catch (Exception ex){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Eroare", ex.getMessage());
        }
    }

    private void validateInputs() throws Exception {
        String error = "";
        if(textFieldNume.getText().isEmpty()) {
            error+="Nu ati introdus numele produsului!\n";
        }
        if(textFieldProducator.getText().isEmpty()) {
            error+="Nu ati introdus producatorul!\n";
        }
        if (textFieldTip.getText().isEmpty()) {
            error+="Nu ati introdus tipul produsului!\n";
        }
        if (textFieldPret.getText().isEmpty()) {
            error+="Nu ati introdus pretul produsului!\n";
        }
        float pret=0;
        try{
            pret = Float.parseFloat(textFieldPret.getText());
            if (pret<0)
                error+="Pretul nu poate fi negativ!\n";
            if(pret*100%1!=0)
                error+="Pretul poate sa aiba maxim 2 zecimale!\n";
        }
        catch (Exception ex) {
            error += "Pretul introdus nu este un numar!\n";
        }
        if(!error.equals("")) {
            //MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Eroare", error);
            throw new Exception(error);
            //return;
        }
    }

    private void clearTextFields() {
        textFieldNume.clear();
        textFieldProducator.clear();
        textFieldTip.clear();
        textFieldPret.clear();
        spinnerStoc.getValueFactory().setValue(0);
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
