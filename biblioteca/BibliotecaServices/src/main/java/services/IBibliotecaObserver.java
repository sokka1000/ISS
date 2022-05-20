package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBibliotecaObserver extends Remote {
    void carteUpdated() throws BibliotecaException, RemoteException;
    void imprumutUpdated() throws BibliotecaException, RemoteException;

}