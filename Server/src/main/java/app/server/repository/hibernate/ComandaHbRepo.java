package app.server.repository.hibernate;

import app.server.model.Comanda;
import app.server.model.StatusComanda;
import app.server.repository.interfaces.ComandaRepo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class ComandaHbRepo implements ComandaRepo {

    @Autowired
    public ComandaHbRepo() {
    }

    @Override
    public  synchronized Comanda findOne(int id) {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Comanda comanda = session.get(Comanda.class, id);
                System.out.println("Cautam comanda cu id " + comanda.getId());
                tx.commit();
                return comanda;
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
    public synchronized Iterable<Comanda> findAll() {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Comanda> comenzi =
                        session.createQuery("from Comanda as c order by c.nume asc", Comanda.class).list();
                System.out.println(comenzi.size() + " comenzi gasite:");
                for (Comanda c : comenzi) {
                    System.out.println(c);
                }
                tx.commit();
                return comenzi;
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
    public synchronized Comanda save(Comanda entity) {
        System.out.println("Init session - ComandaHbRepo.save("+entity+")"); ////////
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
            System.out.println("Closed session - ComandaHbRepo.save("+entity+")"); ////////
        }

        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public synchronized void update(Comanda entity) {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                //session.update(entity);
                session.createQuery("update Comanda set status='"+entity.getStatus().toString()+"' where id="+entity.getId()).executeUpdate();
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
    public synchronized Comanda findOneByOperatorAndClient(int idOperator, int idClient) {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Comanda comanda = session.createQuery("from Comanda where  id_client= '"+idClient+"' " +
                        "and id_operator= '"+idOperator+"'",Comanda.class)
                        .setMaxResults(1)
                        .uniqueResult();
                System.out.println("Cautam comanda");
                tx.commit();
                return comanda;
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
    public synchronized Iterable<Comanda> findByStatus(StatusComanda status) {
        System.out.println("Init session - ComandaHbRepo.findByStatus("+status+")"); ////////
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Comanda> comenzi =
                        session.createQuery("from Comanda as c  where status='"+status.toString()+"' order by c.id asc", Comanda.class).list();
                System.out.println(comenzi.size() + " comenzi gasite:");
                for (Comanda c : comenzi) {
                    System.out.println(c);
                }
                tx.commit();
                return comenzi;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        } finally {
            MySessionFactory.close();
            System.out.println("Closed session - ComandaHbRepo.findByStatus("+status+")");
        }
        return null;
    }
}
