package app.server.services;


import app.server.model.*;
import app.server.repository.interfaces.*;

public interface IService {
     Angajat login(String tipAngajat, String username, String password) throws Exception;
     void addObserver(Observer client) throws AppException;
     void logout(Observer client) throws AppException;
     Produs[] findAllProduse() throws AppException;
     TipProdus[] findAllTipuri() throws AppException;
     Producator[] findAllProducatori() throws AppException;
     Comanda[] findComenziByStatus(StatusComanda status) throws AppException;
     ProdusComanda[] findProduseComenziByComanda(int idComanda) throws AppException;
     Client addClient(Client client) throws AppException;
     StocProdus findOneStocByProdus(int idProdus) throws AppException;
     void addProdusComanda(ProdusComanda produsComanda) throws AppException;
     Comanda addComanda(Comanda comanda) throws AppException;
     void updateComanda(Comanda comanda) throws AppException;
     void  updateStoc(StocProdus stoc) throws AppException;
     void  updateProdus(Produs produs) throws AppException;
     Produs findOneProdusByProducatorAndNume(String numeProducator, String nume) throws AppException;
     Producator findOneProducatorByName(String nume) throws AppException;
     TipProdus findOneTipByName(String nume) throws AppException;
     Producator addProducator(Producator producator) throws AppException;
     TipProdus addTip(TipProdus tip) throws AppException;
     Produs addProdus(Produs produs) throws AppException;
     StocProdus addStoc(StocProdus stoc) throws AppException;
     void deleteStoc(int idStoc) throws AppException;
     void notifyObservers() throws AppException;

     void setRepos(ClientRepo clientRepo,
             ComandaRepo comandaRepo,
             OperatorRepo operatorRepo,
             AdministratorComenziRepo adminComenziRepo,
             AdministratorStocuriRepo adminStocuriRepo,
             ProducatorRepo producatorRepo,
             ProdusComandaRepo produsComandaRepo,
             ProdusRepo produsRepo,
             StocProdusRepo stocProdusRepo,
             TipProdusRepo tipProdusRepo);
}
