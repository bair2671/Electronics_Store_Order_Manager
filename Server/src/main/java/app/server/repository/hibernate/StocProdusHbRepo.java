package app.server.repository.hibernate;

import app.server.model.StocProdus;
import app.server.repository.interfaces.StocProdusRepo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class StocProdusHbRepo implements StocProdusRepo {

    @Autowired
    public StocProdusHbRepo() {

    }

    @Override
    public synchronized StocProdus findOneByProdus(int idProdus) {
        System.out.println("Init session - StocProdusHbRepo.findOneByProdus("+idProdus+")"); ////////
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                StocProdus stocProdus = session.createQuery("from StocProdus where id_produs = '"+idProdus+"'",StocProdus.class)
                        .setMaxResults(1)
                        .uniqueResult();
                System.out.println("Cautam stocul cu id " + stocProdus.getId());
                tx.commit();
                return stocProdus;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        finally {
            MySessionFactory.close();
            System.out.println("Closed session - StocProdusHbRepo.findOneByProdus("+idProdus+")"); ////////
        }
        return null;
    }
    @Override
    public StocProdus findOne(int id) {
        return null;
    }

    @Override
    public Iterable<StocProdus> findAll() {
        return null;
    }

    @Override
    public synchronized StocProdus save(StocProdus entity) {
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
        } finally {
            MySessionFactory.close();
        }

        return null;
    }

    @Override
    public synchronized void delete(int id) {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                StocProdus stoc = session.get(StocProdus.class, id);
                if(stoc!=null)
                    session.delete(stoc);
                System.out.println("Stergem stocul cu id " + id);
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
    public synchronized void update(StocProdus entity) {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                //session.update(entity);
                session.createQuery("update StocProdus set stoc='"+entity.getStoc()+"' where id="+entity.getId()).executeUpdate();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }finally {
            MySessionFactory.close();
        }

    }


}
