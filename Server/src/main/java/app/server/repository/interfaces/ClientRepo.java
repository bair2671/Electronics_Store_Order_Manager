package app.server.repository.interfaces;

import app.server.model.Client;
import app.server.model.Operator;
import app.server.repository.Repository;

public interface ClientRepo extends Repository<Client> {
    Client findOneByEmail(String email);
}
