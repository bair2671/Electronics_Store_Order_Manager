package app.server.repository.hibernate;

import app.server.model.ProdusComanda;
import app.server.repository.interfaces.ProdusComandaRepo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdusComandaHbRepo implements ProdusComandaRepo {

    @Autowired
    public ProdusComandaHbRepo() {

    }

    @Override
    public ProdusComanda findOne(int id) {
        return null;
    }

    @Override
    public Iterable<ProdusComanda> findAll() {
        return null;
    }

    @Override
    public synchronized ProdusComanda save(ProdusComanda entity) {
        System.out.println("Init session - ProdusComandaHbRepo.save("+entity+")"); ////////
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }finally {
            MySessionFactory.close();
            System.out.println("Closed session - ProdusComandaHbRepo.save("+entity+")"); ////////
        }

        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(ProdusComanda entity) {

    }

    @Override
    public synchronized Iterable<ProdusComanda> findByComanda(Integer comandaId) {
        MySessionFactory.initialize();
        try(Session session = MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<ProdusComanda> produseComenzi =
                        session.createQuery("from ProdusComanda as p  where id_comanda = '"+comandaId+"' order by p.id asc", ProdusComanda.class).list();
                System.out.println(produseComenzi.size() + " produsComanda(s) found:");
                for (ProdusComanda p : produseComenzi) {
                    System.out.println(p);
                }
                tx.commit();
                return produseComenzi;
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
