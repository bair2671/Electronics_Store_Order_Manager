package app.server.repository.interfaces;

import app.server.model.Producator;
import app.server.model.StocProdus;
import app.server.repository.Repository;

public interface StocProdusRepo extends Repository<StocProdus> {
    StocProdus findOneByProdus(int idProdus);
}
