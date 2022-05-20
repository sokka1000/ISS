package persistence;

import bibliotecamodel.Imprumut;

import java.util.List;

public interface RepositoryImprumut extends IRepository<Integer, Imprumut>{
    List<Imprumut> getImprumuturiActiveAbonat(int idAbonat);
    Imprumut getImprumutActivCarteAbonat(int codCarte, int idAbonat);
    Imprumut getImprumutActivCarte(int codCarte);
}