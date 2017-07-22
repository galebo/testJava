package com.galebo.test.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.galebo.test.Log;

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

/*
2017-07-10 22:41:25 :Accessing: 2
2017-07-10 22:41:25 :Accessing: 4
2017-07-10 22:41:25 :Accessing: 1
2017-07-10 22:41:25 :Accessing: 0
2017-07-10 22:41:25 :Accessing: 3
2017-07-10 22:41:25 :Accessing: 5
2017-07-10 22:41:26 :Accessing: 6
2017-07-10 22:41:28 :Accessing: 7
2017-07-10 22:41:29 :Accessing: 8
2017-07-10 22:41:29 :Accessing: 9
2017-07-10 22:41:31 :Accessing: 10
2017-07-10 22:41:31 :Accessing: 11
2017-07-10 22:41:34 :Accessing: 12
2017-07-10 22:41:35 :Accessing: 13
2017-07-10 22:41:36 :Accessing: 14
2017-07-10 22:41:37 :Accessing: 15
2017-07-10 22:41:39 :Accessing: 16
2017-07-10 22:41:39 :Accessing: 17
2017-07-10 22:41:40 :Accessing: 18
2017-07-10 22:41:41 :Accessing: 19
*/