package com.blas.fish.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class CategoryForm {

	private String id;
	private String name;
	private CommonsMultipartFile fileDatas;

	public CategoryForm(String id, String name, CommonsMultipartFile fileDatas) {
		super();
		this.id = id;
		this.name = name;
		this.fileDatas = fileDatas;
	}

	public CategoryForm() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommonsMultipartFile getFileDatas() {
		return fileDatas;
	}

	public void setFileDatas(CommonsMultipartFile fileDatas) {
		this.fileDatas = fileDatas;
	}
}
