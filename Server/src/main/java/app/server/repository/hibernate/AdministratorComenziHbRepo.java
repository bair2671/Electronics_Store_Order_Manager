package app.server.repository.hibernate;

import app.server.model.AdministratorComenzi;
import app.server.repository.interfaces.AdministratorComenziRepo;
import app.server.utils.Cryptographer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdministratorComenziHbRepo implements AdministratorComenziRepo {

    @Autowired
    public AdministratorComenziHbRepo() {

    }

    @Override
    public synchronized AdministratorComenzi findOneByUsername(String username) throws Exception {
        Cryptographer cryptographer = new Cryptographer();
        MySessionFactory.initialize();
        try(Session session =   MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                AdministratorComenzi admin = session.createQuery("from AdministratorComenzi where username = '"+username+"'",AdministratorComenzi.class)
                        .setMaxResults(1)
                        .uniqueResult();

                System.out.println("Cautam Administratorul comenzilor cu id " + admin.getId());
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
    public synchronized AdministratorComenzi findOne(int id) {
        MySessionFactory.initialize();
        try(Session session =   MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                AdministratorComenzi admin = session.get(AdministratorComenzi.class, id);
                System.out.println("Cautam Administratorul comenzilor cu id " + admin.getId());
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
    public Iterable<AdministratorComenzi> findAll() {
        return null;
    }

    @Override
    public AdministratorComenzi save(AdministratorComenzi entity) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(AdministratorComenzi entity) {

    }
}
