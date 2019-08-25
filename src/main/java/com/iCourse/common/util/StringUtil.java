package com.iCourse.common.util;

public class StringUtil {

	public static boolean isEmpty( String content ) {
		return content == null || "".equals(content.trim());
	}
	
	public static boolean isNotEmpty( String content ) {
		return !isEmpty(content);
	}
}
