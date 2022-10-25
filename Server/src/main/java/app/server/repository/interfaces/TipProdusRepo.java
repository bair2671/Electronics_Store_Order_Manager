package app.server.repository.interfaces;

import app.server.model.Producator;
import app.server.model.TipProdus;
import app.server.repository.Repository;

public interface TipProdusRepo extends Repository<TipProdus> {
    TipProdus findOneByName(String name);
}
