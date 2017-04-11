package com.tuwaner.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wanglingyun on 2017/3/27.
 */
public class SynchronisedKeyWorld {
    private static boolean flag = false;
    private final static Object lock = new Object();
    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                synchronized (lock) {
                    System.out.println("a");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //while (flag){
                        System.out.println("c");
                      //  flag =false;
                    //}

                }
            }

        }).start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock){

            lock.notifyAll();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        /*ReentrantLock;
        AbstractQueuedSynchronizer;
        AtomicInteger
        ConcurrentHashMap*/
        /*new Thread(new Runnable() {
            public void run() {
                synchronized (lock) {
                    *//*try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*//*
                    System.out.println("b");
                    //lock.notifyAll();
                    flag = true;
                }
            }


        }).start();*/
    }

}
