package com.galebo.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class TestSemaphore {
	static class MyRunnable implements Runnable {
		public MyRunnable(Semaphore semp, int nO) {
			super();
			this.semp = semp;
			NO = nO;
		}

		Semaphore semp;
		int NO;

		public void run() {
			try {
				// 获取许可
				semp.acquire();
				Log.printf("Accessing: " + NO);
				Thread.sleep((long) (Math.random() * 10000));
				// 访问完后，释放 ，如果屏蔽下面的语句，则在控制台只能打印5条记录，之后线程一直阻塞
				semp.release();
			} catch (InterruptedException e) {
			}
		}
	};

	public static void main(String[] args) {
		// 线程池
		ExecutorService exec = Executors.newCachedThreadPool();
		// 只能5个线程同时访问
		final Semaphore semp = new Semaphore(5);
//		final Semaphore semp = new Semaphore(5, true);//先进先出
		// 模拟20个客户端访问
		for (int index = 0; index < 20; index++) {
			exec.execute(new MyRunnable(semp, index));
		}
		// 退出线程池
		exec.shutdown();
	}
}