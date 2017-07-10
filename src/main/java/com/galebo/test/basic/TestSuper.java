package com.galebo.test.basic;

import java.util.ArrayList;
import java.util.List;

public class TestSuper {
	public static class A1{
		
	}
	public static class A11 extends A1{
		
	}
	public static class A12 extends A1{
		
	}
	public void ddd1(List<? super A12>dd){
		
	}
	final public void ddd2(List<? extends A1>dd){
		
	}
	public static void main(String[] args) {
		TestSuper exe=new TestSuper();
		List<A1> dd1=new ArrayList<A1>();
		List<A11> dd2=new ArrayList<A11>();
		exe.ddd1(dd1);
		exe.ddd2(dd2);
	}
}
