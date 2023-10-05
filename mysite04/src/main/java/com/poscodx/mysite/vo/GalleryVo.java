package com.poscodx.mysite.vo;

public class GalleryVo {
	private Long no;
	private String coment;
	private String image_url;
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getComent() {
		return coment;
	}
	public void setComent(String coment) {
		this.coment = coment;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	@Override
	public String toString() {
		return "GalleryVo [no=" + no + ", coment=" + coment + ", image_url=" + image_url + "]";
	}
	
	

}
