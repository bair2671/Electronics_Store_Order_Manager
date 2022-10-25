package app.server.repository.hibernate;

import app.server.model.Produs;
import app.server.repository.interfaces.ProdusRepo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class ProdusHbRepo implements ProdusRepo {

    @Autowired
    public ProdusHbRepo() {

    }

    @Override
    public Produs findOne(int id) {
        return null;
    }

    @Override
    public synchronized Iterable<Produs> findAll() {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Produs> produse =
                        session.createQuery("from Produs as c order by c.nume asc", Produs.class).list();
                System.out.println(produse.size() + " produs(es) found:");
                for (Produs p : produse) {
                    System.out.println(p);
                }
                tx.commit();
                return produse;
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
    public synchronized Produs save(Produs entity) {
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
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Produs produs = session.get(Produs.class, id);
                if(produs!=null)
                    session.delete(produs);
                System.out.println("Stergem produsul cu id " + id);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }finally {
            MySessionFactory.close();
        }
    }

    @Override
    public synchronized void update(Produs entity) {
        MySessionFactory.initialize();
        try(Session session =  MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                //session.update(entity);
                session.createQuery("update Produs set pret='"+entity.getPret()+"' where id="+entity.getId()).executeUpdate();
                System.out.println("Actualizam comanda cu id " + entity.getId());
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }finally {
            MySessionFactory.close();
        }

    }

    @Override
    public synchronized Produs findOneByProducatorAndNume(int idProducator, String nume) {
        MySessionFactory.initialize();
        try(Session session =   MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Produs produs = session.createQuery("from Produs where nume = '"+nume+"'" +
                        " and id_producator='"+idProducator+"'",Produs.class)
                        .setMaxResults(1)
                        .uniqueResult();
                System.out.println("Cautam produsul cu numele " + nume + "si cu id-ul producatorului "+idProducator);
                tx.commit();
                return produs;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }finally {
            MySessionFactory.close();
        }

        return null;
    }
}
