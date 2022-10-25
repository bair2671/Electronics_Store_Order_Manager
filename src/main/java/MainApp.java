import app.client.controller.LoginController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import app.server.repository.hibernate.*;
import app.server.repository.interfaces.*;
import app.server.server.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainApp extends Application {
    static SessionFactory sessionFactory;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        initialize();

        ClientRepo clientRepo= new ClientHbRepo(sessionFactory);
        ComandaRepo comandaRepo= new ComandaHbRepo(sessionFactory);
        OperatorRepo operatorRepo= new OperatorHbRepo(sessionFactory);
        AdministratorComenziRepo adminComenziRepo= new AdministratorComenziHbRepo(sessionFactory);
        AdministratorStocuriRepo adminStocuriRepo= new AdministratorStocuriHbRepo(sessionFactory);
        ProducatorRepo producatorRepo= new ProducatorHbRepo(sessionFactory);
        ProdusComandaRepo produsComandaRepo= new ProdusComandaHbRepo(sessionFactory);
        ProdusRepo produsRepo= new ProdusHbRepo(sessionFactory);
        StocProdusRepo stocProdusRepo= new StocProdusHbRepo(sessionFactory);
        TipProdusRepo tipProdusRepo= new TipProdusHbRepo(sessionFactory);

        Service service = new Service(operatorRepo,adminComenziRepo,adminStocuriRepo,clientRepo,comandaRepo,producatorRepo,produsComandaRepo,produsRepo,stocProdusRepo,tipProdusRepo);

        initView(primaryStage,service);
        primaryStage.show();

    }

    private void initView(Stage primaryStage, Service service) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/login.fxml"));
        AnchorPane loginLayout = fxmlLoader.load();
        primaryStage.setScene(new Scene(loginLayout));


        LoginController loginController = fxmlLoader.getController();
        loginController.setStageAndService(primaryStage,service);
        primaryStage.setTitle("Login");

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                   close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });

    }

    private static void initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("/hibernate/hibernate.cfg.xml")
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Exception " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
