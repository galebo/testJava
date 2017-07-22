package com.galebo.test.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

import com.galebo.test.Log;

  
class Producer extends Thread {  
    List<Integer> list = new ArrayList<>();  
    Exchanger<List<Integer>> exchanger = null;  
    public Producer(Exchanger<List<Integer>> exchanger) { 
        this.exchanger = exchanger;  
    }  
    @Override  
    public void run() {  
        Random rand = new Random();  
        for(int i=0; i<10; i++) {  
            list.clear();  
            list.add(rand.nextInt(10000));  
            list.add(rand.nextInt(10000));  
            list.add(rand.nextInt(10000));  
            list.add(rand.nextInt(10000));  
            list.add(rand.nextInt(10000));  
            try {  
                list = exchanger.exchange(list);
                Log.printf("exchanger send");  
            } catch (InterruptedException e) {
                e.printStackTrace();  
            }  
        }  
    }  
}  
  
class Consumer extends Thread {  
    List<Integer> list = new ArrayList<>();  
    Exchanger<List<Integer>> exchanger = null;  
    public Consumer(Exchanger<List<Integer>> exchanger) {
        this.exchanger = exchanger;  
    }  
    @Override  
    public void run() {  
        for(int i=0; i<10; i++) {  
            try {  
                list = exchanger.exchange(list); 
                Log.printf("exchanger get start");
                Log.printf(list.get(0)+", ");  
                Log.printf(list.get(1)+", ");  
                Log.printf(list.get(2)+", ");  
                Log.printf(list.get(3)+", ");  
                Log.printf(list.get(4)+", ");
                Log.printf("exchanger get end");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();  
            }  
        }  
    }  
}
public class TestExchanger {  
	  
    public static void main(String[] args) {  
        Exchanger<List<Integer>> exchanger = new Exchanger<>();  
        new Consumer(exchanger).start();  
        new Producer(exchanger).start();  
    }  
  
}  