//package com.blas.fish.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import com.blas.fish.dao.FishDAO;
//import com.blas.fish.model2.PaginationResult;
//import com.blas.fish.model2.ProductModel;
//
//@Controller
//@Transactional
//@EnableWebMvc
//public class CategoryController {
//
//	@Autowired
//	private FishDAO productDAO;
//
//	@RequestMapping("/banh")
//	public String listInCategoryBait(Model model, //
//			@RequestParam(value = "name", defaultValue = "BÃ¡nh") String likeName,
//			@RequestParam(value = "page", defaultValue = "1") int page,
//			@RequestParam(value = "sort", defaultValue = "") String type) {
//		final int maxResult = 48;
//		final int maxNavigationPage = 10;
//		PaginationResult<ProductModel> result;
//		if (type.equals("price-inc")) {
//			result = productDAO.queryProductsByCategorySortPrice(page, //
//					maxResult, maxNavigationPage, likeName, "asc");
//		} else {
//			if (type.equals("price-des")) {
//				result = productDAO.queryProductsByCategorySortPrice(page, //
//						maxResult, maxNavigationPage, likeName, "desc");
//			} else {
//				result = productDAO.queryProductsByCategory(page, //
//						maxResult, maxNavigationPage, likeName);
//			}
//		}
//		model.addAttribute("paginationProducts", result);
//		return "productList";
//	}
//
//	@RequestMapping("/keo")
//	public String listInCategoryBeer(Model model, //
//			@RequestParam(value = "name", defaultValue = "Káº¹o") String likeName,
//			@RequestParam(value = "page", defaultValue = "1") int page,
//			@RequestParam(value = "sort", defaultValue = "") String type) {
//		final int maxResult = 48;
//		final int maxNavigationPage = 10;
//		PaginationResult<ProductModel> result;
//		if (type.equals("price-inc")) {
//			result = productDAO.queryProductsByCategorySortPrice(page, //
//					maxResult, maxNavigationPage, likeName, "asc");
//		} else {
//			if (type.equals("price-des")) {
//				result = productDAO.queryProductsByCategorySortPrice(page, //
//						maxResult, maxNavigationPage, likeName, "desc");
//			} else {
//				result = productDAO.queryProductsByCategory(page, //
//						maxResult, maxNavigationPage, likeName);
//			}
//		}
//		model.addAttribute("paginationProducts", result);
//		return "productList";
//	}
//
//	@RequestMapping("/nuoc-ngot")
//	public String listInCategoryDrink(Model model, //
//			@RequestParam(value = "name", defaultValue = "NÆ°á»›c ngá»�t") String likeName,
//			@RequestParam(value = "page", defaultValue = "1") int page,
//			@RequestParam(value = "sort", defaultValue = "") String type) {
//		final int maxResult = 48;
//		final int maxNavigationPage = 10;
//		PaginationResult<ProductModel> result;
//		if (type.equals("price-inc")) {
//			result = productDAO.queryProductsByCategorySortPrice(page, //
//					maxResult, maxNavigationPage, likeName, "asc");
//		} else {
//			if (type.equals("price-des")) {
//				result = productDAO.queryProductsByCategorySortPrice(page, //
//						maxResult, maxNavigationPage, likeName, "desc");
//			} else {
//				result = productDAO.queryProductsByCategory(page, //
//						maxResult, maxNavigationPage, likeName);
//			}
//		}
//		model.addAttribute("paginationProducts", result);
//		return "productList";
//	}
//}
