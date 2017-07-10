package com.galebo.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Participant implements Runnable {
	private final CountDownLatch countDownLatch;
	private String name;

	public Participant(CountDownLatch countDownLatch, String name) {
		this.countDownLatch = countDownLatch;
		this.name = name;

	}

	@Override
	public void run() {

		long duration = (long) (Math.random() * 10);
		try {
			TimeUnit.SECONDS.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.printf(name + " has arrived.");
		countDownLatch.countDown();
		Log.printf("VideoConference:Waiting for " + countDownLatch.getCount());
	}

}

class Videoconference implements Runnable {
	CountDownLatch countDownLatch;

	public Videoconference(CountDownLatch countDownLatch) {
		this.countDownLatch=countDownLatch;
	}

	@Override
	public void run() {

		Log.printf("VideoConference:Initialization:" + countDownLatch.getCount());

		try {
			countDownLatch.await();
			System.out.printf("VideoConference: All the participants have come\n");
			System.out.printf("VideoConference: Let's start...\n");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class TestCountDownLatch {

	public static void main(String[] args) {
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		CountDownLatch countDownLatch = new CountDownLatch(9);
		Videoconference conference = new Videoconference(countDownLatch);
		newCachedThreadPool.submit(conference);
		for (int i = 0; i < 9; i++) {
			Participant p = new Participant(countDownLatch, "Participant" + i);
			newCachedThreadPool.submit(p);
		}
		newCachedThreadPool.shutdown();
	}

}
/*
 * http://blog.csdn.net/junshuaizhang/article/details/39580751

VideoConference:Initialization:9
Participant1 has arrived.
VideoConference:Waiting for 8
Participant4 has arrived.
VideoConference:Waiting for 7
Participant0 has arrived.
VideoConference:Waiting for 6
Participant9 has arrived.
VideoConference:Waiting for 5
Participant7 has arrived.
VideoConference:Waiting for 4
Participant8 has arrived.
VideoConference:Waiting for 3
Participant2 has arrived.
VideoConference:Waiting for 2
Participant6 has arrived.
VideoConference:Waiting for 1
Participant3 has arrived.
VideoConference:Waiting for 0
VideoConference: All the participants have come
VideoConference: Let's start...
Participant5 has arrived.
VideoConference:Waiting for 0
*/