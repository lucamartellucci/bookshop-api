package io.lucci.bookshop.book.model.adapter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import io.lucci.bookshop.model.User;
import io.lucci.bookshop.persistence.model.AuthorityDBTO;
import io.lucci.bookshop.persistence.model.UserDBTO;

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
