package com.galebo.test;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ScheduledThreadPool {
	public static class WorkerThread implements Runnable{

		private String command;
		    
		    public WorkerThread(String s){
		        this.command=s;
		        Log.printf(Thread.currentThread().getName()+" new Time = "+new Date());
		    }

		    @Override
		    public void run() {
		        Log.printf(Thread.currentThread().getName()+" Start. Time = "+new Date());
		        processCommand();
		        Log.printf(Thread.currentThread().getName()+" End. Time = "+new Date());
		    }

		    private void processCommand() {
		        try {
		            Thread.sleep(5000);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		    }

		    @Override
		    public String toString(){
		        return this.command;
		    }
		}
	public static void main(String[] args) throws InterruptedException {
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
		
		
		//schedule to run after sometime
		Log.printf("Current Time = "+new Date());
		for(int i=0; i<3; i++){
			WorkerThread worker = new WorkerThread("do heavy processing");
			scheduledThreadPool.scheduleAtFixedRate(worker,8, 10, TimeUnit.SECONDS);
			//第一次延迟8秒后执行，开始后10秒钟以后再次启动执行
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