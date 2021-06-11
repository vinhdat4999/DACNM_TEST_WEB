package com.blas.fish.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blas.fish.dao.CategoryDAO;
import com.blas.fish.dao.FishDAO;
import com.blas.fish.dao.OrderDAO;
import com.blas.fish.dao.OrderDetailDAO;
import com.blas.fish.dao.ProductImageDAO;
import com.blas.fish.model.Cart;
import com.blas.fish.model.CartItem;
import com.blas.fish.model.Category;
import com.blas.fish.model.DetailImage;
import com.blas.fish.model.Fish;
import com.blas.fish.model.Order;
import com.blas.fish.model.OrderDetail;
import com.blas.fish.model.PaginationResult;
import com.blas.fish.utils.CartUtil;
import com.blas.fish.utils.SendEmail;
import com.google.gson.Gson;

@Controller
@Transactional
@EnableWebMvc
public class ClientController {

	@Autowired
	private FishDAO fishDAO;

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private OrderDetailDAO orderDetailDAO;

	@Autowired
	private ProductImageDAO productImageDAO;

//	@Autowired
//	private ProductImageDAO productImageDAO;

	@RequestMapping("/")
	public String listProductHandler(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(value = "name", defaultValue = "") String likeName,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "sort", defaultValue = "") String type,
			@CookieValue(value = "cartListStr", defaultValue = "") String cartListStr) {
		System.out.println("ff");
		final int maxResult = 48;
		final int maxNavigationPage = 10;
		PaginationResult<Fish> result;
		if (type.equals("price-inc")) {
			result = fishDAO.searchFishSortPrice(page, //
					maxResult, maxNavigationPage, likeName, "asc");
		} else {
			if (type.equals("price-des")) {
				result = fishDAO.searchFishSortPrice(page, //
						maxResult, maxNavigationPage, likeName, "desc");
			} else {
				result = fishDAO.searchFish(page, //
						maxResult, maxNavigationPage, likeName);
			}
		}
		List<Category> list = categoryDAO.getCategoryList();
		List<Fish> prdList = fishDAO.getStandoutFish();

		Random rng = new Random();
		List<Fish> fishList = new ArrayList<Fish>();
		fishList = fishDAO.getFishList();
		Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() < 9) {
			Integer next = rng.nextInt(fishList.size());
			if (fishList.get(next).isIsSelling()) {
				generated.add(next);
			}
		}
		List<Fish> standoutList = new ArrayList<Fish>();
		for (Integer i : generated) {
			standoutList.add(fishList.get(i));
		}
		List<Cart> cartList = CartUtil.stringToList(cartListStr);
		int numberOfItem = 0;
		for (Cart cart : cartList) {
			numberOfItem += cart.getQuantity();
		}
		System.out.println("number: " + numberOfItem);
		model.addAttribute("paginationProducts", result);
		model.addAttribute("prdList", prdList);
		model.addAttribute("prd1", prdList.get(0));
		model.addAttribute("prd2", prdList.get(1));
		model.addAttribute("prd3", prdList.get(2));
		model.addAttribute("categoryList", list);
		model.addAttribute("standoutList", standoutList);
		model.addAttribute("numberOfItem", numberOfItem);
		System.out.println("homepage");
		return "homePage";
	}

	@RequestMapping(value = { "/category/{categoryId}" })
	public String listInCategory1(Model model, @PathVariable("categoryId") String categoryId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "sort", defaultValue = "") String type,
			@CookieValue(value = "cartListStr", defaultValue = "") String cartListStr) {
		final int maxResult = 20;
		final int maxNavigationPage = 10;
		PaginationResult<Fish> result;
		if (type.equals("price-inc")) {
			result = fishDAO.queryFishesByCategorySortPrice(page, //
					maxResult, maxNavigationPage, categoryId, "asc");
		} else {
			if (type.equals("price-des")) {
				result = fishDAO.queryFishesByCategorySortPrice(page, //
						maxResult, maxNavigationPage, categoryId, "desc");
			} else {
				result = fishDAO.queryFishesByCategory(page, //
						maxResult, maxNavigationPage, categoryId);
			}
		}

		List<Cart> cartList = CartUtil.stringToList(cartListStr);
		int numberOfItem = 0;
		for (Cart cart : cartList) {
			numberOfItem += cart.getQuantity();
		}
		List<Category> list = categoryDAO.getCategoryList();
		Category category = categoryDAO.findCategory(categoryId);
		model.addAttribute("categoryList", list);
		model.addAttribute("category", category);
		model.addAttribute("numberOfItem", numberOfItem);
		model.addAttribute("paginationProducts", result);
		return "productList";
	}

	@RequestMapping(value = { "/product/{id}" }, method = RequestMethod.GET)
	public String product(HttpServletRequest request, HttpServletResponse response, Model model,
//			@RequestParam("id") String id, @PathVariable("id") String id ) throws IOException {
			@PathVariable("id") String id, @CookieValue(value = "cartListStr", defaultValue = "") String cartListStr)
			throws IOException {
		Fish productModel = null;
		if (id != null) {
			productModel = this.fishDAO.findFish(id);
			if (productModel == null || !productModel.isIsSelling()) {
				model.addAttribute("productNotFound", "true");
				List<Fish> prdList = fishDAO.getStandoutFish();
				model.addAttribute("prdList", prdList);
				return "product";
			}
		}
		String description = "";
		if (productModel != null) {
//			String priceStr = String.format("%,d", (int) productModel.getPrice());
//			List<ProductImageModel> list = productImageDAO.getImageIdList(id);
//			model.addAttribute("list", list);
//			model.addAttribute("price", priceStr);
			if (productModel.getDescription() != null) {
				description = productModel.getDescription().replaceAll("\n", "</br>");
			}
			model.addAttribute("productInfo", productModel);
		}
		if (productModel.getDescription() != null) {
			for (int i = 0; i < productModel.getDescription().length(); i++) {
				System.out.println("'" + productModel.getDescription().charAt(i) + "'");
				if (productModel.getDescription().charAt(i) == '\n') {
					System.out.println("enter");
				}
			}
		}
		List<Cart> tempList = CartUtil.stringToList(cartListStr);
		int numberOfItem = 0;
		for (Cart cart : tempList) {
			numberOfItem += cart.getQuantity();
		}
		model.addAttribute("numberOfItem", numberOfItem);

		List<Fish> popularList = fishDAO.getStandoutFish();
		model.addAttribute("popularList", popularList);
		List<Fish> prdList = fishDAO.getPopularFish();
		List<DetailImage> detailImgList = productImageDAO.getImageIdList(id);
		model.addAttribute("prdList", prdList);
		model.addAttribute("description", description);
		model.addAttribute("detailImgList", detailImgList);
		fishDAO.IncViewOfFish(id);
		return "product";
	}

	@RequestMapping(value = { "/product/{id}" }, method = RequestMethod.POST)
	public String buyProduct(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable("id") String id, @CookieValue(value = "cartListStr", defaultValue = "") String cartListStr) {
		int quanity = 0;
		if (request.getParameter("quantityItem") == null) {
			quanity = 1;
		} else {
			quanity = Integer.parseInt(request.getParameter("quantityItem"));
		}
		boolean appear = false;
		System.out.println("ddd" + cartListStr);
		List<Cart> cartList = CartUtil.stringToList(cartListStr);
		for (Cart i : cartList) {
			System.out.println(i.toString());
		}
		for (Cart i : cartList) {
			if (i.getFishId().equals(id)) {
				i.setQuantity(i.getQuantity() + quanity);
				appear = true;
			}
		}
		if (appear == false) {
			cartList.add(new Cart(id, quanity));
		}
		Cookie cookie = new Cookie("cartListStr", CartUtil.listToString(cartList));
		cookie.setMaxAge(864000);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/cart";
	}

	@RequestMapping(value = { "/category-image" }, method = RequestMethod.GET)
	public void categoryImage(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("id") String id) throws IOException {
		Category category = null;
		if (id != null) {
			category = categoryDAO.findCategory(id);
		}
		if (category != null && category.getImage() != null) {
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(category.getImage());
		}
		response.getOutputStream().close();
	}

	@RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
	public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("id") String id) throws IOException {
		Fish fish = null;
		if (id != null) {
			fish = fishDAO.findFish(id);
		}
		if (fish != null && fish.getImage() != null) {
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(fish.getImage());
		}
		response.getOutputStream().close();
	}

	@RequestMapping(value = { "/detailImage" }, method = RequestMethod.GET)
	public void detailImage(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("id") String id) throws IOException {
		DetailImage detailImage = null;
		if (id != null) {
			detailImage = productImageDAO.findProductImage(id);
		}
		if (detailImage != null && detailImage.getImage() != null) {
			System.out.println("OK");
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(detailImage.getImage());
		}
		response.getOutputStream().close();
	}

	@RequestMapping({ "/search" })
	public String searchItem(HttpServletRequest request, Model model, //
			@RequestParam(value = "content", defaultValue = "") String content,
			@RequestParam(value = "sort", defaultValue = "") String type,
			@CookieValue(value = "cartListStr", defaultValue = "") String cartListStr) {
		final int maxResult = 48;
		final int maxNavigationPage = 10;
		PaginationResult<Fish> result;
		if (type.equals("price-inc")) {
			result = fishDAO.searchFishSortPrice(1, //
					maxResult, maxNavigationPage, content, "asc");
		} else {
			if (type.equals("price-des")) {
				result = fishDAO.searchFishSortPrice(1, //
						maxResult, maxNavigationPage, content, "desc");
			} else {
				result = fishDAO.searchFish(1, //
						maxResult, maxNavigationPage, content);
			}
		}

		List<Cart> cartList = CartUtil.stringToList(cartListStr);
		int numberOfItem = 0;
		for (Cart cart : cartList) {
//			System.out.println("AAAA" + cart.toString());
			numberOfItem += cart.getQuantity();
		}
		List<Category> list = categoryDAO.getCategoryList();
		model.addAttribute("categoryList", list);
		model.addAttribute("paginationProducts", result);
		model.addAttribute("numberOfItem", numberOfItem);
		model.addAttribute("searchContent", content);
		return "searchPage";
	}

	@RequestMapping({ "/cart" })
	public String shoppingCartHandler(HttpServletRequest request, HttpServletResponse response, Model model,
			@CookieValue(value = "cartListStr", defaultValue = "") String cartListStr) {
		List<Cart> itemList = CartUtil.stringToList(cartListStr);
		System.out.println("aaaaaaaaaaaaa");

		//////////////
//		List<Cart> itemList = new ArrayList<Cart>();
//		List<Fish> fishList = fishDAO.getPopularFish();
//		System.out.println("sd" + fishList.size());
//		for (Fish i : fishList) {
//			itemList.add(new Cart(i.getId(), 2));
//		}
		/////////////
		List<CartItem> cartList = new ArrayList<CartItem>();
		double total = 0;
		for (Cart cart : itemList) {
			Fish fishTemp = fishDAO.findFish(cart.getFishId());
			if (!fishTemp.isIsSelling() || !fishTemp.isIsInStock()) {
				return "redirect:/remove-product?id=" + fishTemp.getId();
			}
			Fish fish = fishDAO.findFish(cart.getFishId());
			double price = 0;
			if (fish.getPricePromo() == 0) {
				price = fish.getPriceOrigin();
			} else {
				price = fish.getPricePromo();
			}
			total += cart.getQuantity() * price;
			CartItem item = new CartItem(fish, cart.getQuantity());
			cartList.add(item);
		}
		String totalStr = String.format("%,d", (int) total);

		List<Cart> tempList = CartUtil.stringToList(cartListStr);
		int numberOfItem = 0;
		for (Cart cart : tempList) {
			numberOfItem += cart.getQuantity();
		}
		model.addAttribute("total", totalStr);
		model.addAttribute("cartList", cartList);
		model.addAttribute("numberOfItem", numberOfItem);
		return "shoppingCart";
	}

	@RequestMapping(value = { "/shipping-infor" }, method = RequestMethod.GET)
	public String shippingInfor(HttpServletRequest request, HttpServletResponse response, Model model,
			@CookieValue(value = "cartListStr", defaultValue = "") String cartListStr) {
		if (cartListStr == null || cartListStr.equals("")) {
			return "redirect:/";
		}
		List<Cart> itemList = CartUtil.stringToList(cartListStr);
		List<CartItem> cartList = new ArrayList<CartItem>();
		double total = 0;
		for (Cart cart : itemList) {
			Fish fish = fishDAO.findFish(cart.getFishId());
			double price = 0;
			if (fish.getPricePromo() == 0) {
				price = fish.getPriceOrigin();
			} else {
				price = fish.getPricePromo();
			}
			total += cart.getQuantity() * price;
			CartItem item = new CartItem(fish, cart.getQuantity());
			cartList.add(item);
		}
		String totalStr = String.format("%,d", (int) total);

		List<Cart> tempList = CartUtil.stringToList(cartListStr);
		int numberOfItem = 0;
		for (Cart cart : tempList) {
			numberOfItem += cart.getQuantity();
		}

		Order order = new Order();
		model.addAttribute("orderForm", order);
		model.addAttribute("total", totalStr);
		model.addAttribute("cartList", cartList);
		model.addAttribute("numberOfItem", numberOfItem);
		return "shippingInfor";
	}

	@RequestMapping(value = { "/shipping-infor" }, method = RequestMethod.POST)
