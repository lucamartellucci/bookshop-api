package com.absontheweb.library.model;

import java.io.Serializable;
import java.util.List;

public class PaginatorResult<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<T> result;
	private int totalPages;
	private int currentPage;
	private int pageSize;
	private long totalItems;
	
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PaginatorResult [result=").append(result)
				.append(", totalPages=").append(totalPages)
				.append(", currentPage=").append(currentPage)
				.append(", pageSize=").append(pageSize).append(", totalItems=")
				.append(totalItems).append("]");
		return builder.toString();
	}
	
}
