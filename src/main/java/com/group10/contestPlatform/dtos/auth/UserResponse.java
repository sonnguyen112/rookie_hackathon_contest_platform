package com.group10.contestPlatform.dtos.auth;



import com.group10.contestPlatform.entities.User;

import java.util.ArrayList;
import java.util.List;


public class UserResponse {

	 private int page;
	private int totalPage;
	private List<User> listResult = new ArrayList<>();
	 
	
 
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<User> getListResult() {
		return listResult;
	}

	public void setListResult(List<User> listResult) {
		this.listResult = listResult;
	}



	
	


}
