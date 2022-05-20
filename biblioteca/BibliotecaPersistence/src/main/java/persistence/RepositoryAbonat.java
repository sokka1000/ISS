package persistence;

import bibliotecamodel.Abonat;

public interface RepositoryAbonat extends IRepository<Integer, Abonat> {
    Abonat findAbonatByUsernameParola(String username, String parola);
    Abonat findAbonatByUsername(String username);


}
