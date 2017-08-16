package com.galebo.test;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.galebo.test.ScheduledThreadPool.WorkerThread;


public class ScheduledThreadPool3 {

	public static void main(String[] args) throws InterruptedException {
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
		
		
		//schedule to run after sometime
		Log.printf("Current Time = "+new Date());
		for(int i=0; i<3; i++){
			WorkerThread worker = new WorkerThread("do heavy processing");
			scheduledThreadPool.scheduleWithFixedDelay(worker,8, 3, TimeUnit.SECONDS);
			//第一次延迟8秒后执行，执行完成后3秒钟以后再次启动执行
		}
		
		//add some delay to let some threads spawn by scheduler
		Thread.sleep(30000);
		
		scheduledThreadPool.shutdown();
		while(!scheduledThreadPool.isTerminated()){
			//wait for all tasks to finish
		}
		Thread.sleep(1000);
		Log.printf("Finished all threads");
	}

}