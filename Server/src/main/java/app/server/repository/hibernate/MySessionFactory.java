package app.server.repository.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.concurrent.TimeUnit;

public class MySessionFactory {
    static SessionFactory sessionFactory;
    static boolean isActive = false;

    public static synchronized void initialize() {
        while(isActive) {
            try {
                TimeUnit.MILLISECONDS.sleep( 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate/hibernate.cfg.xml")
                //.configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            isActive = true;
        } catch (Exception e) {
            System.err.println("Exception " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
            isActive = false;
        }
    }


}
