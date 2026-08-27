package com.sist.web.commons;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceprtion {
	@ExceptionHandler(Exception.class)
	public void exception(Exception ex) {
		System.err.println("==============예외 발생===============");
		ex.printStackTrace();
	}
	@ExceptionHandler(Throwable.class)
	public void throwable(Throwable ex) {
		System.err.println("==============에러 발생===============");
		ex.printStackTrace();
	}

}
