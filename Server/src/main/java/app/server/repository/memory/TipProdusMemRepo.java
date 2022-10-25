package app.server.repository.memory;

import app.server.model.TipProdus;
import app.server.repository.interfaces.TipProdusRepo;


public class TipProdusMemRepo implements TipProdusRepo {
    @Override
    public TipProdus findOneByName(String name) {
        return null;
    }

    @Override
    public TipProdus findOne(int id) {
        return null;
    }

    @Override
    public Iterable<TipProdus> findAll() {
        return null;
    }

    @Override
    public TipProdus save(TipProdus entity) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(TipProdus entity) {

    }
}
