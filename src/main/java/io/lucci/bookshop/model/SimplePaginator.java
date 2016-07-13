package io.lucci.bookshop.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SimplePaginator implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer page;
	private Integer size;

	public SimplePaginator(Integer page, Integer size) {
		super();
		this.page = page;
		this.size = size;
	}

	public SimplePaginator() {
		super();
	}
	
}
