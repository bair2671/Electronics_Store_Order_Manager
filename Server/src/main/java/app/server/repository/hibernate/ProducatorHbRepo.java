package app.server.repository.hibernate;

import app.server.model.Producator;
import app.server.repository.interfaces.ProducatorRepo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class ProducatorHbRepo implements ProducatorRepo {

    @Autowired
    public ProducatorHbRepo() {

    }

    @Override
    public synchronized Producator findOneByName(String name) {
        MySessionFactory.initialize();
        try(Session session =  MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Producator producator = session.createQuery("from Producator where nume = '"+name+"'",Producator.class)
                        .setMaxResults(1)
                        .uniqueResult();
                System.out.println("Cautam producatorul cu numele " + name);
                tx.commit();
                return producator;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }

        } finally {
            MySessionFactory.close();
        }
        return null;
    }

    @Override
    public synchronized Producator findOne(int id) {
        MySessionFactory.initialize();
        try(Session session =  MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Producator producator = session.get(Producator.class, id);
                System.out.println("Cautam producatorul cu id " + producator.getId());
                tx.commit();
                return producator;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }

        } finally {
            MySessionFactory.close();
        }
        return null;
    }

    @Override
    public synchronized Iterable<Producator> findAll() {
        MySessionFactory.initialize();
        try(Session session =  MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Producator> producatori =
                        session.createQuery("from Producator as c order by c.nume asc", Producator.class).list();
                System.out.println(producatori.size() + " producator(s) found:");
                for (Producator p : producatori) {
                    System.out.println(p);
                }
                tx.commit();
                return producatori;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }

        } finally {
            MySessionFactory.close();
        }
        return null;
    }

    @Override
    public synchronized Producator save(Producator entity) {
        MySessionFactory.initialize();
        try(Session session =  MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Serializable id = session.save(entity);
                entity.setId((Integer) id);
                tx.commit();
                return entity;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }

        }  finally {
            MySessionFactory.close();
        }

        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Producator entity) {

    }
}
