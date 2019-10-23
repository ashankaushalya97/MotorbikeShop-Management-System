package dao;

import entity.SuperEntity;

import java.util.List;

public interface CrudDAO <T extends SuperEntity,ID> extends SuperDAO {

    public List<T> findAll() throws Exception;

    public T find(ID id) throws Exception;

    public boolean save(T entity) throws Exception;

    public boolean update(T entity) throws Exception;

    public boolean delete(ID id) throws Exception;
}
