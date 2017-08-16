package com.galebo.test.concurrent;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import com.galebo.test.Log;

public class TestCompletableFuture {
	static class Client extends Thread {
        CompletableFuture<String> completableFuture;
        Client(String threadName, CompletableFuture<String> f) {
            super(threadName);
            this.completableFuture = f;
        }
        @Override
        public void run() {
            try {
            	Thread.sleep(3000);
            	Log.printf(this.getName() + ": " + completableFuture.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        final CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Client("Client1", completableFuture).start();
        new Client("Client2", completableFuture).start();
        Log.printf("waiting");
        completableFuture.complete("100");
        
        CompletableFuture<Void> thenAccept = completableFuture.thenAccept(new Consumer<String>(){  
            @Override  
            public void accept(String t) {  
            Log.printf(t);  
            Log.printf(Thread.currentThread().getName());  
            }  
        });
		System.out.println(thenAccept);
        //f.completeExceptionally(new Exception());
        try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
