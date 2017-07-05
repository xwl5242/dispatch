package com.dispatch.sys.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class PageInfoBean implements Serializable {
	private static final long serialVersionUID = -2068920941782049919L;

	private int totalCount = 0; // 总共记录数

	private int totalPageSize = 0; // 总页数

	private int currentPage = 1; // 当前页
	// 每页显示的记录条数
	private int pageSize = 10; // 每页条数

	

	
	
	private int startPage = 1; // 起始页数
	// 排序字段名
	private String sortField = "";

	// 排序方式 为空是升序，为DESC时是降序
	private String sortType = "DESC";

	private Map<String, String> sortMap; // 排序Map Key 排序字段 value 排序类型 asc desc

	private List pageList; // 保存分页LIST数据

	public PageInfoBean(List list, int totalCount, int size, int currentPage) {
		this.pageList = list;
		this.totalCount = totalCount;
		this.pageSize = size;
		this.currentPage = currentPage;
		try {
			if (totalCount % size == 0) {
				this.totalPageSize = (totalCount / size);
			} else {
				this.totalPageSize = (totalCount / size + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.totalCount = 0;
		}
	}

	public PageInfoBean(List list, int totalCount, int size, int currentPage,
			String sortField, String sortType) {
		this(list, totalCount, size, currentPage);
		this.sortField = sortField;
		this.sortType = sortType;
	}

	public PageInfoBean(List list, int totalCount, int size, int currentPage,
			Map sortMap) {
		this(list, totalCount, size, currentPage);
		this.sortMap = sortMap;
	}

	public PageInfoBean() {

	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		if (this.totalCount % this.pageSize == 0) {
			this.totalPageSize = (totalCount / this.pageSize);
		} else {
			this.totalPageSize = (totalCount / this.pageSize + 1);
		}
	}

	public int getTotalPageSize() {
		return totalPageSize;
	}

	public int getCurrentPage() {
		if (this.currentPage <= 0) {
			this.currentPage = 1;
		}
		return this.currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		/*if (this.pageSize >=this.totalCount) {
			this.currentPage = 1;
		}*/
		if (currentPage <= 0) {
			this.currentPage = 1;
		}
	/*	if (currentPage > totalPageSize) {
			this.currentPage = totalPageSize;
		}*/
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize == 0) {
			this.pageSize = 10;
		} else {
			this.pageSize = pageSize;
		}
	}


	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public Map<String, String> getSortMap() {
		if (null == sortMap) {
			sortMap = new HashMap<String, String>();
		}
		return sortMap;
	}

	public void setSortMap(Map<String, String> sortMap) {
		this.sortMap = sortMap;
	}

	public List getPageList() {
		return pageList;
	}

	public void setPageList(List pageList) {
		this.pageList = pageList;
	}

	// 多少行开始的数据
	public int getStartCount() {
		// if(currentPage==1){
		// System.out.println("currentPage..........."+getCurrentPage());
		
		
		int startRow = (getCurrentPage()-1)*getPageSize()+1;
		
		//int startCount=(getCurrentPage() - 1) * this.pageSize;
		return startRow;
		// }else{
		// return (currentPage-1)*this.pageSize ;
		// }
	}

	public void setStartCount(int startCount) {
	}

	public int getEndCount() {
	//	int startRow = (getCurrentPage()-1)*getPageSize()+1;
		int endRow = (getCurrentPage()-1)*getPageSize()+getPageSize();
		return endRow;
	}

}
