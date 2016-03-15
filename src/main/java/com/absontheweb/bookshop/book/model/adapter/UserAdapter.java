package com.absontheweb.bookshop.book.model.adapter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.absontheweb.bookshop.model.User;
import com.absontheweb.bookshop.persistence.model.AuthorityDBTO;
import com.absontheweb.bookshop.persistence.model.UserDBTO;

@Component
public class UserAdapter {

	
	public User toUser(UserDBTO userDBTO) {
		if (userDBTO == null){
			return null;
		}
		User user = new User();
		BeanUtils.copyProperties(userDBTO, user, "roles");
		for (AuthorityDBTO autority : userDBTO.getAuthorities()) {
			user.getRoles().add(autority.getName());
		}
		return user;
	}
}
