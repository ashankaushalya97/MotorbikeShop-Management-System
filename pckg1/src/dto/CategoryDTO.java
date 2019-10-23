package dto;

public class CategoryDTO {
    private String categoryId;
    private String description;

    public CategoryDTO() {
    }

    public CategoryDTO(String categoryId, String description) {
        this.categoryId = categoryId;
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
