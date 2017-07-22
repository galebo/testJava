package com.galebo.test.concurrent;

import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.galebo.test.Log;

public class TestCyclicBarrier {

    private static final int THREAD_NUM = 5;
    
    public static class WorkerThread implements Runnable{

        CyclicBarrier barrier;
        
        public WorkerThread(CyclicBarrier b){
            this.barrier = b;
        }
        
        @Override
        public void run() {
            try{
                Log.printf("ID:"+Thread.currentThread().getId()+" Worker's waiting "+new Date());
                //线程在这里等待，直到所有线程都到达barrier。
                barrier.await();
                Log.printf("ID:"+Thread.currentThread().getId()+" Working "+new Date());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(THREAD_NUM, new Runnable() {
            //当所有线程到达barrier时执行
            @Override
            public void run() {
                Log.printf("Inside Barrier");
                
            }
        });
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        for(int i=0;i<12;i++){
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            newCachedThreadPool.submit(new WorkerThread(cb));
        }
        newCachedThreadPool.shutdown();
    }

}
/*
以下是输出：
Worker's waiting
Worker's waiting
Worker's waiting
Worker's waiting
Worker's waiting
Inside Barrier
ID:12 Working
ID:8 Working
ID:11 Working
ID:9 Working
ID:10 Working
*/