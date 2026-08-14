package com.sist.web.commons;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceprtion {
	@ExceptionHandler(Exception.class)
	public void exception(Exception ex) {
		System.out.println("==============예외 발생===============");
		ex.printStackTrace();
	}
	@ExceptionHandler(Throwable.class)
	public void throwable(Throwable ex) {
		System.out.println("==============에러 발생===============");
		ex.printStackTrace();
	}

}
