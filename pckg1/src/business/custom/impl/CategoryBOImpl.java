package business.custom.impl;

import business.custom.CategoryBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.CategoryDAO;
import dto.CategoryDTO;
import entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryBOImpl implements CategoryBO {
    CategoryDAO categoryDAO = DAOFactory.getInstance().getDAO(DAOTypes.CATEGORY);
    @Override
    public List<CategoryDTO> getCategories() throws Exception {
        List<CategoryDTO> categories = new ArrayList<>();
        List<Category> all = categoryDAO.findAll();
        for (Category category : all) {
            categories.add(new CategoryDTO(category.getCategoryId(),category.getDescription()));
        }
        return categories;
    }
}
