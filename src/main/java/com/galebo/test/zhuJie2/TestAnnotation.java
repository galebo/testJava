package com.galebo.test.zhuJie2;

import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestAnnotation {

	public static void main(String[] args) {
		try {
			for (Method method : TestAnnotation.class.getClassLoader()
					.loadClass(("com.galebo.test.zhuJie2.TestAnnotation")).getMethods()) {
				// checks if MethodInfo annotation is present for the method
				if (method.isAnnotationPresent(com.galebo.test.zhuJie2.MethodInfo.class)) {
					try {
						// iterates all the annotations available in the method
						for (Annotation anno : method.getDeclaredAnnotations()) {
							System.out.println("Annotation in Method " + method + " : " + anno);
						}
						MethodInfo methodAnno = method.getAnnotation(MethodInfo.class);
						if (methodAnno.revision() == 1) {
							System.out.println("Method with revision no 1 = " + method);
						}

					} catch (Throwable ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@MethodInfo(author = "Pankaj", comments = "Main method", date = "Nov 17 2012", revision = 1)
	public String toString() {
		return "Overriden toString method";
	}

	@MethodInfo(comments = "deprecated method", date = "Nov 17 2012")
	public static void oldMethod() {
		System.out.println("old method, don't use it.");
	}

	@MethodInfo(author = "Pankaj", comments = "Main method", date = "Nov 17 2012", revision = 10)
	public static void genericsTest() throws FileNotFoundException {
		List<String> l = new ArrayList<String>();
		l.add("abc");
		oldMethod();
	}

}
