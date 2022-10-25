package app.server.server;

import app.server.model.*;
import app.server.model.validator.ClientValidator;
import app.server.model.validator.ValidationException;
import app.server.repository.interfaces.*;
import app.server.services.AppException;
import app.server.services.IService;
import app.server.services.Observer;
import app.server.utils.Cryptographer;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IService {
    private ClientRepo clientRepo;
    private ComandaRepo comandaRepo;
    private OperatorRepo operatorRepo;
    private AdministratorComenziRepo adminComenziRepo;
    private AdministratorStocuriRepo adminStocuriRepo;
    private ProducatorRepo producatorRepo;
    private ProdusComandaRepo produsComandaRepo;
    private ProdusRepo produsRepo;
    private StocProdusRepo stocProdusRepo;
    private TipProdusRepo tipProdusRepo;

    private ClientValidator clientValidator;

    private ConcurrentLinkedQueue<Observer> loggedClients;

    public Service(OperatorRepo operatorRepo,AdministratorComenziRepo adminComenziRepo,AdministratorStocuriRepo adminStocuriRepo,
                   ClientRepo clientRepo, ComandaRepo comandaRepo,ProducatorRepo producatorRepo, ProdusComandaRepo produsComandaRepo,
                   ProdusRepo produsRepo, StocProdusRepo stocProdusRepo, TipProdusRepo tipProdusRepo) {
        this.clientRepo = clientRepo;
        this.comandaRepo = comandaRepo;
        this.operatorRepo = operatorRepo;
        this.adminComenziRepo = adminComenziRepo;
        this.adminStocuriRepo = adminStocuriRepo;
        this.producatorRepo = producatorRepo;
        this.produsComandaRepo = produsComandaRepo;
        this.produsRepo = produsRepo;
        this.stocProdusRepo = stocProdusRepo;
        this.tipProdusRepo = tipProdusRepo;

        this.clientValidator = new ClientValidator();

        loggedClients = new ConcurrentLinkedQueue<>();
    }

    @Override
    public Angajat login(String tipAngajat, String username, String password) throws Exception {
        Cryptographer cryptographer = new Cryptographer();
        if(tipAngajat.equals("operator")) {
            Operator operator = operatorRepo.findOneByUsername(username);
            if (operator != null) {
                if (!operator.getPassword().equals(password))
                    throw new AppException("Parola incorecta!");
                return operator;
            } else
                throw new AppException("Nu exista operator cu acest username!");
        }
        else if(tipAngajat.equals("administrator comenzi")) {
            AdministratorComenzi admin = adminComenziRepo.findOneByUsername(username);
            if (admin != null) {
                if (!admin.getPassword().equals(password))
                    throw new AppException("Parola incorecta!");
                return admin;
            } else
                throw new AppException("Nu exista administrator comenzi cu acest username!");
        }
        else if(tipAngajat.equals("administrator stocuri")) {
            AdministratorStocuri admin = adminStocuriRepo.findOneByUsername(username);
            if (admin != null) {
                if (!admin.getPassword().equals(password))
                    throw new AppException("Parola incorecta!");
                return admin;
            } else
                throw new AppException("Nu exista administrator stocuri cu acest username!");
        }
        return null;
    }

    @Override
    public synchronized void addObserver(Observer client) throws AppException {
        loggedClients.add(client);
    }

    @Override
    public synchronized void logout(Observer client) throws AppException {
        if (!loggedClients.remove(client))
            throw new AppException("Angajatul nu este logat in aplicatie!");
    }

    public Operator findOneOperator(Integer id){
        return operatorRepo.findOne(id);
    }

    public Operator findOneOperatorByUsername(String string){
        return operatorRepo.findOneByUsername(string);
    }

    @Override
    public Produs[] findAllProduse() throws AppException {
        Iterable<Produs> produse = produsRepo.findAll();
        Set<Produs> result=new HashSet<Produs>();
        for (Produs produs : produse){
            result.add(produs);
        }
        return result.toArray(new Produs[result.size()]);
    }

    @Override
    public TipProdus[] findAllTipuri() throws AppException {
        Iterable<TipProdus> tipuri = tipProdusRepo.findAll();
        Set<TipProdus> result= new HashSet<>();
        for (TipProdus tipProdus : tipuri){
            result.add(tipProdus);
        }
        return result.toArray(new TipProdus[result.size()]);
    }

    @Override
    public Producator[] findAllProducatori() throws AppException {
        Iterable<Producator> producatori = producatorRepo.findAll();
        Set<Producator> result=new HashSet<Producator>();
        for (Producator producator : producatori){
            result.add(producator);
        }
        return result.toArray(new Producator[result.size()]);
    }

    @Override
    public Comanda[] findComenziByStatus(StatusComanda status) throws AppException {
        Iterable<Comanda> comenzi = comandaRepo.findByStatus(status);
        Set<Comanda> result=new HashSet<Comanda>();
        for (Comanda comanda : comenzi){
            result.add(comanda);
        }
        return result.toArray(new Comanda[result.size()]);
    }

    @Override
    public ProdusComanda[] findProduseComenziByComanda(int idComanda) throws AppException {
        Iterable<ProdusComanda> produseComenzi = produsComandaRepo.findByComanda(idComanda);
        Set<ProdusComanda> result=new HashSet<ProdusComanda>();
        for (ProdusComanda produsComanda : produseComenzi){
            result.add(produsComanda);
        }
        return result.toArray(new ProdusComanda[result.size()]);
    }

    @Override
    public Client addClient(Client client) throws AppException{
        try{
            clientValidator.validate(client);
        }
        catch (ValidationException ex){
            throw new AppException(ex.getMessage());
        }
        return clientRepo.save(client);
    }

    public Client findOneClientByEmail(String email){
        return clientRepo.findOneByEmail(email);
    }

    @Override
    public StocProdus findOneStocByProdus(int idProdus){
        return stocProdusRepo.findOneByProdus(idProdus);
    }

    @Override
    public void addProdusComanda(ProdusComanda produsComanda){
        produsComandaRepo.save(produsComanda);
    }

    @Override
    public Comanda addComanda(Comanda comanda) throws AppException {
        Comanda comanda1 =  comandaRepo.save(comanda);
        notifyObservers();
        return comanda1;
    }

    public Comanda findOneComandaByOperatorAndClient(int idOperator,int idClient){
       return comandaRepo.findOneByOperatorAndClient(idOperator,idClient);
    }

    @Override
    public void updateComanda(Comanda comanda) throws AppException {
        comandaRepo.update(comanda);
        notifyObservers();
    }

    @Override
    public void  updateStoc(StocProdus stoc) throws AppException {
        stocProdusRepo.update(stoc);
        notifyObservers();
    }

    @Override
    public void  updateProdus(Produs produs) throws AppException {
        produsRepo.update(produs);
        notifyObservers();
    }

    @Override
    public Produs findOneProdusByProducatorAndNume(String numeProducator, String nume){
        Producator producator = producatorRepo.findOneByName(numeProducator);
        if(producator==null)
            return null;
        return produsRepo.findOneByProducatorAndNume(producator.getId(),nume);
    }

    @Override
    public Producator findOneProducatorByName(String nume){
        return producatorRepo.findOneByName(nume);
    }

    @Override
    public TipProdus findOneTipByName(String nume){
        return tipProdusRepo.findOneByName(nume);
    }

    @Override
    public Producator addProducator(Producator producator) {
        return producatorRepo.save(producator);
    }

    @Override
    public TipProdus addTip(TipProdus tip) {
        return tipProdusRepo.save(tip);
    }

    @Override
    public Produs addProdus(Produs produs) {
        return produsRepo.save(produs);
    }

    @Override
    public StocProdus addStoc(StocProdus stoc) throws AppException {
        StocProdus stocProdus = stocProdusRepo.save(stoc);
        notifyObservers();
        return stocProdus;
    }

    @Override
    public void deleteStoc(int idStoc) throws AppException {
        stocProdusRepo.delete(idStoc);
        notifyObservers();
    }

    private final int defaultThreadsNo=5;
    @Override
    public void notifyObservers() throws AppException {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(Observer obs : loggedClients){
            executor.execute(() -> {
                try {
                    System.out.println("Notifying [" + obs + "] ");
                    obs.update();
                } catch (AppException | RemoteException e) {
                    System.err.println("Error notifying user " + e);
                }
            });
        }
        executor.shutdown();
    }

    @Override
    public void setRepos(ClientRepo clientRepo, ComandaRepo comandaRepo, OperatorRepo operatorRepo, AdministratorComenziRepo adminComenziRepo, AdministratorStocuriRepo adminStocuriRepo, ProducatorRepo producatorRepo, ProdusComandaRepo produsComandaRepo, ProdusRepo produsRepo, StocProdusRepo stocProdusRepo, TipProdusRepo tipProdusRepo) {
        this.clientRepo = clientRepo;
        this.comandaRepo = comandaRepo;
        this.operatorRepo = operatorRepo;
        this.adminComenziRepo = adminComenziRepo;
        this.adminStocuriRepo = adminStocuriRepo;
        this.producatorRepo = producatorRepo;
        this.produsComandaRepo = produsComandaRepo;
        this.produsRepo = produsRepo;
        this.stocProdusRepo = stocProdusRepo;
        this.tipProdusRepo = tipProdusRepo;
    }
}
