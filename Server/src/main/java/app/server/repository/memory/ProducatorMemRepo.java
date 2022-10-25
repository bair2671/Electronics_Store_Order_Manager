package app.server.repository.memory;

import app.server.model.Producator;
import app.server.repository.interfaces.ProducatorRepo;


public class ProducatorMemRepo implements ProducatorRepo {
    @Override
    public Producator findOneByName(String name) {
        return null;
    }

    @Override
    public Producator findOne(int id) {
        return null;
    }

    @Override
    public Iterable<Producator> findAll() {
        return null;
    }

    @Override
    public Producator save(Producator entity) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Producator entity) {

    }
}
