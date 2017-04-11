package com.tuwaner.concurrent;

/**
 * Created by wanglingyun on 2017/3/28.
 */
public class OutPutThread implements Runnable {
    private int num;

    private Object lock;

    public OutPutThread(int num, Object lock) {
        super();
        this.num = num;
        this.lock = lock;
    }

    public void run() {
        try {
            while (true){
                synchronized (lock){
                    System.out.println(num);
                    lock.notifyAll();
                    lock.wait();
                    Thread.sleep(3000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        final Object lock = new Object();
        Thread thread1 = new Thread(new OutPutThread(1,lock));
        Thread thread2 = new Thread(new OutPutThread(2,lock));
        thread1.start();
        thread2.start();
    }
}
