package app.server.repository.interfaces;

import app.server.model.Client;
import app.server.model.Comanda;
import app.server.model.StatusComanda;
import app.server.repository.Repository;

public interface ComandaRepo extends Repository<Comanda> {
    Comanda findOneByOperatorAndClient(int idOperator,int idClient);
    Iterable<Comanda> findByStatus(StatusComanda status);
}
