package app.server.repository.interfaces;

import app.server.model.AdministratorComenzi;
import app.server.repository.Repository;

public interface AdministratorComenziRepo extends Repository<AdministratorComenzi> {
    AdministratorComenzi findOneByUsername(String username) throws Exception;
}
