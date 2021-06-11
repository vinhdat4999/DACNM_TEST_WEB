package com.blas.fish.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blas.fish.dao.AdminDAO;
import com.blas.fish.dao.CategoryDAO;
import com.blas.fish.dao.FishDAO;
import com.blas.fish.dao.OrderDAO;
import com.blas.fish.dao.OrderDetailDAO;
import com.blas.fish.dao.ProductImageDAO;
import com.blas.fish.model.Admin;
import com.blas.fish.model.Category;
import com.blas.fish.model.CategoryForm;
import com.blas.fish.model.DetailImage;
import com.blas.fish.model.Fish;
import com.blas.fish.model.Order;
import com.blas.fish.model.OrderDetail;
import com.blas.fish.model.PaginationResult;
import com.blas.fish.model.ProductForm;
import com.blas.fish.model.UploadProductImageForm;
import com.blas.fish.utils.JDBCUtils;
import com.blas.fish.utils.ToURLId;

@Controller
@Transactional
@EnableWebMvc
public class AdminController {

		@Autowired
		private FishDAO fishDAO;
		
		@Autowired
		private AdminDAO adminDAO;
	//
//		@Autowired
//		private ProductImageDAO productImageDAO;

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private OrderDetailDAO orderDetailDAO;
	
	@Autowired
	private ProductImageDAO productImageDAO;

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(Model model) {
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		if (!username.equals("anonymousUser")) {
			return "redirect:/admin/order-list";
		}
		return "login";
	}

	@RequestMapping(value = { "admin/order-list" }, method = RequestMethod.GET)
	public String orderList(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		final int maxResult = 48;
		final int maxNavigationPage = 10;
		PaginationResult<Order> result;
		result = orderDAO.listOrder(page, maxResult, maxNavigationPage);

		System.out.println("number: " + orderDAO.numberOfWaitingOrder());
		model.addAttribute("paginationOrders", result);
		int numberOfWaitingOrder = orderDAO.numberOfWaitingOrder();
		model.addAttribute("numberOfWaitingOrder", numberOfWaitingOrder);
		return "orderList";
	}

	@RequestMapping(value = { "/admin/order/{id}" })
	public String order(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Order order = orderDAO.getOrder(id);
		List<OrderDetail> orderDetail = orderDetailDAO.getOrderDetail(id);
		model.addAttribute("order", order);
		model.addAttribute("orderDetailList", orderDetail);
		int numberOfWaitingOrder = orderDAO.numberOfWaitingOrder();
		model.addAttribute("numberOfWaitingOrder", numberOfWaitingOrder);
		return "order";
	}

	@RequestMapping(value = { "/admin/cancel/{id}" })
	public String cancel(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		orderDAO.cancelOrder(id);
		return "redirect:/admin/order/" + id;
	}

	@RequestMapping(value = { "/admin/done/{id}" })
	public String done(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		orderDAO.doneOrder(id);
		return "redirect:/admin/order/" + id;
	}

	@RequestMapping(value = { "/admin/delivering/{id}" })
	public String delivering(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		orderDAO.deliveringOrder(id);
		return "redirect:/admin/order/" + id;
	}

	@RequestMapping(value = { "admin/danh-muc" }, method = RequestMethod.GET)
	public String category(@RequestParam(value = "error", defaultValue = "") String error, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Category> categoryList = categoryDAO.getCategoryList();
		int numberOfWaitingOrder = orderDAO.numberOfWaitingOrder();
		if(error.equals("cannot-delete")) {
			model.addAttribute("error","cannot-delete");
		}
		model.addAttribute("numberOfWaitingOrder", numberOfWaitingOrder);
		model.addAttribute("categoryList", categoryList);
		return "categoryList";
	}

	@RequestMapping(value = { "admin/edit-category/{id}" }, method = RequestMethod.GET)
	public String editCategory(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Category category = categoryDAO.findCategory(id);
		int numberOfWaitingOrder = orderDAO.numberOfWaitingOrder();
		CategoryForm categoryForm = new CategoryForm();
		categoryForm.setId(category.getId());
		categoryForm.setName(category.getName());
		model.addAttribute("numberOfWaitingOrder", numberOfWaitingOrder);
		model.addAttribute("categoryForm", categoryForm);
		return "editCategory";
	}

	@RequestMapping(value = { "admin/edit-category/{id}" }, method = RequestMethod.POST)
	@Transactional(propagation = Propagation.NEVER)
	public String productSave(@PathVariable("id") String id, HttpServletRequest request, Model model, //
			@ModelAttribute("categoryForm") @Validated CategoryForm category, CategoryForm uploadImage, BindingResult result, //
            final RedirectAttributes redirectAttributes) {
		Category category2 = null;
		if(category.getFileDatas().getBytes().length==0) {
			Category temp = categoryDAO.findCategory(id);
			category2 = new Category(category.getId(), category.getName(), temp.getImage());
		}else {
			category2 = new Category(category.getId(), category.getName(), category.getFileDatas().getBytes());
		}
		System.out.println(category2.toString());
		categoryDAO.save(category2);
		return "redirect:/admin/danh-muc";
	}

