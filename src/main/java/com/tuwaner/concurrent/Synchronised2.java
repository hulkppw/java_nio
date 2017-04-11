package com.tuwaner.concurrent;

/**
 * Created by wanglingyun on 2017/3/30.
 */
public class Synchronised2 {

    public synchronized void method1(){
        System.out.println("Method 1 start");
        System.out.println("Method 1 execute");
        System.out.println("Method 1 end");
    }

    public synchronized void method2(){
        System.out.println("Method 2 start");
        System.out.println("Method 2 execute");
        System.out.println("Method 2 end");
    }

    public static void main(String[] args) {
        final Synchronised2 synchronised = new Synchronised2();
        new Thread(new Runnable() {
            public void run() {

                synchronised.method2();
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                synchronised.method1();
                //synchronised.method2();
                System.out.println("thread1 end");
            }
        }).start();



    }
}
