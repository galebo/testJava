package com.galebo.test;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.galebo.test.ScheduledThreadPool.WorkerThread;


public class ScheduledThreadPool2 {

	public static void main(String[] args) throws InterruptedException {
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
		
		
		//schedule to run after sometime
		Log.printf("Current Time = "+new Date());
		for(int i=0; i<3; i++){
			WorkerThread worker = new WorkerThread("do heavy processing");
			scheduledThreadPool.schedule(worker, 10, TimeUnit.SECONDS);
		}
		
		//add some delay to let some threads spawn by scheduler
		Thread.sleep(20000);
		
		scheduledThreadPool.shutdown();
		while(!scheduledThreadPool.isTerminated()){
			//wait for all tasks to finish
		}
		Log.printf("Finished all threads");
	}

}