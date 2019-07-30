package com.node;

import java.util.List;

public class Pager {
	//数据集合
	private List list;
	//当前页
	private int curPage;
	//每页条数
	private int pageSize;
	//总条数
	private int totalCount;
	//总页数
	private int totalPages;
	//首页
	private int first;
	//上页
	private int prev;
	//下页
	private int next;
	//尾页
	private int last;

	//开始索引
	private int startIndex;

	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getCurPage() {
		if(curPage<1)
			return 1;
		return curPage;
	}
	public void setCurPage(int curPage) {

		this.curPage = curPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPages() {
		return getTotalCount()%getPageSize()==0?getTotalCount()/getPageSize():getTotalCount()/getPageSize()+1;
	}

	public int getFirst() {
		return 1;
	}

	public int getPrev() {
		return getCurPage()<=1?1:getCurPage()-1;
	}

	public int getNext() {
		return getCurPage()<getTotalPages()?getCurPage()+1:1;
	}

	public int getLast() {
		return getTotalPages();
	}


	public int getStartIndex() {
		return (this.curPage-1)*this.pageSize;
	}
	public Pager(int curPage, int pageSize, int totalCount) {
		super();
		this.curPage = curPage;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}
	public Pager() {
		super();
	}
	public Pager(List list, int curPage, int pageSize, int totalCount) {
		super();
		this.list = list;
		this.curPage = curPage;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}

}