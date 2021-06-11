//package com.blas.fish.controller;
//
//import java.io.IOException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import com.blas.fish.dao.CategoryDAO;
//import com.blas.fish.dao.FishDAO;
//import com.blas.fish.dao.OrderDAO;
//import com.blas.fish.dao.ProductImageDAO;
//import com.blas.fish.model.Category;
//import com.blas.fish.model.DetailImage;
//import com.blas.fish.model.Fish;
//import com.blas.fish.model.PaginationResult;
//
//@Controller
//@Transactional
//@EnableWebMvc
//public class SystemController {
//
//	@Autowired
//	private FishDAO fishDAO;
//
//	@Autowired
//	private OrderDAO orderDAO;
//	
//	@Autowired
//	private CategoryDAO categoryDAO;
//
//	@Autowired
//	private ProductImageDAO productImageDAO;
//
//	@RequestMapping("/403")
//	public String accessDenied() {
//		return "/403";
//	}
//
//	@RequestMapping(value = { "/image" }, method = RequestMethod.GET)
//	public void image(HttpServletRequest request, HttpServletResponse response, Model model,
//			@RequestParam("id") String imageId) throws IOException {
//		DetailImage productImage = null;
//		if (imageId != null) {
//			productImage = productImageDAO.findProductImage(imageId);
//		}
//		if (productImage != null && productImage.getImage() != null) {
//			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
//			response.getOutputStream().write(productImage.getImage());
//		}
//		response.getOutputStream().close();
//	}
//
////	@RequestMapping(value = { "/cart" }, method = RequestMethod.GET)
////	public String shoppingCartHandler(@CookieValue(value = "receiverInfo", defaultValue = "") String receiverInfoId,
////			HttpServletRequest request, HttpServletResponse response, Model model) {
////		String username = "";
////		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////		if (principal instanceof UserDetails) {
////			username = ((UserDetails) principal).getUsername();
////		} else {
////			username = principal.toString();
////		}
////		List<CartModel> itemList = cartDAO.getAllItemInCartByUser(username);
////		List<CartDetailModel> detailList = new ArrayList<CartDetailModel>();
////		for (CartModel i : itemList) {
////			CartDetailModel temp = new CartDetailModel(i.getId(), i.getProductId(), "", i.getQuantity(), 0,
////					i.getUsername());
////			ProductModel productModel = productDAO.findProductModel(i.getProductId());
////			temp.setProductName(productModel.getName());
////			temp.setPrice(productModel.getPrice());
////			detailList.add(temp);
////		}
////		double total = cartDAO.getTotalAmount(username);
////		String totalStr = String.format("%,d", (int) total);
////
////		ReceiverInfoModel receiverInfoModel = null;
////		if (receiverInfoId == null || receiverInfoId.equals("")) {
////			receiverInfoModel = receiverInfoDAO.findReceiverInfoModelByUsername(username);
////			Cookie cookie = new Cookie("receiverInfo", receiverInfoModel.getId());
////			response.addCookie(cookie);
////		} else {
////			receiverInfoModel = receiverInfoDAO.findReceiverInfoModelById(receiverInfoId);
////		}
////		model.addAttribute("receiverInfo", receiverInfoModel);
////		model.addAttribute("total", totalStr);
////		model.addAttribute("detailList", detailList);
////		return "shoppingCart";
////	}
////
////	@RequestMapping(value = { "/cart" }, method = RequestMethod.POST)
////	public String shoppingCartUpdateQty(HttpServletRequest request, //
////			Model model, @ModelAttribute("detailList") @Validated CartDetailModel detailList) {
////		String username = "";
////		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////		if (principal instanceof UserDetails) {
////			username = ((UserDetails) principal).getUsername();
////		} else {
////			username = principal.toString();
////		}
////		String[] quantityString = request.getParameterValues("quantityItem");
////		int[] quantityInt = new int[quantityString.length];
////		for (int i = 0; i < quantityString.length; i++) {
////			int temp = Integer.parseInt(quantityString[i]);
////			quantityInt[i] = temp;
////		}
////		cartDAO.updateQuantityInCart(quantityInt, username);
////		return "redirect:/cart";
////	}
////
////	@RequestMapping(value = { "/orderList" }, method = RequestMethod.GET)
////	public String orderList(Model model, //
////			@RequestParam(value = "page", defaultValue = "1") String pageStr) {
////		int page = 1;
////		try {
////			page = Integer.parseInt(pageStr);
////		} catch (Exception e) {
////		}
////		final int MAX_RESULT = 5;
////		final int MAX_NAVIGATION_PAGE = 10;
////
////		String username = "";
////		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////		if (principal instanceof UserDetails) {
////			username = ((UserDetails) principal).getUsername();
////		} else {
////			username = principal.toString();
////		}
////		PaginationResult<OrderModel> paginationResult //
////				= orderDAO.listOrderModelByUser(page, MAX_RESULT, MAX_NAVIGATION_PAGE, username);
////		model.addAttribute("paginationResult", paginationResult);
////		model.addAttribute("pageNow", pageStr);
////		return "orderList";
////	}
////
////	@RequestMapping(value = { "/order" }, method = RequestMethod.GET)
////	public String orderView(Model model, @RequestParam("id") String id) {
////		String username = "";
////		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////		if (principal instanceof UserDetails) {
////			username = ((UserDetails) principal).getUsername();
////		} else {
////			username = principal.toString();
////		}
////		OrderModel orderModel = null;
////		if (id != null) {
////			orderModel = this.orderDAO.getOrderModel(id);
////		}
////		if (orderModel == null) {
////			return "redirect:/orderList";
////		}
////		if (!orderModel.getUsername().equals(username)) {
////			return "403";
////		}
////		List<OrderDetailModel> details = orderDAO.listOrderDetailModels(id);
////		for (OrderDetailModel i : details) {
////			ProductModel productModel = productDAO.findProductModel(i.getProductId());
////			i.setName(productModel.getName());
////		}
////		if (details == null) {
////			return "redirect:/orderList";
////		}
////		ReceiverInfoModel receiverInfoModel = receiverInfoDAO.findReceiverInfoModelById(orderModel.getReceiverInfoId());
////		double total = orderModel.getTotal();
////		String totalStr = String.format("%,d", (int) total);
////		model.addAttribute("receiverInfo", receiverInfoModel);
////		model.addAttribute("orderInfo", orderModel);
////		model.addAttribute("detailList", details);
////		model.addAttribute("total", totalStr);
////		return "order";
////	}
////
////	@RequestMapping(value = { "/incItem" }, method = RequestMethod.GET)
////	public String incItem(HttpServletRequest request, HttpServletResponse response, Model model,
////			@RequestParam("id") String id) {
////		CartModel cartModel = cartDAO.findCartModel(id);
////		cartDAO.updateQuantityItemInCart(id, cartModel.getQuantity() + 1);
////		return "redirect:/cart";
////	}
////
////	@RequestMapping(value = { "/desItem" }, method = RequestMethod.GET)
////	public String desItem(HttpServletRequest request, HttpServletResponse response, Model model,
////			@RequestParam("id") String id) {
////		CartModel cartModel = cartDAO.findCartModel(id);
////		if (cartModel.getQuantity() >= 2) {
////			cartDAO.updateQuantityItemInCart(id, cartModel.getQuantity() - 1);
////		}
////		return "redirect:/cart";
////	}
////
////	@RequestMapping(value = { "/shipping" }, method = RequestMethod.GET)
////	public String chooseReceiverInfo(HttpServletRequest request, HttpServletResponse response, Model model,
////			@RequestParam(value = "page", defaultValue = "1") int page) {
////		String username = "";
////		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////		if (principal instanceof UserDetails) {
////			username = ((UserDetails) principal).getUsername();
////		} else {
////			username = principal.toString();
////		}
////		final int maxResult = 48;
////		final int maxNavigationPage = 10;
////		PaginationResult<ReceiverInfoModel> result = receiverInfoDAO.queryReceiverInfos(page, //
////				maxResult, maxNavigationPage, username);
////		model.addAttribute("paginationReceiverInfos", result);
////		return "shipping";
////	}
////
////	@RequestMapping(value = { "/shipping" }, method = RequestMethod.POST)
////	public String createReceiverInfo(HttpServletRequest request, HttpServletResponse response, Model model,
////			@ModelAttribute("receiverInfo") @Validated ReceiverInfoModel receiverInfoModel) {
////		String username = "";
////		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////		if (principal instanceof UserDetails) {
////			username = ((UserDetails) principal).getUsername();
////		} else {
////			username = principal.toString();
////		}
////		String id = UUID.randomUUID().toString();
////		ReceiverInfoModel receiverInfoModel2 = new ReceiverInfoModel(id, username, receiverInfoModel.getReceiverName(),
////				receiverInfoModel.getReceiverPhone(), receiverInfoModel.getReceiverEmail(),
////				receiverInfoModel.getReceiverAddress(),true);
////		receiverInfoDAO.save(receiverInfoModel2);
////		Cookie cookie = new Cookie("receiverInfo", id);
////		response.addCookie(cookie);
////		return "redirect:/cart";
////	}
////
////	@RequestMapping(value = { "/shipping-to" }, method = RequestMethod.GET)
////	public String changeReceiverInfo(HttpServletRequest request, HttpServletResponse response, Model model,
////			@RequestParam(value = "receiverInfo", defaultValue = "") String receiverInfoId) {
////		String username = "";
////		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////		if (principal instanceof UserDetails) {
////			username = ((UserDetails) principal).getUsername();
////		} else {
////			username = principal.toString();
////		}
////		ReceiverInfoModel receiverInfoModel = receiverInfoDAO.findReceiverInfoModelById(receiverInfoId);
////		if (!receiverInfoModel.getUsername().equals(username)) {
////			return "redirect:/shipping";
////		}
////		Cookie cookie = new Cookie("receiverInfo", receiverInfoId);
////		response.addCookie(cookie);
////		return "redirect:/cart";
////	}
////
////	@RequestMapping(value = { "/delete-receiver" }, method = RequestMethod.GET)
////	public String deleteReceiver(HttpServletRequest request, HttpServletResponse response, Model model,
////			@RequestParam(value = "receiverInfo", defaultValue = "") String receiverInfoId,
////			@CookieValue(value = "receiverInfo", defaultValue = "") String receiverInfoIdCookie) {
////		String username = "";
////		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////		if (principal instanceof UserDetails) {
////			username = ((UserDetails) principal).getUsername();
////		} else {
////			username = principal.toString();
////		}
////		ReceiverInfoModel receiverInfoModel = receiverInfoDAO.findReceiverInfoModelById(receiverInfoId);
////		if (!receiverInfoModel.getUsername().equals(username)) {
////			return "redirect:/";
////		}
////		if(receiverInfoIdCookie!=null && !receiverInfoIdCookie.equals("")) {
////			if(receiverInfoIdCookie.equals(receiverInfoId)) {
////				Cookie cookie = new Cookie("receiverInfo", null);
////				cookie.setMaxAge(0);
////				response.addCookie(cookie);
////			}
////		}
////		receiverInfoDAO.delete(receiverInfoId);
////		return "redirect:/cart";
////	}
////
////	@RequestMapping(value = { "/checkout" }, method = RequestMethod.GET)
////	public String checkout(@CookieValue(value = "receiverInfo", defaultValue = "") String receiverInfoId,
////			HttpServletRequest request, HttpServletResponse response, Model model) {
////		ReceiverInfoModel receiverInfoModel = receiverInfoDAO.findReceiverInfoModelById(receiverInfoId);
////		String username = "";
////		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////		if (principal instanceof UserDetails) {
////			username = ((UserDetails) principal).getUsername();
////		} else {
////			username = principal.toString();
////		}
////		double total = cartDAO.getTotalAmount(username);
////		String totalStr = String.format("%,d", (int) total);
////		model.addAttribute("total", totalStr);
////		model.addAttribute("receiverInfo", receiverInfoModel);
////		return "checkout";
////	}
////
////	@RequestMapping(value = { "/checkout" }, method = RequestMethod.POST)
////	public String checkout(@CookieValue(value = "receiverInfo", defaultValue = "") String receiverInfoId, Model model,
////			HttpServletResponse response) {
////		String username = "";
////		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////		if (principal instanceof UserDetails) {
////			username = ((UserDetails) principal).getUsername();
////		} else {
////			username = principal.toString();
////		}
////		String orderId = orderDAO.saveOrder(username, receiverInfoId);
////		Cookie cookie = new Cookie("receiverInfo", null);
////		cookie.setMaxAge(0);
////		response.addCookie(cookie);
////		Cookie cookie2 = new Cookie("orderId", orderId);
////		response.addCookie(cookie2);
////		return "redirect:/shoppingCartFinalize";
////	}
////
////	@RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
////	public String shoppingCartFinalize(@CookieValue(value = "orderId", defaultValue = "") String orderId,
////			HttpServletRequest request, Model model, HttpServletResponse response) {
////		if (orderId == null || orderId.equals("")) {
////			return "redirect:/";
////		}
////		String username = "";
////		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////		if (principal instanceof UserDetails) {
////			username = ((UserDetails) principal).getUsername();
////		} else {
////			username = principal.toString();
////		}
////		UserModel user = userDAO.findUserModel(username);
////		OrderModel orderModel = orderDAO.getOrderModel(orderId);
////		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
////		String formatDateTime = orderModel.getOrderTime().format(formatter);
////		ReceiverInfoModel receiverInfoModel = receiverInfoDAO.findReceiverInfoModelById(orderModel.getReceiverInfoId());
////		String content = "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<meta charset=\"UTF-8\">\n" + "\n"
////				+ "<link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,700\"\n"
////				+ "	rel=\"stylesheet\">\n" + "<link rel=\"stylesheet\" type=\"text/css\"\n"
////				+ "	href=\"${pageContext.request.contextPath}/styles.css\">\n" + "<style>\n" + "\n" + "body {\n"
////				+ "	background-color: #8080801a;\n" + "	font-family: 'open sans';\n" + "	overflow-x: hidden;\n"
////				+ "}\n" + ".btnDetail {\n" + "	text-decoration: none;\n" + "	background: #189fff;\n"
////				+ "	padding: 0.8em 0.8em;\n" + "	border: none;\n" + "	text-transform: UPPERCASE;\n"
////				+ "	font-weight: bold;\n" + "	color: #fff;\n" + "	-webkit-transition: background .3s ease;\n"
////				+ "	transition: background .3s ease;\n" + "}\n" + "\n" + ".btnDetail:hover {\n"
////				+ "	background: #18cfff;\n" + "	color: #fff;\n" + "	cursor: pointer;\n" + "}" + "</style>\n"
////				+ "</head>\n" + "<body>\n" + "    <div>\n" + "        <div>\n" + "            <h3>Cáº£m Æ¡n quÃ½ khÃ¡ch "
////				+ user.getFirstname() + " " + user.getLastname() + " Ä‘Ã£ Ä‘áº·t hÃ ng táº¡i BLAS,</h3>\n"
////				+ "            <p> BLAS ráº¥t vui thÃ´ng bÃ¡o Ä‘Æ¡n hÃ ng #" + orderId
////				+ "			   cá»§a quÃ½ khÃ¡ch Ä‘Ã£ Ä‘Æ°á»£c tiáº¿p nháº­n vÃ  Ä‘ang trong quÃ¡ trÃ¬nh xá»­ lÃ½. BLAS sáº½ thÃ´ng bÃ¡o Ä‘áº¿n quÃ½ khÃ¡ch ngay khi hÃ ng chuáº©n bá»‹ Ä‘Æ°á»£c giao.</br></br></p>\n"
////				+ "        </div>\n" + "        <div>\n" + "            <div style=\"display: flex;\">\n"
////				+ "                <h3 style=\"color:#22a2ff;\">THÃ”NG TIN Ä�Æ N HÃ€NG #" + orderId + "</h3>"
////				+ "                <h4 style=\"color:#94a3ad; margin-left: 10px;\">(Thá»�i gian Ä‘áº·t hÃ ng: "
////				+ formatDateTime + ")</h4>\n" + "            </div>\n"
////				+ "            <div style=\"margin-left: 5%;\">\n"
////				+ "                <p style=\"font-weight: bold;\">Ä�á»‹a chá»‰ giao hÃ ng</p>\n" + "                <div>"
////				+ receiverInfoModel.getReceiverName() + "</div>\n" + "                <div>"
////				+ receiverInfoModel.getReceiverAddress() + "</div>\n" + "                <div>"
////				+ receiverInfoModel.getReceiverPhone() + "</div>\n" + "            </div>\n"
////				+ "            <div style=\"margin-top: 15px;\">PhÆ°Æ¡ng thá»©c thanh toÃ¡n: Thanh toÃ¡n tiá»�n máº·t khi nháº­n hÃ ng</div>\n"
////				+ "            <div style=\"font-style: italic;\">LÆ°u Ã½: Ä�á»‘i vá»›i Ä‘Æ¡n hÃ ng Ä‘Ã£ Ä‘Æ°á»£c thanh toÃ¡n trÆ°á»›c, nhÃ¢n viÃªn giao nháº­n cÃ³ thá»ƒ yÃªu cáº§u ngÆ°á»�i nháº­n hÃ ng cung cáº¥p CMND / giáº¥y phÃ©p lÃ¡i xe Ä‘á»ƒ chá»¥p áº£nh hoáº·c ghi láº¡i thÃ´ng tin.</div>\n"
////				+ "        </div>\n" + "			<div style=\"margin-top: 20px; width: 60%;margin-left: 19%;\">\n"
////				+ "            <div>\n" + "                <div\n"
////				+ "                    style=\"padding-left: 40px; padding-right: 40px; display: flex; font-weight: bold; background-color: #22a2ff; color: white;\">\n"
////				+ "                    <div style=\"width: 60%;\">Sáº£n pháº©m</div>\n"
////				+ "                    <div style=\"width: 14%;\">GiÃ¡</div>\n"
////				+ "                    <div style=\"width: 12%;\">Sá»‘ lÆ°á»£ng</div>\n"
////				+ "                    <div style=\"width: 14%;\">Táº¡m tÃ­nh</div>\n" + "                </div>\n"
////				+ "                <div style=\"padding: 40px; padding-top: inherit; background-color: #d7e2e9;\">\n";
////		List<CartModel> itemList = cartDAO.getAllItemInCartByUser(username);
////		for (CartModel i : itemList) {
////			ProductModel productModel = productDAO.findProductModel(i.getProductId());
////			content += "<div class=\"product-preview-shopping-cart-container\"\n"
////					+ "                            style=\"display: flex;\">\n"
////					+ "                            <div style=\"width: 60%;\">\n" + productModel.getName()
////					+ "                            </div>\n" + "                            <div style=\"width: 14%;\">"
////					+ productModel.getPrice() + "</div>\n" + "                            <div style=\"width: 12%;\">"
////					+ i.getQuantity() + "</div>\n" + "                            <div style=\"width: 14%;\">"
////					+ (i.getQuantity() * productModel.getPrice()) + "</div>\n" + "                        </div>";
////		}
////		content += "                    <div style=\"width: 100%;margin-top: 25px; margin-left: 50%; font-weight: bold;\">Tá»”NG GIÃ� TRá»Š Ä�Æ N HÃ€NG: "
////				+ cartDAO.getTotalAmount(username) + "Ä‘ </div> \n" + "                </div>\n"
////				+ "            </div>            \n" + "        </div>\n"
////				+ "		   <div style=\"margin-top: 30px; margin-left: 40%;\">"
////				+ "            <a class=\"btnDetail\" href=\"http://localhost:8080/BlasEcommerce/order?id=" + orderId
////				+ "\">Xem chi tiáº¿t Ä‘Æ¡n hÃ ng</a>\n" + "        </div>" + "        <div style=\"margin-top: 20px;\">\n"
////				+ "            Má»�i tháº¯c máº¯c vÃ  gÃ³p Ã½, quÃ½ khÃ¡ch vui lÃ²ng liÃªn há»‡ vá»›i BLAS Care qua <a href=\"https://www.facebook.com/vinhdat4999/\">Facebook</a> hoáº·c hotline 0965 040 999. Ä�á»™i ngÅ© BLAS Care luÃ´n sáºµn sÃ ng há»— trá»£ báº¡n.\n"
////				+ "        </div>\n" + "        <div style=\"font-weight: bold;\">"
////				+ "            Má»™t láº§n ná»¯a BLAS cáº£m Æ¡n quÃ½ khÃ¡ch.\n" + "        </div>     \n"
////				+ "        <div style=\"color: #22a2ff; margin-left: 80%;font-weight: bold;\">\n"
////				+ "            <h3>BLAS</h3>\n" + "        </div> " + "    </div>\n" + "</body>\n" + "</html>";
////		String title = "XÃ¡c nháº­n Ä‘Æ¡n hÃ ng #" + orderId + " BLAS\n";
////		UserModel userModel = userDAO.findUserModel(username);
////		new SendEmail().sendEmail(userModel.getEmail(), title, content);
////		Cookie cookie = new Cookie("orderId", null);
////		cookie.setMaxAge(0);
////		response.addCookie(cookie);
////		if (itemList.size() == 0) {
////			return "redirect:/shoppingCart";
////		}
////		cartDAO.deleteAllItemsInCartByUser(username);
////		return "shoppingCartFinalize";
////	}
////
////	@RequestMapping(value = { "/place-again" }, method = RequestMethod.GET)
////	public String placeAgain(HttpServletRequest request, HttpServletResponse response, Model model,
////			@RequestParam(value = "id", defaultValue = "") String orderId) {
////		OrderModel orderModel = orderDAO.getOrderModel(orderId);
////		String username = "";
////		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////		if (principal instanceof UserDetails) {
////			username = ((UserDetails) principal).getUsername();
////		} else {
////			username = principal.toString();
////		}
////		if (!username.equals(orderModel.getUsername())) {
////			return "redirect:/";
////		}
////		List<OrderDetailModel> list = orderDAO.listOrderDetailModels(orderId);
////		cartDAO.deleteAllItemsInCartByUser(username);
////		for (OrderDetailModel i : list) {
////			cartDAO.updateItemInCart(i.getProductId(), i.getQuantity(), orderModel.getUsername());
////		}
////		return "redirect:/cart";
////	}
////
////	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
////	public String signup(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
////		return "signup";
////	}
////
////	@RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
////	@Transactional(propagation = Propagation.NEVER)
////	public String signup(HttpServletRequest request, Model model, //
////			@ModelAttribute("accountForm") @Validated UserModel userModel) {
////		String[] nameSplit = userModel.getLastname().split(" ");
////		String lastname = nameSplit[nameSplit.length - 1];
////		String firstname = "";
////		for (int i = 0; i < nameSplit.length - 1; i++) {
////			firstname += nameSplit[i] + " ";
////		}
////		userModel.setFirstname(firstname.trim());
////		userModel.setLastname(lastname);
////		String radioValue = request.getParameter("genderR");
////		if (radioValue.equals("Nam")) {
////			userModel.setGender(true);
////		} else {
////			userModel.setGender(false);
////		}
////		String day = request.getParameter("day");
////		String month = request.getParameter("month");
////		String year = request.getParameter("year");
////		Date date = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month) - 1, Integer.parseInt(day));
////		userModel.setBirthdate(date);
////		userModel.setActive(true);
////		userModel.setUserRole("CUSTOMER");
////
////		User user = new User(userModel);
////		String address = request.getParameter("address");
////		ReceiverInfoModel receiverInfoModel = new ReceiverInfoModel(UUID.randomUUID().toString(),
////				userModel.getUsername(), userModel.getFirstname() + " " + userModel.getLastname(),
////				userModel.getPhoneNum(), userModel.getEmail(), address, true);
////
////		userDAO.saveUser(user, new ReceiverInfo(receiverInfoModel));
////		return "redirect:/login";
////	}
////
////	@RequestMapping(value = { "/resetPassword" }, method = RequestMethod.GET)
////	public String resetPassword(HttpServletRequest request, Model model) throws IOException {
////		return "resetPassword";
////	}
////
////	@RequestMapping(value = { "/resetPassword" }, method = RequestMethod.POST)
////	@Transactional(propagation = Propagation.NEVER)
////	public String resetPassword(HttpServletRequest request, HttpServletResponse response, Model model) {
////		String username = request.getParameter("username").trim();
////		UserModel userModel = userDAO.findUserModel(username);
////		Random random = new Random();
////		int code = random.nextInt(900000) + 100000;
////		authenticationDAO.save(username, code + "");
////		String title = "Ã„ï¿½Ã¡ÂºÂ·t lÃ¡ÂºÂ¡i mÃ¡ÂºÂ­t khÃ¡ÂºÂ©u tÃƒÂ i khoÃ¡ÂºÂ£n BLAS";
////		String content = "Vui lÃƒÂ²ng khÃƒÂ´ng tiÃ¡ÂºÂ¿t lÃ¡Â»â„¢ mÃƒÂ£ xÃƒÂ¡c thÃ¡Â»Â±c cho bÃ¡ÂºÂ¥t kÃƒÂ¬ ai. MÃƒÂ£ xÃƒÂ¡c thÃ¡Â»Â±c sÃ¡ÂºÂ½ hÃ¡ÂºÂ¿t hÃ¡ÂºÂ¡n trong 20 phÃƒÂºt. MÃƒÂ£ xÃƒÂ¡c thÃ¡Â»Â±c tÃƒÂ i khoÃ¡ÂºÂ£n cÃ¡Â»Â§a bÃ¡ÂºÂ¡n lÃƒÂ Ã‚Â  : "
////				+ code + ".";
////		new SendEmail().sendEmail(userModel.getEmail(), title, content);
////		Cookie cookie = new Cookie("resetPassUsername", username);
////		response.addCookie(cookie);
////		return "redirect:/new-password";
////	}
////
////	@RequestMapping(value = { "/new-password" }, method = RequestMethod.GET)
////	public String newPassword(@CookieValue(value = "resetPassUsername", defaultValue = "") String username,
////			HttpServletRequest request, Model model) throws IOException {
////		UserModel userModel = userDAO.findUserModel(username);
////		String email = userModel.getEmail();
////		String[] temp = email.split("@");
////		String temp2 = temp[0];
////		email = temp2.substring(0, temp2.length() - 4).concat("****");
////		email += "@" + temp[1];
////		model.addAttribute("email", email);
////		return "newPassword";
////	}
////
////	@RequestMapping(value = { "/new-password" }, method = RequestMethod.POST)
////	@Transactional(propagation = Propagation.NEVER)
////	public String newPassword(@CookieValue(value = "resetPassUsername", defaultValue = "") String username,
////			HttpServletRequest request, HttpServletResponse response, Model model) {
////		String code = request.getParameter("code");
////		String password = request.getParameter("retype");
////		if (authenticationDAO.isValidAuthentication(username, code)) {
////			UserModel userModel = userDAO.findUserModel(username);
////			userModel.setPassword(password);
////			userDAO.saveUser(new User(userModel));
////			Cookie cookie = new Cookie("resetPassUsername", null);
////			cookie.setMaxAge(0);
////			response.addCookie(cookie);
////			Authentication authentication = authenticationDAO.findAuthentication(username);
////			LocalDateTime expire = LocalDateTime.of(1900, 1, 1, 1, 1, 1);
////			authentication.setTimeExpire(expire);
////			authenticationDAO.save(username, expire);
////		}
////		return "redirect:/login";
////	}
//}
