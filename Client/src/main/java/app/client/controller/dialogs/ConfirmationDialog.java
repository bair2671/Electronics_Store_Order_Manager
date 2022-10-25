package app.client.controller.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ConfirmationDialog {
    public static Optional<ButtonType> showDialog(String header,String text){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmare");
        alert.setHeaderText(header);
        alert.setContentText(text);

        ButtonType nu = new ButtonType("Nu", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType da = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(da,nu);

        return alert.showAndWait();
    }
}
