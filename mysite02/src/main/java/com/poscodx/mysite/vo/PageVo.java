package com.poscodx.mysite.vo;

public class PageVo {
	private int startPage;
	private int endPage;
	private int currentPage;
	private int nextPage;
	private int prevPage;
	private int totalPage;
	
	public PageVo(int page) {
		this.currentPage = page;
	}
	
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalpage) {
		this.totalPage = totalpage;
	}
	@Override
	public String toString() {
		return "PageVo [startPage=" + startPage + ", endPage=" + endPage + ", currentPage=" + currentPage
				+ ", nextPage=" + nextPage + ", prevPage=" + prevPage + ", totalPage=" + totalPage + "]";
	}
	
	
	
}
