package persistence;

import java.util.List;

public interface IRepository<ID, T> {
    void add(T elem) throws Exception;
    void delete(ID id);
    void update(T elem);
    T findById(ID id);
    List<T> findAll();

}