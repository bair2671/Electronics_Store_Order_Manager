package app.client;

import app.client.controller.LoginController;
import app.server.services.IService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class StartClient  extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("In start");

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IService server=(IService)factory.getBean("appService");
        System.out.println("Obtained a reference to remote chat server");

        initView(primaryStage,server);
        primaryStage.show();
    }

    private void initView(Stage primaryStage, IService service) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/login.fxml"));
        AnchorPane loginLayout = fxmlLoader.load();
        primaryStage.setScene(new Scene(loginLayout));


        LoginController loginController = fxmlLoader.getController();
        loginController.setStageAndService(primaryStage, service);
        primaryStage.setTitle("Login");
    }
}
