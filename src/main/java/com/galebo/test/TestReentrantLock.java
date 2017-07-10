package com.galebo.test;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {

	public static class MyRunnable implements Runnable {
		private Lock lock;

		public MyRunnable(Lock lock) {
			this.lock = lock;
		}

		private void doSomething() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Log.printf(Thread.currentThread().getName() + " do someting " + new Date());
		}

		private void doLogging() {
			// logging, no need for thread safety
		}

		@Override
		public void run() {
			try {
				if (lock.tryLock(10, TimeUnit.SECONDS)) {
					doSomething();
				} else {
					Log.printf(Thread.currentThread().getName() + " tryLock fail " + new Date());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			doLogging();
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Lock lock = new ReentrantLock();
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		executorService.submit(new MyRunnable(lock));
		Thread.sleep(10000);
		executorService.shutdown();

	}
}
/**
2017-07-03 09:53:43 :Accessing: 0
2017-07-03 09:53:43 :Accessing: 2
2017-07-03 09:53:43 :Accessing: 6
2017-07-03 09:53:43 :Accessing: 8
2017-07-03 09:53:43 :Accessing: 4
2017-07-03 09:53:48 :Accessing: 10
2017-07-03 09:53:50 :Accessing: 12
2017-07-03 09:53:50 :Accessing: 14
2017-07-03 09:53:51 :Accessing: 16
2017-07-03 09:53:51 :Accessing: 18
2017-07-03 09:53:52 :Accessing: 1
2017-07-03 09:53:52 :Accessing: 3
2017-07-03 09:53:53 :Accessing: 5
2017-07-03 09:53:58 :Accessing: 7
2017-07-03 09:53:58 :Accessing: 9
2017-07-03 09:53:59 :Accessing: 11
2017-07-03 09:53:59 :Accessing: 13
2017-07-03 09:54:01 :Accessing: 15
2017-07-03 09:54:02 :Accessing: 17
2017-07-03 09:54:02 :Accessing: 19
*/