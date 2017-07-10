package com.galebo.test;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPoolExecutor2 {

	private static int queueDeep = 4;

	public void createThreadPool() {
		/*
		 * �����̳߳أ���С�߳���Ϊ2������߳���Ϊ4���̳߳�ά���̵߳Ŀ���ʱ��Ϊ3�룬
		 * ʹ�ö������Ϊ4���н���У����ִ�г�����δ�رգ���λ�ڹ�������ͷ�������񽫱�ɾ����
		 * Ȼ������ִ�г�������ٴ�ʧ�ܣ����ظ��˹��̣��������Ѿ����ݶ�����ȶ�������ؽ����˿��ơ�
		 */
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(queueDeep), new ThreadPoolExecutor.DiscardOldestPolicy());

		// ���̳߳������ 10 ������
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (getQueueSize(tpe.getQueue()) >= queueDeep) {
				Log.printf("������������3�����������");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			ThreadPoolTask ttp = new ThreadPoolTask(i);
			Log.printf("put i:" + i);
			tpe.execute(ttp);
		}

		tpe.shutdown();
	}

	private synchronized int getQueueSize(Queue<Runnable> queue) {
		return queue.size();
	}

	public static void main(String[] args) {
		TestThreadPoolExecutor2 test = new TestThreadPoolExecutor2();
		test.createThreadPool();
	}

	class ThreadPoolTask implements Runnable {
		private int index;

		public ThreadPoolTask(int index) {
			this.index = index;
		}

		public void run() {
			Log.printf(Thread.currentThread() + " index:" + index);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}