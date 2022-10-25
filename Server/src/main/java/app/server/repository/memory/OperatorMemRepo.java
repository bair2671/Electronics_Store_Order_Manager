package app.server.repository.memory;

import app.server.model.Operator;
import app.server.repository.interfaces.OperatorRepo;

import java.util.Arrays;
import java.util.List;


public class OperatorMemRepo implements OperatorRepo {
    List<Operator> operatori;

    public OperatorMemRepo(){
        Operator operator = new Operator();
        operator.setNume("Popescu");
        operator.setPrenume("Ion");
        operator.setUsername("ion1");
        operator.setPassword("ion1");
        operator.setId(1);

        operatori = Arrays.asList(operator);
    }

    @Override
    public Operator findOneByUsername(String username) {
        for(Operator op: operatori){
            if(op.getUsername().equals(username))
                return op;
        }
        return null;
    }

    @Override
    public Operator findOne(int id) {
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
