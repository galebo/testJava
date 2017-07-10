package com.galebo.test.basic;

import java.util.IdentityHashMap;
import java.util.Map.Entry;

public class TestIdentityHashMap {
	public static void main(String[] args) {
		IdentityHashMap<String, Object> map = new IdentityHashMap<String, Object>();
		String fsString = new String("xx");
		map.put(fsString, "first");
		map.put(new String("xx"), "second");
		for (Entry<String, Object> entry : map.entrySet()) {
			System.out.print(entry.getKey() + "    ");
			System.out.println(entry.getValue());
		}
		System.out.println("idenMap=" + map.containsKey(fsString));
		System.out.println("idenMap=" + map.get(fsString));
	}
}
