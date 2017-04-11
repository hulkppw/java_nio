package com.tuwaner.concurrent;

/**
 * Created by wanglingyun on 2017/3/30.
 */
public class NotifyTest {

    private synchronized void testWait(){
        System.out.println(Thread.currentThread().getName() +" Start-----");
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //while (flag){
        System.out.println(Thread.currentThread().getName() +" End-------");
    }

    public static void main(String[] args) throws InterruptedException {
        final NotifyTest test = new NotifyTest();

        for (int i = 0; i< 5; i++){
            new Thread(new Runnable() {
                public void run() {
                    test.testWait();
                }
            }).start();
        }
        synchronized (test){
            test.notify();
        }
        Thread.sleep(3000);
        System.out.println("-----------分割线-------------");
        synchronized (test){
            test.notifyAll();
        }
    }
}
