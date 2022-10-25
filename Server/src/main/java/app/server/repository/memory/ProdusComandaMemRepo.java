package app.server.repository.memory;

import app.server.model.ProdusComanda;
import app.server.repository.interfaces.ProdusComandaRepo;


public class ProdusComandaMemRepo implements ProdusComandaRepo {
    @Override
    public Iterable<ProdusComanda> findByComanda(Integer comandaId) {
        return null;
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
    public ProdusComanda save(ProdusComanda entity) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(ProdusComanda entity) {

    }
}
