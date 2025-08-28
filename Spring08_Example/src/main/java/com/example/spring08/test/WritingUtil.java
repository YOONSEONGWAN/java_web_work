package com.example.spring08.test;

import org.springframework.stereotype.Component;

// @Component 어노테이션을 붙여서 spring 이 관리하는 bean 으로 만들어준다. 
@Component
public class WritingUtil {
	public void writeLetter() {
		
		System.out.println("편지를 써요");	
		try {
			Thread.sleep(1000);
		}catch(Exception e) {}
		
	}
	
	public void writeReport() {
		
		System.out.println("보고서를 써요");
		try {
			Thread.sleep(1000);
		}catch(Exception e) {}
		
	}
	public void writeDiary() {
		
		System.out.println("일기를 써요");	
		try {
			Thread.sleep(1000);
		}catch(Exception e) {}
		
	}	
}
