package app.server.repository.hibernate;

import app.server.model.AdministratorStocuri;
import app.server.repository.interfaces.AdministratorStocuriRepo;
import app.server.utils.Cryptographer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdministratorStocuriHbRepo implements AdministratorStocuriRepo {

    @Autowired
    public AdministratorStocuriHbRepo() {

    }

    @Override
    public synchronized AdministratorStocuri findOneByUsername(String username) {
        Cryptographer cryptographer = new Cryptographer();
        MySessionFactory.initialize();
        try(Session session =  MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                AdministratorStocuri admin = session.createQuery("from AdministratorStocuri where username = '"+username+"'",AdministratorStocuri.class)
                        .setMaxResults(1)
                        .uniqueResult();
                System.out.println("Cautam Administratorul stocurilor cu id " + admin.getId());
                tx.commit();
                admin.setPassword(cryptographer.decrypt(admin.getPassword()));
                return admin;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }finally {
            MySessionFactory.close();
        }

        return null;
    }

    @Override
    public synchronized AdministratorStocuri findOne(int id) {
        MySessionFactory.initialize();
        try(Session session =  MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                AdministratorStocuri admin = session.get(AdministratorStocuri.class, id);
                System.out.println("Cautam Administratorul stocurilor cu id " + admin.getId());
                tx.commit();
                return admin;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }finally {
            MySessionFactory.close();
        }
        return null;
    }

    @Override
    public Iterable<AdministratorStocuri> findAll() {
        return null;
    }

    @Override
    public AdministratorStocuri save(AdministratorStocuri entity) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(AdministratorStocuri entity) {

    }
}
