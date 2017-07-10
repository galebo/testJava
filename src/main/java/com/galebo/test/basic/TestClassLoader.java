package com.galebo.test.basic;

/**
 * @see http://blog.csdn.net/briblue/article/details/54973413
 * @author Administrator
 *
 */
public class TestClassLoader {

	public static void main(String[] args) {
		ClassLoader cl = TestClassLoader.class.getClassLoader();
		System.out.println("ClassLoader is:" + cl.toString());
		System.out.println("ClassLoader\'s parent is:"+cl.getParent().toString());
	}

}
