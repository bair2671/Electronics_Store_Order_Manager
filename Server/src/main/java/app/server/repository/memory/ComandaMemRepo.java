package app.server.repository.memory;

import app.server.model.Comanda;
import app.server.model.StatusComanda;
import app.server.repository.interfaces.ComandaRepo;


public class ComandaMemRepo implements ComandaRepo {
    @Override
    public Comanda findOneByOperatorAndClient(int idOperator, int idClient) {
        return null;
    }

    @Override
    public Iterable<Comanda> findByStatus(StatusComanda status) {
        return null;
    }

    @Override
    public Comanda findOne(int id) {
        return null;
    }

    @Override
    public Iterable<Comanda> findAll() {
        return null;
    }

    @Override
    public Comanda save(Comanda entity) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Comanda entity) {

    }
}
