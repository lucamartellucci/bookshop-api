package com.absontheweb.bookshop.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PaginatorResult<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<T> result;
	private int totalPages;
	private int currentPage;
	private int pageSize;
	private long totalItems;
	
}