	@RequestMapping(value = { "admin/add-category" }, method = RequestMethod.GET)
	public String addCategory(@RequestParam(value = "error", defaultValue = "") String error, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if(error.equals("duplicated")) {
			model.addAttribute("error","duplicated");
		}
		int numberOfWaitingOrder = orderDAO.numberOfWaitingOrder();
		CategoryForm categoryForm = new CategoryForm();
		model.addAttribute("numberOfWaitingOrder", numberOfWaitingOrder);
		model.addAttribute("categoryForm", categoryForm);
		return "editCategory";
	}

	@RequestMapping(value = { "admin/add-category" }, method = RequestMethod.POST)
	@Transactional(propagation = Propagation.NEVER)
	public String addproductSave(HttpServletRequest request, Model model, //
			@ModelAttribute("categoryForm") @Validated CategoryForm category, CategoryForm uploadImage, BindingResult result, //
            final RedirectAttributes redirectAttributes) {
		String id = ToURLId.toURLId(category.getName());
		System.out.println("id: " + id);
		if(categoryDAO.findCategory(id)!=null) {
			System.out.println("not null");
			return "redirect:/admin/add-category?error=duplicated";
		}
		Category category2 = new Category(ToURLId.toURLId(category.getName()), category.getName(), category.getFileDatas().getBytes());
		System.out.println("ffffff:"+category2.getImage().length);
		categoryDAO.save(category2);
		return "redirect:/admin/danh-muc";
	}

	@RequestMapping(value = { "admin/delete-category/{id}" }, method = RequestMethod.GET)
	public String deleteCategory(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("da vao day");
		if(!categoryDAO.checkHaveProductInCategory(id)) {
			categoryDAO.deleteCategory(id);	
		}else {
			return "redirect:/admin/danh-muc?error=cannot-delete";
		}
		return "redirect:/admin/danh-muc";
	}

	@RequestMapping(value = { "admin/san-pham" }, method = RequestMethod.GET)
	public String listProductHandler(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		System.out.println("ff");
		final int maxResult = 48;
		final int maxNavigationPage = 10;
		PaginationResult<Fish> result;
		result = fishDAO.searchAllFish(page, //
				maxResult, maxNavigationPage, likeName);
		model.addAttribute("paginationProducts", result);
		int numberOfWaitingOrder = orderDAO.numberOfWaitingOrder();
		model.addAttribute("numberOfWaitingOrder", numberOfWaitingOrder);
		return "manageProduct";
	}

	@RequestMapping(value = { "admin/add-product" }, method = RequestMethod.GET)
	public String addProduct(@RequestParam(value = "error", defaultValue = "") String error, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if(error.equals("duplicated")) {
			model.addAttribute("error","duplicated");
		}
		int numberOfWaitingOrder = orderDAO.numberOfWaitingOrder();
		ProductForm productForm = new ProductForm();
		List<Category> list = categoryDAO.getCategoryList();
		model.addAttribute("categoryList", list);
		model.addAttribute("numberOfWaitingOrder", numberOfWaitingOrder);
		model.addAttribute("productForm", productForm);
		return "addProduct";
	}

	@RequestMapping(value = { "admin/add-product" }, method = RequestMethod.POST)
	@Transactional(propagation = Propagation.NEVER)
	public String addProduct(HttpServletRequest request, Model model, //
			@ModelAttribute("productForm") @Validated ProductForm productForm, CategoryForm uploadImage, BindingResult result, //
            final RedirectAttributes redirectAttributes) {
		String id = ToURLId.toURLId(productForm.getName());
		System.out.println("id: " + id);
		if(fishDAO.findFish(id)!=null) {
			System.out.println("not null");
			return "redirect:/admin/add-product?error=duplicated";
		}
		Category category = categoryDAO.findCategory(productForm.getCategoryId());
		Fish fish = new Fish(ToURLId.toURLId(productForm.getName()), productForm.getName(), category, productForm.getImage().getBytes(), productForm.getPriceOrigin(), productForm.getPricePromo(), productForm.getDescription(), true, productForm.getCount(), productForm.getView(), true);
		fishDAO.save(fish);
		return "redirect:/admin/san-pham";
	}

