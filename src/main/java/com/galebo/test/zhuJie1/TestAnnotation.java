package com.galebo.test.zhuJie1;

import java.util.List;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

public class TestAnnotation {
	public static void main(String[] args) {
		List<Integer> useCases = new ArrayList<Integer>();
		Collections.addAll(useCases, 47, 48, 49, 50);
		trackUseCases(useCases, PasswordUtils.class);
	}

	public static void trackUseCases(List<Integer> useCases, Class<?> _class) {
		for (Method method : _class.getDeclaredMethods()) {
			UseCase uc = method.getAnnotation(UseCase.class);
			if (uc != null) {
				System.out.println("Found Use Case:" + uc.id() + " " + uc.description());
				useCases.remove(new Integer(uc.id()));
			}
		}
		for (int i : useCases) {
			System.out.println("Warning: Missing use case-" + i);
		}
	}
}
