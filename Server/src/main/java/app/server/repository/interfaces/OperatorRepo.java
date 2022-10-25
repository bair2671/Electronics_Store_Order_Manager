package app.server.repository.interfaces;

import app.server.model.Operator;
import app.server.repository.Repository;

public interface OperatorRepo extends Repository<Operator> {
    Operator findOneByUsername(String username);
}
