package app.client.controller.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class MessageAlert {
    public static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        if(type == Alert.AlertType.INFORMATION)
            message.setTitle("Mesaj de informare");
        if(type == Alert.AlertType.WARNING)
            message.setTitle("Mesaj de avertizare");
        if(type == Alert.AlertType.ERROR)
            message.setTitle("Mesaj de eroare");
        message.setContentText(text);
        message.initOwner(owner);
        message.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        message.showAndWait();
    }

    public static void showErrorMessage(Stage owner, String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.initOwner(owner);
        message.setHeaderText("Eroare!");
        message.setTitle("Mesaj de eroare");
        message.setContentText(text);
        message.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        message.showAndWait();
    }
}


