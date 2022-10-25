package app.server.repository.interfaces;

import app.server.model.Produs;
import app.server.repository.Repository;

public interface ProdusRepo extends Repository<Produs> {
    Produs findOneByProducatorAndNume(int idProducator, String nume);
}
