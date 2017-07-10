package com.galebo.test;

import com.alibaba.fastjson.JSON;

public class TestFastJson {

	String name;
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static void main(String[] args) {
		TestFastJson group2 = JSON.parseObject("{'name':'dd'}",TestFastJson.class);
		System.out.println(group2.name);
	}
}
