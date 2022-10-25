package app.server.repository.interfaces;

import app.server.model.Producator;
import app.server.repository.Repository;

public interface ProducatorRepo extends Repository<Producator> {
    Producator findOneByName(String name);
}
