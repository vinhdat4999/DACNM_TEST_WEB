package com.blas.fish.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadProductImageForm {

	private CommonsMultipartFile[] fileDatas;

	public CommonsMultipartFile[] getFileDatas() {
		return fileDatas;
	}

	public void setFileDatas(CommonsMultipartFile[] fileDatas) {
		this.fileDatas = fileDatas;
	}
}
