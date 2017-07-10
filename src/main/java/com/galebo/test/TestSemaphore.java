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
				// ��ȡ���
				semp.acquire();
				Log.printf("Accessing: " + NO);
				Thread.sleep((long) (Math.random() * 10000));
				// ��������ͷ� ����������������䣬���ڿ���ֻ̨�ܴ�ӡ5����¼��֮���߳�һֱ����
				semp.release();
			} catch (InterruptedException e) {
			}
		}
	};

	public static void main(String[] args) {
		// �̳߳�
		ExecutorService exec = Executors.newCachedThreadPool();
		// ֻ��5���߳�ͬʱ����
		final Semaphore semp = new Semaphore(5);
//		final Semaphore semp = new Semaphore(5, true);//�Ƚ��ȳ�
		// ģ��20���ͻ��˷���
		for (int index = 0; index < 20; index++) {
			exec.execute(new MyRunnable(semp, index));
		}
		// �˳��̳߳�
		exec.shutdown();
	}
}