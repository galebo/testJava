package com.galebo.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPoolExecutor {
	private static int produceTaskSleepTime = 2;
	private static int produceTaskMaxNumber = 10;
	public static int consumeTaskSleepTime = 2000;

	public static void main(String[] args) {
		// ����һ���̳߳�
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 6, TimeUnit.SECONDS,
//				new ArrayBlockingQueue<Runnable>(3), new ThreadPoolExecutor.DiscardOldestPolicy());
		new ArrayBlockingQueue<Runnable>(3), new ThreadPoolExecutor.CallerRunsPolicy());

		for (int i = 1; i <= produceTaskMaxNumber; i++) {
			try {
				// ����һ�����񣬲�������뵽�̳߳�
				String task = "task@ " + i;
				Log.printf("put " + task);
				threadPool.execute(new ThreadPoolTask(task));

				// ���ڹ۲죬�ȴ�һ��ʱ��
				Thread.sleep(produceTaskSleepTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		threadPool.shutdown();
	}
}

/**
 * �̳߳�ִ�е�����
 */
class ThreadPoolTask implements Runnable {
	// ������������Ҫ������
	private Object threadPoolTaskData;

	ThreadPoolTask(Object tasks) {
		this.threadPoolTaskData = tasks;
	}

	public void run() {
		// ����һ����������Ĵ���ʽ̫���ˣ�������һ����ӡ���
		Log.printf(Thread.currentThread().getName()+" start .." + threadPoolTaskData);

		try {
			// //���ڹ۲죬�ȴ�һ��ʱ��
			Thread.sleep(TestThreadPoolExecutor.consumeTaskSleepTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.printf(Thread.currentThread().getName()+" end .." + threadPoolTaskData);
	}

}