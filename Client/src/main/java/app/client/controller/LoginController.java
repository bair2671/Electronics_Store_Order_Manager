package app.client.controller;

import app.client.controller.dialogs.MessageAlert;
import app.server.model.AdministratorComenzi;
import app.server.model.AdministratorStocuri;
import app.server.model.Angajat;
import app.server.model.Operator;
import app.server.services.IService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class LoginController {
    private IService server;
    private Stage stage;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button buttonConectare;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void initialize() {
        anchorPane.getStylesheets().add("app.css");
        comboBox.getItems().setAll("operator","administrator comenzi","administrator stocuri");
    }

    public void setStageAndService(Stage stage, IService service){
        this.server = service;
        this.stage = stage;
    }

    @FXML
    private void buttonConectareClicked(ActionEvent actionEvent){
        String error = "";
        if(comboBox.getValue()==null) {
           error+="Nu ai selectat tipul anagajatului!\n";
        }
        if(textFieldUsername.getText().isEmpty()) {
           error+="Nu ai introdus username-ul!\n";
        }
        if (passwordField.getText().isEmpty()) {
           error+="Nu ai introdus parola!";
        }
        if(!error.equals(""))
            MessageAlert.showMessage(null, Alert.AlertType.WARNING,"Eroare",error );
        else
            try {
                Angajat angajat = server.login(comboBox.getValue(),textFieldUsername.getText(),passwordField.getText());
                if (angajat != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    if(comboBox.getValue().equals("operator")){
                        fxmlLoader.setLocation(getClass().getResource("/fxml/operator_home.fxml"));
                        AnchorPane HomeLayout = fxmlLoader.load();
                        stage.setScene(new Scene(HomeLayout));

                        OperatorController operatorController = fxmlLoader.getController();
                        operatorController.setStageServiceAndAngajat(stage, server,(Operator)angajat);
                        stage.setTitle("Operator Home");
                        server.addObserver(operatorController);
                    }
                    if(comboBox.getValue().equals("administrator comenzi")){
                        fxmlLoader.setLocation(getClass().getResource("/fxml/admin_comenzi.fxml"));
                        AnchorPane HomeLayout = fxmlLoader.load();
                        stage.setScene(new Scene(HomeLayout));

                        AdminComenziController adminComenziController = fxmlLoader.getController();
                        adminComenziController.setStageServiceAndAngajat(stage, server,(AdministratorComenzi)angajat);
                        stage.setTitle("Administrator Comenzi Home");
                        server.addObserver(adminComenziController);
                    }
                    if(comboBox.getValue().equals("administrator stocuri")){
                        fxmlLoader.setLocation(getClass().getResource("/fxml/stocuri.fxml"));
                        AnchorPane HomeLayout = fxmlLoader.load();
                        stage.setScene(new Scene(HomeLayout));

                        AdminStocuriController adminStocuriController = fxmlLoader.getController();
                        adminStocuriController.setStageServiceAndAngajat(stage, server,(AdministratorStocuri)angajat);
                        stage.setTitle("Administrator Stocuri Home");
                        server.addObserver(adminStocuriController);
                    }
                }
            }
            catch(Exception ex)
            {
                textFieldUsername.clear();
                passwordField.clear();
                MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Eroare",ex.getMessage());
            }

    }

}
