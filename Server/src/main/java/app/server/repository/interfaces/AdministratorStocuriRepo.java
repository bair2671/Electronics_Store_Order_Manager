package app.server.repository.interfaces;

import app.server.model.AdministratorStocuri;
import app.server.repository.Repository;

public interface AdministratorStocuriRepo extends Repository<AdministratorStocuri> {
    AdministratorStocuri findOneByUsername(String username);
}