	@RequestMapping(value = { "admin/edit-product/{id}" }, method = RequestMethod.GET)
	public String editProduct(@PathVariable("id") String id, @RequestParam(value = "error", defaultValue = "") String error, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if(error.equals("duplicated")) {
			model.addAttribute("error","duplicated");
		}
		Fish fish = fishDAO.findFish(id);
		ProductForm productForm = new ProductForm(fish.getId(), fish.getName(), fish.getCategory().getId(), null, fish.getPriceOrigin(), fish.getPricePromo(), fish.getDescription(), fish.isIsSelling(), fish.getCount(), fish.getView(), fish.isIsInStock());
		int numberOfWaitingOrder = orderDAO.numberOfWaitingOrder();
		List<Category> list = categoryDAO.getCategoryList();
		UploadProductImageForm myUploadForm = new UploadProductImageForm();
		List<DetailImage> listLink = productImageDAO.getImageIdList(id);
		model.addAttribute("listLink", listLink);
		model.addAttribute("myUploadForm", myUploadForm);
		model.addAttribute("categoryList", list);
		model.addAttribute("numberOfWaitingOrder", numberOfWaitingOrder);
		model.addAttribute("productForm", productForm);
		return "editProduct";
	}

	@RequestMapping(value = { "admin/change-password" }, method = RequestMethod.GET)
	public String changepassword(@RequestParam(value = "error", defaultValue = "") String error, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if(error.equals("wrong-password")) {
			System.out.println("dfsdfsffdsf");
			model.addAttribute("error","wrong-password");
		}
		return "changePassword";
	}

	@RequestMapping(value = { "admin/change-password" }, method = RequestMethod.POST)
	public String changepasswordHandler(HttpServletRequest request, HttpServletResponse response, Model model) {
		String oldPass = request.getParameter("oldPass");
		String newPass = request.getParameter("newPass");
		if(new JDBCUtils().isExistThisId("admin", oldPass)) {
			Admin admin = adminDAO.findAdmin("admin");
			admin.setPassword(newPass);
			adminDAO.save(admin);
		}else {
			return "redirect:/admin/change-password?error=wrong-password";
		}
		return "redirect:/admin/order-list";
	}

	@RequestMapping(value = { "admin/edit-product/{id}" }, method = RequestMethod.POST)
	@Transactional(propagation = Propagation.NEVER)
	public String editProduct(@PathVariable("id") String id, HttpServletRequest request, Model model,
			@ModelAttribute("myUploadForm") @Validated ProductForm productForm, UploadProductImageForm uploadImage, BindingResult result, //
            final RedirectAttributes redirectAttributes) {
		Fish fish = null;
		if(productForm.getImage().getBytes().length==0) {
			Fish temp = fishDAO.findFish(id);
			Category category = categoryDAO.findCategory(productForm.getCategoryId());
			fish = new Fish(productForm.getId(), productForm.getName(), category, temp.getImage(), productForm.getPriceOrigin(), productForm.getPricePromo(), productForm.getDescription(), productForm.isIsSelling(), productForm.getCount(), productForm.getView(), productForm.isIsInStock());
		}else {
			Category category = categoryDAO.findCategory(productForm.getCategoryId());
			fish = new Fish(productForm.getId(), productForm.getName(), category, productForm.getImage().getBytes(), productForm.getPriceOrigin(), productForm.getPricePromo(), productForm.getDescription(), productForm.isIsSelling(), productForm.getCount(), productForm.getView(), productForm.isIsInStock());
		}
		String isSelling = request.getParameter("isSelling");
//		if(isSelling.equals("conhang")) {
//			fish.setIsSelling(true);
//		}else {
//			fish.setIsSelling(false);
//		}
//		String isInStock = request.getParameter("isInStock");
//		if(isInStock.equals("dangban")) {
//			fish.setIsInStock(true);
//		}else {
//			fish.setIsInStock(false);
//		}
		fishDAO.save(fish);
		

		CommonsMultipartFile[] fileDatas = uploadImage.getFileDatas();
		int i = 0; 
		List<DetailImage> list = productImageDAO.getImageIdList(id);
		for (CommonsMultipartFile fileData : fileDatas) {
			i++;
			if (fileData.getSize() != 0) {
				if(i<fileDatas.length) {
					productImageDAO.deleteImage(list.get(i-1).getId());	
				}
				Fish fish2 = fishDAO.findFish(id);
				DetailImage temp = new DetailImage(UUID.randomUUID().toString(), fish2, fileData.getBytes());
				productImageDAO.addImage(temp);
				if(i==fileDatas.length) {
					return "redirect:/admin/edit-product/" + id;
				}
			}
		}
		
		return "redirect:/admin/san-pham";
	}
	
	@RequestMapping("admin/delete-image")
	public String deleteImage(Model model, //
			@RequestParam(value = "id", defaultValue = "") String id) {
		DetailImage detailImage = productImageDAO.findProductImage(id);
		String fishId=detailImage.getFish().getId();
		productImageDAO.deleteImage(id);
		return "redirect:/admin/edit-product/" + fishId;
	}
}
