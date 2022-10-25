package app.client.controller;

import app.client.controller.dialogs.MessageAlert;
import app.client.controller.widgets.ProducatorCheckBox;
import app.client.controller.widgets.TipCheckBox;
import app.client.utils.GeneratorPDF;
import app.client.wrraper.ComandaWrraper;
import app.client.wrraper.ProdusWrraper;
import app.server.model.*;
import app.server.services.AppException;
import app.server.services.IService;
import app.server.services.Observer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class OperatorController extends UnicastRemoteObject implements Observer {
    private IService server;
    private Stage stage;
    private Operator operator;

    //Creare Comanda
    ObservableList<Produs> modelProduse;
    ObservableList<ProdusWrraper> modelCos;

    @FXML
    AnchorPane anchorPane;

    @FXML
    Label labelOperator;
    @FXML
    Label labelUsername;


    @FXML
    TableView<Produs> tableProduse;
    @FXML
    TableColumn<Produs,Integer> columnIdProdus;
    @FXML
    TableColumn<Produs,String> columnNume;
    @FXML
    TableColumn<Produs,String> columnProducator;
    @FXML
    TableColumn<Produs,String> columnTip;
    @FXML
    TableColumn<Produs,Float> columnPret;

    @FXML
    Button buttonAdaugaInCos;

    @FXML
    TableView<ProdusWrraper> tableCos;
    @FXML
    TableColumn<ProdusWrraper,String> columnNumeCos;
    @FXML
    TableColumn<ProdusWrraper,String> columnProducatorCos;
    @FXML
    TableColumn<ProdusWrraper,String> columnTipCos;
    @FXML
    TableColumn<ProdusWrraper,Integer> columnPretCos;
    @FXML
    TableColumn<ProdusWrraper,Float> columnNrExemplareCos;

    @FXML
    Button buttonStergeDinCos;

    @FXML
    Button buttonInregistrareComanda;

    @FXML
    TextField textFieldNume;
    @FXML
    TextField textFieldPrenume;
    @FXML
    TextField textFieldTelefon;
    @FXML
    TextField textFieldEmail;

    @FXML
    Label labelValoareNumerica;


    //Confirmare livrare
    ObservableList<ProdusWrraper> modelProduse2;
    ObservableList<ComandaWrraper> modelComenzi;

    @FXML
    TableView<ComandaWrraper> tableComenzi;
    @FXML
    TableColumn<ComandaWrraper,Integer> columnId;
    @FXML
    TableColumn<ComandaWrraper,String> columnClient;
    @FXML
    TableColumn<ComandaWrraper,String> columnData;
    @FXML
    TableColumn<ComandaWrraper,Float> columnValoare;

    @FXML
    TableView<ProdusWrraper> tableProduse2;
    @FXML
    TableColumn<ProdusWrraper,String> columnNume2;
    @FXML
    TableColumn<ProdusWrraper,String> columnProducator2;
    @FXML
    TableColumn<ProdusWrraper,String> columnTip2;
    @FXML
    TableColumn<ProdusWrraper,Integer> columnPret2;
    @FXML
    TableColumn<ProdusWrraper,Float> columnNrExemplare2;

    @FXML
    TextField textFieldNume2;
    @FXML
    TextField textFieldPrenume2;
    @FXML
    TextField textFieldTelefon2;
    @FXML
    TextField textFieldEmail2;
    @FXML
    TextField textFieldData2;
    @FXML
    TextField textFieldValoare2;


    //Predare comananda
    ObservableList<ProdusWrraper> modelProduse3;
    ObservableList<ComandaWrraper> modelComenzi3;

    @FXML
    TableView<ComandaWrraper> tableComenzi3;
    @FXML
    TableColumn<ComandaWrraper,Integer> columnId3;
    @FXML
    TableColumn<ComandaWrraper,String> columnClient3;
    @FXML
    TableColumn<ComandaWrraper,String> columnData3;
    @FXML
    TableColumn<ComandaWrraper,Float> columnValoare3;

    @FXML
    TableView<ProdusWrraper> tableProduse3;
    @FXML
    TableColumn<ProdusWrraper,String> columnNume3;
    @FXML
    TableColumn<ProdusWrraper,String> columnProducator3;
    @FXML
    TableColumn<ProdusWrraper,String> columnTip3;
    @FXML
    TableColumn<ProdusWrraper,Integer> columnPret3;
    @FXML
    TableColumn<ProdusWrraper,Float> columnNrExemplare3;

    @FXML
    TextField textFieldNume3;
    @FXML
    TextField textFieldPrenume3;
    @FXML
    TextField textFieldTelefon3;
    @FXML
    TextField textFieldEmail3;
    @FXML
    TextField textFieldData3;
    @FXML
    TextField textFieldValoare3;


    //Filtrare produse
    ObservableList<TipCheckBox> modelTip;
    ObservableList<ProducatorCheckBox> modelProducator;

    @FXML
    Label labelTip;
    @FXML
    Label labelProducator;

    @FXML
    ListView<TipCheckBox> listViewTip;
    @FXML
    ListView<ProducatorCheckBox> listViewProducator;



    public OperatorController() throws RemoteException {
    }


    @FXML
    private void initialize(){
        anchorPane.getStylesheets().add("app.css");

        //Creare comanda
        modelTip = FXCollections.observableArrayList();
        listViewTip.setItems(modelTip);

        modelProducator = FXCollections.observableArrayList();
        listViewProducator.setItems(modelProducator);

        modelProduse = FXCollections.observableArrayList();
        columnIdProdus.setCellValueFactory(new PropertyValueFactory<Produs,Integer>("id"));
        columnNume.setCellValueFactory(new PropertyValueFactory<Produs,String>("nume"));
        columnProducator.setCellValueFactory(new PropertyValueFactory<Produs,String>("producator"));
        columnTip.setCellValueFactory(new PropertyValueFactory<Produs,String>("tip"));
        columnPret.setCellValueFactory(new PropertyValueFactory<Produs,Float>("pret"));
        tableProduse.setItems(modelProduse);

        modelCos = FXCollections.observableArrayList();
        columnNumeCos.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("nume"));
        columnProducatorCos.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("producator"));
        columnTipCos.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("tip"));
        columnPretCos.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,Integer>("pret"));
        columnNrExemplareCos.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,Float>("nrExemplare"));
        tableCos.setItems(modelCos);


        //Confirmare livrare
        modelProduse2 = FXCollections.observableArrayList();
        columnNume2.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("nume"));
        columnProducator2.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("producator"));
        columnTip2.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("tip"));
        columnPret2.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,Integer>("pret"));
        columnNrExemplare2.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,Float>("nrExemplare"));
        tableProduse2.setItems(modelProduse2);

        modelComenzi = FXCollections.observableArrayList();
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        columnValoare.setCellValueFactory(new PropertyValueFactory<>("valoare"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("dateString"));

        tableComenzi.setItems(modelComenzi);


        //Predare comanda
        modelProduse3 = FXCollections.observableArrayList();
        columnNume3.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("nume"));
        columnProducator3.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("producator"));
        columnTip3.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,String>("tip"));
        columnPret3.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,Integer>("pret"));
        columnNrExemplare3.setCellValueFactory(new PropertyValueFactory<ProdusWrraper,Float>("nrExemplare"));
        tableProduse3.setItems(modelProduse3);

        modelComenzi3 = FXCollections.observableArrayList();
        columnId3.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnClient3.setCellValueFactory(new PropertyValueFactory<>("client"));
        columnValoare3.setCellValueFactory(new PropertyValueFactory<>("valoare"));
        columnData3.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        tableComenzi3.setItems(modelComenzi3);

        textFieldEmail2.setDisable(true);
        textFieldTelefon2.setDisable(true);
        textFieldData2.setDisable(true);
        textFieldNume2.setDisable(true);
        textFieldPrenume2.setDisable(true);
        textFieldValoare2.setDisable(true);

        textFieldEmail3.setDisable(true);
        textFieldTelefon3.setDisable(true);
        textFieldData3.setDisable(true);
        textFieldNume3.setDisable(true);
        textFieldPrenume3.setDisable(true);
        textFieldValoare3.setDisable(true);

        textFieldEmail2.setStyle("-fx-opacity: 1;");
        textFieldTelefon2.setStyle("-fx-opacity: 1;");
        textFieldData2.setStyle("-fx-opacity: 1;");
        textFieldNume2.setStyle("-fx-opacity: 1;");
        textFieldPrenume2.setStyle("-fx-opacity: 1;");
        textFieldValoare2.setStyle("-fx-opacity: 1;");

        textFieldEmail3.setStyle("-fx-opacity: 1;");
        textFieldTelefon3.setStyle("-fx-opacity: 1;");
        textFieldData3.setStyle("-fx-opacity: 1;");
        textFieldNume3.setStyle("-fx-opacity: 1;");
        textFieldPrenume3.setStyle("-fx-opacity: 1;");
        textFieldValoare3.setStyle("-fx-opacity: 1;");
    }

    public void setStageServiceAndAngajat(Stage stage, IService service, Operator operator) {
        this.server = service;
        this.stage = stage;
        this.operator = operator;
        labelOperator.setText("Operator : " + operator.getNume()+" "+operator.getPrenume());
        labelUsername.setText("Username : " + operator.getUsername());
        initModel();
    }

    private void initModel(){
        //Filtrare
        //Pastrare filtrari
        List<TipProdus> tipuriBifate = getTipuriBifate();
        List<Producator> producatoriBifati = getProducatoriBifati();

        List<TipCheckBox> listTipuri =new ArrayList<>();
        try {
            Arrays.asList(server.findAllTipuri()).forEach(x -> {
                listTipuri.add(new TipCheckBox(x));
            });
        } catch (AppException e) {
            e.printStackTrace();
        }
        modelTip.setAll(listTipuri);

        List<ProducatorCheckBox> listProducatori =new ArrayList<>();
        try {
            Arrays.asList(server.findAllProducatori()).forEach(x -> {
                listProducatori.add(new ProducatorCheckBox(x));
            });
        } catch (AppException e) {
            e.printStackTrace();
        }
        modelProducator.setAll(listProducatori);

        listProducatori.forEach(x->{
            x.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    filtrare();
                }
            });
        });

        listTipuri.forEach(x->{
            x.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    filtrare();
                }
            });
        });

        for(TipCheckBox checkBox : modelTip){
            if(tipuriBifate.contains(checkBox.getTip()))
                checkBox.setSelected(true);
        }
        for(ProducatorCheckBox checkBox : modelProducator){
            if(producatoriBifati.contains(checkBox.getProducator()))
                checkBox.setSelected(true);
        }

        //Creare Comanda
        List<Produs> produse = new ArrayList<>();
        try {
            Arrays.asList(server.findAllProduse()).forEach(x -> {
                StocProdus stocProdus = null;
                try {
                    stocProdus = server.findOneStocByProdus(x.getId());
                } catch (AppException e) {
                    MessageAlert.showErrorMessage(null,e.getMessage());
                }
                if(stocProdus!=null)
                    if(stocProdus.getStoc()>0)
                        produse.add(x);
            });
            produse.sort(Comparator.comparingInt(Entity::getId));
        } catch (AppException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        modelProduse.setAll(produse);
        filtrare();

        //Confirmare livrare
        List<ComandaWrraper> comenzi = new ArrayList<>();
        try {
            Arrays.asList(server.findComenziByStatus(StatusComanda.TRIMISA)).forEach(x -> {
                if(x.getOperator().getId()==operator.getId()) {
                    comenzi.add(new ComandaWrraper(x));
                }
            });
            comenzi.sort((o1, o2) -> Integer.compare(0, o2.getData().compareTo(o1.getData())));
        } catch (AppException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        modelComenzi.setAll(comenzi);

        tableComenzi.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                textFieldData2.setText(newSelection.getDateString().toString());
                textFieldEmail2.setText(newSelection.getClient().getEmail());
                textFieldTelefon2.setText(newSelection.getClient().getTelefon());
                textFieldNume2.setText(newSelection.getClient().getNume());
                textFieldPrenume2.setText(newSelection.getClient().getPrenume());
                textFieldValoare2.setText(newSelection.getValoare()+"");

                List<ProdusWrraper> produse2 = new ArrayList<>();
                try {
                    Arrays.asList(server.findProduseComenziByComanda(newSelection.getId())).forEach(x -> produse2.add(new ProdusWrraper(x.getProdus(),x.getCantitate())));
                } catch (AppException e) {
                    MessageAlert.showErrorMessage(null,e.getMessage());
                }
                modelProduse2.setAll(produse2);
            }
        });


        //Predare comanda
        List<ComandaWrraper> comenzi3 = new ArrayList<>();
        try {
            Arrays.asList(server.findComenziByStatus(StatusComanda.LIVRATA)).forEach(x -> {
                if(x.getOperator().getId()==operator.getId()) {
                    comenzi3.add(new ComandaWrraper(x));
                }
            });
            comenzi3.sort((o1, o2) -> Integer.compare(0, o2.getData().compareTo(o1.getData())));
        } catch (AppException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        modelComenzi3.setAll(comenzi3);

        tableComenzi3.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                textFieldData3.setText(newSelection.getDateString());
                textFieldEmail3.setText(newSelection.getClient().getEmail());
                textFieldTelefon3.setText(newSelection.getClient().getTelefon());
                textFieldNume3.setText(newSelection.getClient().getNume());
                textFieldPrenume3.setText(newSelection.getClient().getPrenume());
                textFieldValoare3.setText(newSelection.getValoare()+"");

                List<ProdusWrraper> produse3 = new ArrayList<>();
                try {
                    Arrays.asList(server.findProduseComenziByComanda(newSelection.getId())).forEach(x -> produse3.add(new ProdusWrraper(x.getProdus(),x.getCantitate())));
                } catch (AppException e) {
                    MessageAlert.showErrorMessage(null,e.getMessage());
                }
                modelProduse3.setAll(produse3);
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
    private void buttonAdaugaInCosClicked(){
        if (tableProduse.getSelectionModel().getSelectedItems().size() == 0)
        {
            MessageAlert.showMessage(null, Alert.AlertType.WARNING,"Eroare","Nu ati selectat niciun produs!");
            return;
        }
        try {
            Produs produs = tableProduse.getSelectionModel().getSelectedItem();
            boolean este = false;
            for(int i=0;i<modelCos.size();i++) {
                ProdusWrraper produsWrraper = modelCos.get(i);
                if (produsWrraper.getId() == produs.getId()) {
                    produsWrraper.setNrExemplare(produsWrraper.getNrExemplare()+1);
                    modelCos.set(i, produsWrraper);
                    este = true;
                }
            }
            if(!este){
                modelCos.add(new ProdusWrraper(produs,1));
            }
            computeCommandValue();

        }
        catch (Exception ex){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Eroare",ex.getMessage());
        }
    }

    @FXML
    private void buttonStergeDinCosClicked(){
        if (tableCos.getSelectionModel().getSelectedItems().size() == 0)
        {
            MessageAlert.showMessage(null, Alert.AlertType.WARNING,"Eroare","Nu ati selectat niciun produs din cos!");
            return;
        }
        try {
            ProdusWrraper produs = tableCos.getSelectionModel().getSelectedItem();
            boolean gol = false;
            for(int i=0;i<modelCos.size();i++) {
                ProdusWrraper produsWrraper = modelCos.get(i);
                if (produsWrraper.getId() == produs.getId()) {
                    produsWrraper.setNrExemplare(produsWrraper.getNrExemplare()-1);
                    modelCos.set(i, produsWrraper);
                    if(produsWrraper.getNrExemplare()==0)
                        gol = true;
                }
            }
            if(gol){
                modelCos.remove(produs);
            }
            computeCommandValue();

        }
        catch (Exception ex){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Eroare",ex.getMessage());
        }
    }

    @FXML
    private void buttonInregistrareComandaClicked(){
        if(modelCos.size()==0){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Eroare", "Nu ati adaugat niciun produs in cos!");
            return;
        }
        String error = "";
        if(textFieldNume.getText().isEmpty()) {
            error+="Nu ati introdus numele clientului!\n";
        }
        if(textFieldPrenume.getText().isEmpty()) {
            error+="Nu ati introdus prenumele clientului!\n";
        }
        if (textFieldEmail.getText().isEmpty()) {
            error+="Nu ati introdus email-ul clientului!\n";
        }
        if (textFieldTelefon.getText().isEmpty()) {
            error+="Nu ati introdus telefonul clientului!\n";
        }
        if(!error.equals("")) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Eroare", error);
            return;
        }
        try {
            Client client = new Client(textFieldNume.getText(), textFieldPrenume.getText(), textFieldTelefon.getText(), textFieldEmail.getText());
            client = server.addClient(client);
            for (ProdusWrraper produsWrraper : modelCos) {
                StocProdus stocProdus = server.findOneStocByProdus(produsWrraper.getId());
                if (stocProdus == null)
                    throw new Exception("Produsul " + produsWrraper.getProducator().getNume() + " " + produsWrraper.getNume() + " nu mai este in stoc!");
                int stoc = stocProdus.getStoc();
                if (stoc == 0)
                    throw new Exception("Produsul " + produsWrraper.getProducator().getNume() + " " + produsWrraper.getNume() + " nu mai este in stoc!");
                if (stoc < produsWrraper.getNrExemplare())
                    throw new Exception("Mai sunt doar " + stoc + " exemplare ale produsului " + produsWrraper.getNume() + " in stoc!");
            }
            Comanda comanda = new Comanda(LocalDateTime.now(), client, operator, computeCommandValue());
            comanda = server.addComanda(comanda);
            for (ProdusWrraper produsWrraper : modelCos) {
                server.addProdusComanda(new ProdusComanda(produsWrraper.getProdus(), comanda, produsWrraper.getNrExemplare()));
                StocProdus stoc = server.findOneStocByProdus(produsWrraper.getId());
                stoc.setStoc(stoc.getStoc() - produsWrraper.getNrExemplare());
                server.updateStoc(stoc);
            }
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Informare","Comanda efectuata!");

            GeneratorPDF.getInstance().generarePdfRaportComanda(comanda,modelCos);

            textFieldTelefon.clear();
            textFieldEmail.clear();
            textFieldPrenume.clear();
            textFieldNume.clear();
            modelCos.clear();
            labelValoareNumerica.setText("0 lei");

        }
        catch (Exception ex){
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Eroare",ex.getMessage());

        }

    }

    @FXML
    private void buttonConfirmareClicked(){
        if (tableComenzi.getSelectionModel().getSelectedItems().size() == 0)
        {
            MessageAlert.showMessage(null, Alert.AlertType.WARNING,"Eroare","Nu ati selectat nicio comanda!");
            return;
        }
        ComandaWrraper comandaWr = tableComenzi.getSelectionModel().getSelectedItem();
        Comanda comanda = comandaWr.getComanda();
        comanda.setStatus(StatusComanda.LIVRATA);
        try {
            server.updateComanda(comanda);
        } catch (AppException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        modelComenzi.remove(comandaWr);
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Informare","Livrare confirmata!");

        textFieldValoare2.clear();
        textFieldNume2.clear();
        textFieldTelefon2.clear();
        textFieldData2.clear();
        textFieldEmail2.clear();
        textFieldPrenume2.clear();
        modelProduse2.clear();

    }

    @FXML
    private void buttonPredareClicked(){
        if (tableComenzi3.getSelectionModel().getSelectedItems().size() == 0)
        {
            MessageAlert.showMessage(null, Alert.AlertType.WARNING,"Eroare","Nu ati selectat nicio comanda!");
            return;
        }
        ComandaWrraper comandaWr = tableComenzi3.getSelectionModel().getSelectedItem();
        Comanda comanda = comandaWr.getComanda();
        comanda.setStatus(StatusComanda.PREDATA);
        try {
            server.updateComanda(comanda);
        } catch (AppException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        modelComenzi3.remove(comandaWr);
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Informare","Predare efectuata!");

        textFieldValoare3.clear();
        textFieldNume3.clear();
        textFieldTelefon3.clear();
        textFieldData3.clear();
        textFieldEmail3.clear();
        textFieldPrenume3.clear();
        modelProduse3.clear();
    }

    private void clearDeLivratTextFields(){
        textFieldValoare2.clear();
        textFieldNume2.clear();
        textFieldTelefon2.clear();
        textFieldData2.clear();
        textFieldEmail2.clear();
        textFieldPrenume2.clear();
        modelProduse2.clear();
    }

    private void clearDePredatTextFields(){
        textFieldValoare3.clear();
        textFieldNume3.clear();
        textFieldTelefon3.clear();
        textFieldData3.clear();
        textFieldEmail3.clear();
        textFieldPrenume3.clear();
        modelProduse3.clear();
    }

    private float computeCommandValue(){
        float val = 0F;
        for(int i=0;i<modelCos.size();i++) {
            ProdusWrraper produs = modelCos.get(i);
            val += produs.getPret() * produs.getNrExemplare();
        }
        labelValoareNumerica.setText(val+" lei");
        return val;
    }


    private void filtrare(){
        List<Produs> produse = new ArrayList<>();
        try {
            List<Produs> toate = new ArrayList<>();
            Arrays.asList(server.findAllProduse()).forEach(x -> {
                StocProdus stocProdus = null;
                try {
                    stocProdus = server.findOneStocByProdus(x.getId());
                } catch (AppException e) {
                    MessageAlert.showErrorMessage(null,e.getMessage());
                }
                if(stocProdus!=null)
                    if(stocProdus.getStoc()>0)
                        toate.add(x);
            });
            toate.sort(Comparator.comparingInt(Entity::getId));

            List<Produs> produseFiltrateTip = new ArrayList<>();

            List<TipProdus> tipuri = getTipuriBifate();
            if(tipuri.size()!=0) {
                toate.forEach(x -> {
                    if (tipuri.contains(x.getTip()))
                        produseFiltrateTip.add(x);
                });
            }
            else
                produseFiltrateTip.addAll(toate);

            List<Producator> producatori = getProducatoriBifati();
            if(producatori.size()!=0) {
                produseFiltrateTip.forEach(x -> {
                    if (producatori.contains(x.getProducator()))
                        produse.add(x);
                });
            }
            else
                produse.addAll(produseFiltrateTip);

            modelProduse.setAll(produse);

        } catch (AppException e) {
           MessageAlert.showErrorMessage(null,e.getMessage());
        }


    }

    private List<Producator> getProducatoriBifati(){
        List<Producator> producatori = new ArrayList<>();
        modelProducator.forEach(x->{
            if(x.isSelected())
                producatori.add(x.getProducator());
        });
        return producatori;
    }

    private List<TipProdus> getTipuriBifate(){
        List<TipProdus> tipuri = new ArrayList<>();
        modelTip.forEach(x->{
            if(x.isSelected())
                tipuri.add(x.getTip());
        });
        return tipuri;
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
                            clearDeLivratTextFields();
                        if (tableComenzi3.getSelectionModel().getSelectedItems().size() == 0)
                            clearDePredatTextFields();
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


