package com.blas.fish.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.blas.fish.model.Fish;
import com.blas.fish.model.PaginationResult;

public interface FishDAO {

	public Fish findFish(String fishId);
	
	public List<Fish> getPopularFish();
	
	public List<Fish> getStandoutFish();

	public List<Fish> getFishList();

	public PaginationResult<Fish> queryFishes(int page, int maxResult, int maxNavigationPage);

	public PaginationResult<Fish> queryFishes(int page, int maxResult, int maxNavigationPage,
			String likeName);

	public PaginationResult<Fish> queryFishesByCategory(int page, int maxResult, int maxNavigationPage,
			String likeName);

	public PaginationResult<Fish> queryFishesByCategorySortPrice(int page, int maxResult,
			int maxNavigationPage, String likeName, String sortType);

	public PaginationResult<Fish> searchFish(int page, int maxResult, int maxNavigationPage,
			String likeName);
	
	public PaginationResult<Fish> searchAllFish(int page, int maxResult, int maxNavigationPage, String likeName);

	public PaginationResult<Fish> searchFishSortPrice(int page, int maxResult, int maxNavigationPage,
			String likeName, String sortType);

	public void save(Fish fish);
	
	public void toggleSellingFish(String fishId);
	
	public void IncViewOfFish(String fishId);
}
