package persistence;

import bibliotecamodel.Carte;

import java.util.List;

public interface RepositoryCarte extends IRepository<Integer, Carte> {
    List<Carte> toateCartileDisponibile();
}