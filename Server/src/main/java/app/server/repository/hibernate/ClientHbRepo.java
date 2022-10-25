package app.server.repository.hibernate;

import app.server.model.Client;
import app.server.repository.interfaces.ClientRepo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class ClientHbRepo implements ClientRepo {

    @Autowired
    public ClientHbRepo() {
    }

    @Override
    public synchronized Client findOne(int id) {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Client client = session.get(Client.class, id);
                System.out.println("Cautam clientul cu id " + client.getId());
                tx.commit();
                return client;
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
    public synchronized Iterable<Client> findAll() {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Client> clienti =
                        session.createQuery("from Client as c order by c.nume asc", Client.class).list();
                System.out.println(clienti.size() + " client(s) found:");
                for (Client c : clienti) {
                    System.out.println(c);
                }
                tx.commit();
                return clienti;
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
    public synchronized Client save(Client entity) {
        System.out.println("Init session - ClientHbRepo.save("+entity+")");      ///////////
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
            System.out.println("Closed session - ClientHbRepo.save("+entity+")");
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
                Client client = session.get(Client.class, id);
                session.delete(client);
                System.out.println("Stergem clientul cu id " + client.getId());
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
    public void update(Client entity) {
    }

    @Override
    public synchronized Client findOneByEmail(String email) {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Client client = session.createQuery("from Client where email = '"+email+"'",Client.class)
                        .setMaxResults(1)
                        .uniqueResult();
                System.out.println("Cautam clientul cu email" + email);
                tx.commit();
                return client;
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
