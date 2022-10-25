package app.server.repository.hibernate;

import app.server.model.Operator;
import app.server.repository.interfaces.OperatorRepo;
import app.server.utils.Cryptographer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperatorHbRepo implements OperatorRepo {

    @Autowired
    public OperatorHbRepo() {

    }

    @Override
    public synchronized Operator findOneByUsername(String username) {
        Cryptographer cryptographer = new Cryptographer();
        MySessionFactory.initialize();
        try(Session session =  MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Operator operator = session.createQuery("from Operator where username = '"+username+"'",Operator.class)
                        .setMaxResults(1)
                        .uniqueResult();
                System.out.println("Cautam operatorul cu id " + operator.getId());
                tx.commit();
                operator.setPassword(cryptographer.decrypt(operator.getPassword()));
                return operator;
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
    public synchronized Operator findOne(int id) {
        MySessionFactory.initialize();
        try(Session session =  MySessionFactory.sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Operator operator = session.get(Operator.class, id);
                System.out.println("Cautam operatorul cu id " + operator.getId());
                tx.commit();
                return operator;
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
    public Iterable<Operator> findAll() {
        return null;
    }

    @Override
    public Operator save(Operator entity) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Operator entity) {

    }
}
