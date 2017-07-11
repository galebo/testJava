package com.galebo.test.queue;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.galebo.test.Log;

public class TestArrayBlockingQueue {
	static class Message {
		private String msg;

		public Message(String str) {
			this.msg = str;
		}

		public String getMsg() {
			return msg;
		}

	}

	static class Consumer implements Runnable {

		private BlockingQueue<Message> queue;

		public Consumer(BlockingQueue<Message> q) {
			this.queue = q;
		}

		@Override
		public void run() {
			try {
				Message msg;
				// consuming messages until exit message is received
				while ((msg = queue.take()).getMsg() != "exit") {
					Thread.sleep(1000);
					Log.printf("Consumed " + msg.getMsg()+" "+new Date());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	static class Producer implements Runnable {

	    private BlockingQueue<Message> queue;
	    
	    public Producer(BlockingQueue<Message> q){
	        this.queue=q;
	    }
	    @Override
	    public void run() {
	        //produce messages
	        for(int i=0; i<20; i++){
	            Message msg = new Message(""+i);
	            try {
	                Thread.sleep(i);
	                queue.put(msg);
	                Log.printf("Produced "+msg.getMsg()+" "+new Date());
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	        //adding exit message
	        Message msg = new Message("exit");
	        try {
	            queue.put(msg);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	}

	public static void main(String[] args) {
		// Creating BlockingQueue of size 10
		BlockingQueue<Message> queue = new ArrayBlockingQueue<Message>(10);
		Producer producer = new Producer(queue);
		Consumer consumer = new Consumer(queue);
		// starting producer to produce messages in queue
		new Thread(producer).start();
		// starting consumer to consume messages from queue
		new Thread(consumer).start();
		Log.printf("Producer and Consumer has been started");
	}

}
/*
Producer and Consumer has been started
Produced 0 Tue Jun 27 18:51:13 CST 2017
Produced 1 Tue Jun 27 18:51:13 CST 2017
Produced 2 Tue Jun 27 18:51:13 CST 2017
Produced 3 Tue Jun 27 18:51:13 CST 2017
Produced 4 Tue Jun 27 18:51:13 CST 2017
Produced 5 Tue Jun 27 18:51:13 CST 2017
Produced 6 Tue Jun 27 18:51:13 CST 2017
Produced 7 Tue Jun 27 18:51:13 CST 2017
Produced 8 Tue Jun 27 18:51:13 CST 2017
Produced 9 Tue Jun 27 18:51:13 CST 2017
Produced 10 Tue Jun 27 18:51:13 CST 2017
Consumed 0 Tue Jun 27 18:51:14 CST 2017
Produced 11 Tue Jun 27 18:51:14 CST 2017
Consumed 1 Tue Jun 27 18:51:15 CST 2017
Produced 12 Tue Jun 27 18:51:15 CST 2017
Consumed 2 Tue Jun 27 18:51:16 CST 2017
Produced 13 Tue Jun 27 18:51:16 CST 2017
Consumed 3 Tue Jun 27 18:51:17 CST 2017
Produced 14 Tue Jun 27 18:51:17 CST 2017
Consumed 4 Tue Jun 27 18:51:18 CST 2017
Produced 15 Tue Jun 27 18:51:18 CST 2017
Consumed 5 Tue Jun 27 18:51:19 CST 2017
Produced 16 Tue Jun 27 18:51:19 CST 2017
Consumed 6 Tue Jun 27 18:51:20 CST 2017
Produced 17 Tue Jun 27 18:51:20 CST 2017
Consumed 7 Tue Jun 27 18:51:21 CST 2017
Produced 18 Tue Jun 27 18:51:21 CST 2017
Consumed 8 Tue Jun 27 18:51:22 CST 2017
Produced 19 Tue Jun 27 18:51:22 CST 2017
Consumed 9 Tue Jun 27 18:51:23 CST 2017
Consumed 10 Tue Jun 27 18:51:24 CST 2017
Consumed 11 Tue Jun 27 18:51:25 CST 2017
Consumed 12 Tue Jun 27 18:51:26 CST 2017
Consumed 13 Tue Jun 27 18:51:27 CST 2017
Consumed 14 Tue Jun 27 18:51:28 CST 2017
Consumed 15 Tue Jun 27 18:51:29 CST 2017
Consumed 16 Tue Jun 27 18:51:30 CST 2017
Consumed 17 Tue Jun 27 18:51:31 CST 2017
Consumed 18 Tue Jun 27 18:51:32 CST 2017
Consumed 19 Tue Jun 27 18:51:33 CST 2017
*/