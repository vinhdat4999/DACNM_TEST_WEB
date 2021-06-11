package com.blas.fish.dao;

import java.util.List;

import com.blas.fish.model.Category;

public interface CategoryDAO {
	
	public Category findCategory(String id);
	
	public List<Category> getCategoryList();
	
	public void save(Category category);
	
	public void deleteCategory(String id);
	
	public boolean checkHaveProductInCategory(String id);
}
