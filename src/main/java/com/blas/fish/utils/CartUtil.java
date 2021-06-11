package com.blas.fish.utils;

import java.util.ArrayList;
import java.util.List;

import com.blas.fish.model.Cart;

public class CartUtil {

	public static boolean existFish(String fishId, List<Cart> cartList) {
		for (Cart i : cartList) {
			if (i.getFishId().equals(fishId))
				return true;
		}
		return false;
	}

	public static String listToString(List<Cart> cartList) {
		String s = "";
		for (Cart cart : cartList) {
			s += cart.getFishId() + "#" + cart.getQuantity()+"&";
//			if (cart != cartList.get(cartList.size()-1)) {
//				s += "&";
//			}
		}
		System.out.println("list to string: " + s);
		return s;
	}

	public static List<Cart> stringToList(String localStorage) {
		List<Cart> cartList = new ArrayList<Cart>();
		if (localStorage != null && !localStorage.equals("")) {
			String[] temp = localStorage.split("&");
			for (String i : temp) {
				System.out.println("String: " + i);
				String fishId = i.split("#")[0];
				String quantity = i.split("#")[1];
				System.out.println(fishId + ".." + quantity);
				cartList.add(new Cart(fishId, Integer.parseInt(quantity)));
			}
		}
		return cartList;
	}
	

//	public static List<Cart> stringToList(String localStorage){
//		List<Cart> cartList = new ArrayList<Cart>();
//		String[] temp = localStorage.split("|");
//		for(String i:temp) {
//			String fishId = i.split(",")[0];
//			String quantity = i.split(",")[1];
//			if(!existFish(fishId, cartList)) {
//				cartList.add(new Cart(fishId, Integer.parseInt(quantity)));
//			}else {
//				for(Cart cart:cartList) {
//					if(cart.getFishId().equals(fishId)) {
//						cart.setQuantity(cart.getQuantity() + Integer.parseInt(quantity));
//					}
//				}
//			}
//		}
//		return cartList;
//	}
}