//    @Transactional(propagation = Propagation.NEVER)
	public String checkout(HttpServletResponse response, Model model, //
			@ModelAttribute("orderForm") @Validated Order order, //
			BindingResult result, final RedirectAttributes redirectAttributes,
			@CookieValue(value = "cartListStr", defaultValue = "") String cartListStr) {
		List<Cart> cartList = CartUtil.stringToList(cartListStr);
		double total = 0;
		for (Cart cart : cartList) {
			Fish fish = fishDAO.findFish(cart.getFishId());
			double price = 0;
			if (fish.getPricePromo() == 0) {
				price = fish.getPriceOrigin();
			} else if (fish.getPricePromo() > 0) {
				price = fish.getPricePromo();
			}
			total += cart.getQuantity() * price;
		}
		order.setTotal(total);
		order.setId(orderDAO.getNewOrderId());
		Gson gson = new Gson();
		String orderInfor = gson.toJson(order);
		orderInfor = orderInfor.replace(',', '&');
		orderInfor = orderInfor.replace('"', '|');
		orderInfor = orderInfor.replace(' ', '_');
		Cookie cookie = new Cookie("orderInfor", orderInfor);
		cookie.setMaxAge(864000);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/check-out";
	}

	@RequestMapping(value = { "/check-out" }, method = RequestMethod.GET)
	public String checkout(HttpServletRequest request, HttpServletResponse response, Model model,
			@CookieValue(value = "cartListStr", defaultValue = "") String cartListStr,
			@CookieValue(value = "orderInfor", defaultValue = "") String orderInfor) {
		if (cartListStr == null || cartListStr.equals("")) {
			return "redirect:/";
		}

		if (orderInfor == null || orderInfor.equals("")) {
			return "redirect:/";
		}
		Gson gson = new Gson();
		orderInfor = orderInfor.replace('_', ' ');
		orderInfor = orderInfor.replace('|', '"');
		orderInfor = orderInfor.replace('&', ',');
		Order order = gson.fromJson(orderInfor, Order.class);
		List<Cart> itemList = CartUtil.stringToList(cartListStr);
		List<CartItem> cartList = new ArrayList<CartItem>();
		double total = 0;
		for (Cart cart : itemList) {
			Fish fish = fishDAO.findFish(cart.getFishId());
			double price = 0;
			if (fish.getPricePromo() == 0) {
				price = fish.getPriceOrigin();
			} else {
				price = fish.getPricePromo();
			}
			total += cart.getQuantity() * price;
			CartItem item = new CartItem(fish, cart.getQuantity());
			cartList.add(item);
		}
		String totalStr = String.format("%,d", (int) total);

		List<Cart> tempList = CartUtil.stringToList(cartListStr);
		int numberOfItem = 0;
		for (Cart cart : tempList) {
			numberOfItem += cart.getQuantity();
		}
		model.addAttribute("total", totalStr);
		model.addAttribute("cartList", cartList);
		model.addAttribute("order", order);
		model.addAttribute("numberOfItem", numberOfItem);
		return "checkout";
	}

	@RequestMapping(value = { "/success" }, method = RequestMethod.GET)
	public String success(HttpServletRequest request, HttpServletResponse response, Model model,
			@CookieValue(value = "cartListStr", defaultValue = "") String cartListStr,
			@CookieValue(value = "orderInfor", defaultValue = "") String orderInfor) {
		if (cartListStr == null || cartListStr.equals("")) {
			return "redirect:/";
		}

		if (orderInfor == null || orderInfor.equals("")) {
			return "redirect:/";
		}
		Gson gson = new Gson();
		orderInfor = orderInfor.replace('_', ' ');
		orderInfor = orderInfor.replace('|', '"');
		orderInfor = orderInfor.replace('&', ',');
		Order order = gson.fromJson(orderInfor, Order.class);
		order.setOrderTime(LocalDateTime.now());
		order.setStatus("Đặt hàng thành công");
		orderDAO.saveOrder(order);

		List<Cart> itemList = CartUtil.stringToList(cartListStr);
		double total = 0;
		for (Cart cart : itemList) {
			Fish fish = fishDAO.findFish(cart.getFishId());
			double price = 0;
			if (fish.getPricePromo() == 0) {
				price = fish.getPriceOrigin();
			} else if (fish.getPricePromo() > 0) {
				price = fish.getPricePromo();
			}
			total += cart.getQuantity() * price;
			OrderDetail orderDetail = new OrderDetail(orderDAO.getNewOrderId(), order, fish, price, cart.getQuantity());
			orderDetailDAO.saveOrderDetail(orderDetail);
		}

		Cookie cookie1 = new Cookie("cartListStr", null);
		cookie1.setMaxAge(0);
		cookie1.setPath("/");
		response.addCookie(cookie1);

		Cookie cookie2 = new Cookie("orderInfor", null);
		cookie2.setMaxAge(0);
		cookie2.setPath("/");
		response.addCookie(cookie2);

		String totalStr = String.format("%,d", (int) total);

		// send email
		String content = "<div>";
		content += "Khách hàng " + order.getName() + " vừa đặt đơn hàng [" + order.getId() + "] vào lúc "
				+ order.getOrderTime().toString().substring(0, 19) + "</br>";
		content += ", tổng giá trị đơn hàng: " + order.getTotal() + "</br></br>";
		content += "<a href='" + request.getRequestURL().substring(0, 26) + "/admin/order/" + order.getId() + "'>Kiểm tra đơn hàng</a>";
		content += "</div>";
		SendEmail sendEmail = new SendEmail("vinhdat4999@gmail.com", "Đơn hàng mới [" + order.getId() + "]", content);
		Thread t = new Thread(sendEmail);
		t.start();
		model.addAttribute("total", totalStr);
		model.addAttribute("order", order);
		model.addAttribute("numberOfItem", 0);
		return "success";
	}

	@RequestMapping({ "/remove-product" })
	public String removeProductHandler(HttpServletRequest request, HttpServletResponse response, Model model, //
			@RequestParam(value = "id", defaultValue = "") String id,
			@CookieValue(value = "cartListStr", defaultValue = "") String cartListStr) {
		List<Cart> cartList = CartUtil.stringToList(cartListStr);
		for (Cart cart : cartList) {
			if (cart.getFishId().equals(id)) {
				cartList.remove(cart);
				break;
			}
		}
		Cookie cookie = new Cookie("cartListStr", CartUtil.listToString(cartList));
		cookie.setMaxAge(864000);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/cart";
	}

	@RequestMapping({ "/update-cart" })
	public String updateProductInCartHandler(HttpServletRequest request, HttpServletResponse response, Model model, //
			@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "type", defaultValue = "") String type,
			@CookieValue(value = "cartListStr", defaultValue = "") String cartListStr) {
		List<Cart> cartList = CartUtil.stringToList(cartListStr);
		for (Cart cart : cartList) {
			if (cart.getFishId().equals(id)) {
				if (type.equals("des")) {
					if (cart.getQuantity() > 1) {
						cart.setQuantity(cart.getQuantity() - 1);
					}
				} else if (type.equals("inc")) {
					cart.setQuantity(cart.getQuantity() + 1);
				}
				break;
			}
		}
		Cookie cookie = new Cookie("cartListStr", CartUtil.listToString(cartList));
		cookie.setMaxAge(864000);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/cart";
	}
//
//	public static boolean isStringIsDoubleNumber(String s) {
//		try {
//			Double.parseDouble(s);
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
//
//	public static boolean isStringIsLongNumber(String s) {
//		try {
//			Long.parseLong(s);
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
}
