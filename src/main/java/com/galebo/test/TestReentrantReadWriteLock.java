package com.galebo.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;  
  
/** 
 * @author: yanxuxin 
 * @date: 2010-1-7 
 */  
public class TestReentrantReadWriteLock {  
  
    public static void main(String[] args) {  
//        testReadLock();
        testWriteLock();
    }  
      
    public static void testReadLock() {  
       final ReadWriteLockSampleSupport support = new ReadWriteLockSampleSupport();  
        support.initCache();  
          
        Runnable get = new Runnable() {  
            public void run() {  
                support.get("test");  
            }  
        };  
          
          
        Runnable put = new Runnable() {  
            public void run() {  
                support.put("test", "test");  
            }  
        };
        new Thread(get).start();  
        new Thread(get).start();  
		new Thread(put).start();  
    }  
      
    public static void testWriteLock() {  
       final ReadWriteLockSampleSupport support = new ReadWriteLockSampleSupport();  
        support.initCache();  
          
        new Thread(new Runnable() {  
            public void run() {  
                support.put("key1", "value1");  
            }  
        }).start();  
          
        new Thread(new Runnable() {  
            public void run() {  
                support.put("key2", "value2");  
            }  
        }).start();  
          
        new Thread(new Runnable() {  
            public void run() {  
                support.get("key1");  
            }  
        }).start();  
    }  
}  
  
class ReadWriteLockSampleSupport {  
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();  
    private final Lock readLock = lock.readLock();  
    private final Lock writeLock = lock.writeLock();  
      
    private volatile  boolean completed;  
    private Map<String,String> cache;  
      
    public void initCache() {  
        readLock.lock();  
        if(!completed) {  
            // Must release read lock before acquiring write lock  
            readLock.unlock(); // (1)  
            writeLock.lock();  // (2)  
            if(!completed) {  
                cache = new HashMap<String,String>(32);  
                completed = true;  
            }  
            // Downgrade by acquiring read lock before releasing write lock  
            readLock.lock();    // (3)  
            writeLock.unlock(); // (4) Unlock write, still hold read  
        }  
          
        Log.printf("empty? " + cache.isEmpty());  
        readLock.unlock();  
    }  
      
    public String get(String key) {  
        readLock.lock();  
        Log.printf(Thread.currentThread().getName() + " read.");  
        startTheCountdown();  
        try{  
            return cache.get(key);  
        }  
        finally{  
            readLock.unlock();  
        }  
    }  
      
    public String put(String key, String value) {  
        writeLock.lock();  
        Log.printf(Thread.currentThread().getName() + " write.");  
        startTheCountdown();  
        try{  
            return cache.put(key, value);  
        }  
        finally {  
            writeLock.unlock();  
        }  
    }  
      
    /** 
     * A simple countdown,it will stop after about 5s.  
     */  
    private void startTheCountdown() {  
        long currentTime = System.currentTimeMillis();  
        for(;;) {  
            long diff = System.currentTimeMillis() - currentTime;  
            if(diff > 5000) {  
                break;  
            }  
        }  
    }  
}  

/*
2017-06-27 20:13:57 :empty? true
2017-06-27 20:13:57 :Thread-1 read.
2017-06-27 20:13:57 :Thread-0 read.
2017-06-27 20:14:02 :Thread-2 write.



2017-06-27 20:16:09 :empty? true
2017-06-27 20:16:09 :Thread-0 write.
2017-06-27 20:16:14 :Thread-1 write.
2017-06-27 20:16:19 :Thread-2 read.

*/