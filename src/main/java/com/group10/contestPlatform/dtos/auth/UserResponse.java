package com.group10.contestPlatform.dtos.auth;



import com.group10.contestPlatform.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserResponse {


	public int currentPage;
	public  int totalPages;
	public long startCount;
	public long endCount;
	public long totalItems;
	public List<?> listName;



}
