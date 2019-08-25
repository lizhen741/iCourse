package com.iCourse;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class test1 {
	public void f(Collection c) {
		System.out.println("Collection");
	}
 
	public void f(List m) {
		System.out.println("List");
	}
 
	public void f(Set m) {
		System.out.println("Set");
	}

	/*
	this.$confirm("确认添加该题目吗？")
    .then(_ => {
    })
    .catch(_ => {});
    */
	public static void main(String[] args) {
//		test1 t = new test1();
//		
//		Collection[] c = { new ArrayList(), new HashSet() };
//		for (int i = 0; i < c.length; i++) {
//			t.f(c[i]);
//		}

//		A a = new B();
//		
//		System.out.println(a.getresult());
		
//		String exercises_content = "【计算机语言】和【编程语言】是什么鬼！！！";
//		int begin = exercises_content.indexOf("【");
//		if(begin==0) {
//			exercises_content = "  "+exercises_content;
//			begin = exercises_content.indexOf("【");
//		}
//		List<String> answer = new ArrayList<String>();
//		do {
//			int end = exercises_content.indexOf("】", begin);
//			
//			answer.add(exercises_content.substring(begin+1, end));
//
//			begin = exercises_content.indexOf("【", end);
//		} while (begin != -1);
//		String temp;
//		for (String str : answer) {
//			System.out.println(str);
//			temp = exercises_content.replaceAll(str, "");
//			System.out.println(temp);
//			exercises_content = temp;
//		}
//		System.out.println(exercises_content);
	}
	
	public static void function() {
		 File dir = new File("../java");
		 
		 String[] children = dir.list();
	        if (children == null) {
	            System.out.println("该目录不存在");
	        }
	        else {
	            for (int i = 0; i < children.length; i++) {
	                String filename = children[i];
	                System.out.println(filename);
	            }
	        }
	}
	
}
class A{
	public int i = 10;
	
//	public void getresult() {
//		System.out.println(getI() + 30);
//	}
	public int getresult() {
		System.out.println("调用a result");
		return getI() + 10;
	}

	protected int getI() {
		System.out.println("调用a geti");
		return i;
	}
}

class B extends A{
	public int i = 20;

	public int getI() {
		System.out.println("调用b geti");
		return i;
	}
	
//	public int getresult() {
//		return i + 20;
//	}
}