package com.sumCo.common.utils;

import java.io.Serializable;
import java.util.List;

/**
 * @author oplus
 * @Description: TODO(分頁工具類)
 * @date 2017-6-23 15:07
 */
public class PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	//總記錄數
	private int totalCount;
	//每頁記錄數
	private int pageSize;
	//總頁數
	private int totalPage;
	//當前頁數
	private int currPage;
	//列表數據
	private List<?> list;
	
	/**
	 * 分頁
	 * @param list        列表數據
	 * @param totalCount  總記錄數
	 * @param pageSize    每頁記錄數
	 * @param currPage    當前頁數
	 */
	public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	
}
