package business.custom;

import business.SuperBO;
import dto.LoginDTO;

public interface LoginBO extends SuperBO {

    boolean authentication (LoginDTO loginDTO) throws Exception;

}
