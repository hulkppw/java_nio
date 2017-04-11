package com.tuwaner.concurrent;

/**
 * Created by wanglingyun on 2017/3/30.
 */
public class Synchronised3 {
    public  void method1(){
        synchronized(this){
            System.out.println("Method 1 start");
            try {
                Thread.sleep(1000);
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Method 1 execute");
            System.out.println("Method 1 end");
        }

    }

    public synchronized void method2(){
        synchronized(this){
            System.out.println("Method 2 start");
            notifyAll();
            System.out.println("Method 2 execute");
            System.out.println("Method 2 end");
        }

    }

    public static void main(String[] args) {
        final Synchronised3 synchronised = new Synchronised3();
        final Synchronised3 synchronised1 = new Synchronised3();
        new Thread(new Runnable() {
            public void run() {
                synchronised.method1();
                //synchronised.method2();

                System.out.println("thread1 end");
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {

                synchronised1.method2();
            }
        }).start();

    }
}
