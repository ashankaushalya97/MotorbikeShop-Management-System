package business.custom;

import business.SuperBO;
import dto.CategoryDTO;

import java.util.List;

public interface CategoryBO extends SuperBO {
    public List<CategoryDTO> getCategories()throws Exception;
}
