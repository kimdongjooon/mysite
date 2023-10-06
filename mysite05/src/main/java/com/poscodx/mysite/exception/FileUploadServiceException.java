package com.poscodx.mysite.exception;

public class FileUploadServiceException extends RuntimeException {
	private static final long serialVersionUID = 5400155805945059689L;

	public FileUploadServiceException(String message) {
		super(message);
	}
	
	public FileUploadServiceException() {
		super("FileUploadService Exception Thrown");
	}
}
