package app.server.services;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {
    void update() throws AppException, RemoteException;
}
