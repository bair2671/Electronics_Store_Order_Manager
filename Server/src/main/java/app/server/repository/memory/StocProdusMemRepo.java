package app.server.repository.memory;

import app.server.model.StocProdus;
import app.server.repository.interfaces.StocProdusRepo;


public class StocProdusMemRepo implements StocProdusRepo {
    @Override
    public StocProdus findOneByProdus(int idProdus) {
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
    public StocProdus save(StocProdus entity) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(StocProdus entity) {

    }
}
