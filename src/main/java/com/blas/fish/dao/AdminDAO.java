package com.blas.fish.dao;

import com.blas.fish.model.Admin;

public interface AdminDAO {

	public Admin findAdmin(String username);

	public void save(Admin admin);
}