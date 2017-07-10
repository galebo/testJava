package com.galebo.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static void printf(String format, Object ... args){
		String dd=sdf.format(new Date());
		System.out.printf(dd+" :"+format+"\n",args);
	}
	public static void main(String[] args) {
		printf("%s","dddd");
	}
}
