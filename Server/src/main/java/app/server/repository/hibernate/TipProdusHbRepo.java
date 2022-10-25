package app.server.repository.hibernate;

import app.server.model.TipProdus;
import app.server.repository.interfaces.TipProdusRepo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class TipProdusHbRepo implements TipProdusRepo {

    @Autowired
    public TipProdusHbRepo() {

    }

    @Override
    public synchronized TipProdus findOneByName(String name) {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                TipProdus tip = session.createQuery("from TipProdus where nume = '"+name+"'",TipProdus.class)
                        .setMaxResults(1)
                        .uniqueResult();

                System.out.println("Cautam tipul de produs cu id " + tip.getId());
                tx.commit();
                return tip;
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
    public synchronized TipProdus findOne(int id) {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                TipProdus tip = session.get(TipProdus.class, id);
                System.out.println("Cautam tipul cu id " + tip.getId());
                tx.commit();
                return tip;
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
    public synchronized Iterable<TipProdus> findAll() {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<TipProdus> tipuri =
                        session.createQuery("from TipProdus as c order by c.nume asc", TipProdus.class).list();
                System.out.println(tipuri.size() + " producator(s) found:");
                for (TipProdus p : tipuri) {
                    System.out.println(p);
                }
                tx.commit();
                return tipuri;
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
    public synchronized TipProdus save(TipProdus entity) {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
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
        }finally {
            MySessionFactory.close();
        }

        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(TipProdus entity) {

    }
}
