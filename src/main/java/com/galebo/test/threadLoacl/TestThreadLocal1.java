package com.galebo.test.threadLoacl;


public class TestThreadLocal1 {
	public static class SeqCreater {

		// ①通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值
		private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
			public Integer initialValue() {
				return 0;
			}
		};
		// ②获取下一个序列值
		public int getNextNum() {
			seqNum.set(seqNum.get() + 1);
			return seqNum.get();
		}
	}
	private static class TestClient extends Thread {
		private SeqCreater seqCreater;

		public TestClient(SeqCreater sn) {
			this.seqCreater = sn;
		}

		public void run() {
			for (int i = 0; i < 3; i++) {
				// ④每个线程打出3个序列值
				System.out.println("thread[" + Thread.currentThread().getName() + "] --> sn[" + seqCreater.getNextNum() + "]");
			}
		}
	}
	public static void main(String[] args) {
		SeqCreater sn = new SeqCreater();
		// ③ 3个线程共享sn，各自产生序列号
		TestClient t1 = new TestClient(sn);
		TestClient t2 = new TestClient(sn);
		TestClient t3 = new TestClient(sn);
		t1.start();
		t2.start();
		t3.start();
	}

}
