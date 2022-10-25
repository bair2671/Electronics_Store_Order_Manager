package app.server.repository.interfaces;

import app.server.model.ProdusComanda;
import app.server.repository.Repository;

public interface ProdusComandaRepo extends Repository<ProdusComanda> {
    Iterable<ProdusComanda> findByComanda(Integer comandaId);
}
