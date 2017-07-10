package com.galebo.test.basic;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.util.ASMUtils;

public class Many {
	void empty(){
		List<String> dd1=Collections.emptyList();
		Set<String> dd2=Collections.emptySet();
		Map<String,String> dd3=Collections.emptyMap();
		System.out.println(dd1.size());
		System.out.println(dd2.size());
		System.out.println(dd3.size());
	}
	void identityHashCode1(){
        String string1 = "wxg";
        String string2 = "wxg";

        System.out.println(System.identityHashCode(string1));
        System.out.println(System.identityHashCode(string2));

        System.out.println(string1.hashCode());
        System.out.println(string2.hashCode());
        /*
31168322
31168322
118182
118182
         */
	}
	void identityHashCode2(){
	    String string1 = new String("wxg");
	    String string2 = "wxg";

	    System.out.println(System.identityHashCode(string1));
	    System.out.println(System.identityHashCode(string2));

	    System.out.println(string1.hashCode());
	    System.out.println(string2.hashCode());
	    /*
	    17225372
	    31168322
	    118182
	    118182 */
	}
	void type(){
		System.out.println(Integer.TYPE);
		System.out.println(String.class);
		System.out.println(ASMUtils.getDesc(String.class));
	}
	/**
	 * @see http://www.tuicool.com/articles/imyueq
	 */
	public class Car {
		private double price;
		private String colour;
		
		public Car(double price, String colour){
			this.price = price;
			this.colour = colour;
		}
		
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public String getColour() {
			return colour;
		}
		public void setColour(String colour) {
			this.colour = colour;
		}
		
		public String toString(){
			return colour +"car costs $"+price;
		}
		
	}
	void weakReference(){
		WeakReference<Car> weakCar =null;
		{
			Car car = new Car(22000,"silver");
			weakCar = new WeakReference<Car>(car);
		}
		
		int i=0;
		
		while(true){
			if(weakCar.get()!=null){
				i++;
				System.out.println("Object is alive for "+i+" loops - "+weakCar);
			}else{
				System.out.println("Object has been collected.");
				break;
			}
		}
	}
	public static void main(String[] args) {
		Many dee=new Many();

//		dee.identityHashCode1();
//		dee.identityHashCode2();
//		dee.type();
		dee.weakReference();
	}
}
