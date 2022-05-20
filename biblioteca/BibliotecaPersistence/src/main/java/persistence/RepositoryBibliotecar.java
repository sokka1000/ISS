package persistence;

import bibliotecamodel.Bibliotecar;

public interface RepositoryBibliotecar extends IRepository<Integer, Bibliotecar>{

    Bibliotecar findBibliotecarByUsernameParola(String username, String parola);
}
