package app.server.repository.memory;

import app.server.model.Produs;
import app.server.repository.interfaces.ProdusRepo;


public class ProdusMemRepo implements ProdusRepo {
    @Override
    public Produs findOneByProducatorAndNume(int idProducator, String nume) {
        return null;
    }

    @Override
    public Produs findOne(int id) {
        return null;
    }

    @Override
    public Iterable<Produs> findAll() {
        return null;
    }

    @Override
    public Produs save(Produs entity) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Produs entity) {

    }
}
