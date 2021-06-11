package com.blas.fish.dao;

import java.util.List;

import com.blas.fish.model.DetailImage;
import com.blas.fish.model.PaginationResult;

public interface ProductImageDAO {

	public DetailImage findProductImage(String imageId);

	public DetailImage findDetailImage(String imageId);

	public List<DetailImage> getImageIdList(String productId);

	public void deleteImage(String imageId);

	public void addImage(DetailImage productImage);

	public PaginationResult<DetailImage> queryProductImages(int page, int maxResult, int maxNavigationPage);

	public PaginationResult<DetailImage> queryProductImages(int page, int maxResult, int maxNavigationPage,
			String likeName);
}
