package business.custom.impl;

import business.custom.LoginBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.AdminDAO;
import dto.LoginDTO;
import entity.Admin;

public class LoginBOImpl implements LoginBO {
    AdminDAO adminDAO = DAOFactory.getInstance().getDAO(DAOTypes.ADMIN);

    @Override
    public boolean authentication(LoginDTO loginDTO) throws Exception {
        return adminDAO.authentication(new Admin(loginDTO.getUsename(),loginDTO.getPassword()));
    }

}
