package com.galebo.test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadWriteLock {

	static class PricesInfo {
		private int price1;
		private int price2;
		private ReadWriteLock lock;

		public PricesInfo() {
			price1 = 1;
			price2 = 1;
			lock = new ReentrantReadWriteLock();

		}

		public int getPrice1() {
			lock.readLock().lock();
			int value = price1;
			lock.readLock().unlock();
			return value;
		}

		public int getPrice2() {
			lock.readLock().lock();
			int value = price2;
			lock.readLock().unlock();
			return value;
		}

		public void setPrice1(int price1) {
			this.price1 = price1;
		}

		public void setPrice2(int price2) {
			this.price2 = price2;
		}

		public void setPrices(int price1, int price2) {
			lock.writeLock().lock();
			this.price1 = price1;
			this.price2 = price2;
			lock.writeLock().unlock();
		}
	}

	static class Reader implements Runnable {
		private PricesInfo pricesInfo;

		public Reader(PricesInfo pricesInfo) {
			this.pricesInfo = pricesInfo;
		}

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				Log.printf("%s:Price 2:%d,:Price 1:%d", Thread.currentThread().getName(), pricesInfo.getPrice2(), pricesInfo.getPrice1());
			}

		}
	}

	static class Writer implements Runnable {
		private PricesInfo pricesInfo;

		public Writer(PricesInfo pricesInfo) {
			this.pricesInfo = pricesInfo;
		}

		@Override
		public void run() {

			for (int i = 0; i < 3; i++) {
				int p1 = new Random().nextInt(47);
				int p2 = new Random().nextInt(47) * 10;

				Log.printf("Writer:Attempt to modify the prices." + p1 + "    price2:" + p2);
				pricesInfo.setPrices(p1, p2);
				Log.printf("Writer:Prices have been modified." + p1 + "    price2:" + p2);
				try {
					Thread.sleep(2);
				} catch (Exception e) {
				}
			}
		}
	}

	public static void main(String[] args) {
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		PricesInfo pricesInfo = new PricesInfo();
		for (int i = 0; i < 5; i++) {
			newCachedThreadPool.submit( new Reader(pricesInfo));
		}
		newCachedThreadPool.submit( new Writer(pricesInfo));
		newCachedThreadPool.shutdown();
	}

}