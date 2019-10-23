package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import entity.Admin;

public interface AdminDAO extends CrudDAO<Admin,String> {

    boolean authentication(Admin admin)throws Exception;
}
