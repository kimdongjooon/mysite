package com.poscodx.mysite.vo;

public class BoardListVo {
	private int no;
	private String title;
	private String name;
	private String hit;
	private String reg_date;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHit() {
		return hit;
	}
	public void setHit(String hit) {
		this.hit = hit;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	@Override
	public String toString() {
		return "BoardListVo [no=" + no + ", title=" + title + ", name=" + name + ", hit=" + hit + ", reg_date="
				+ reg_date + "]";
	}
	
	
}
