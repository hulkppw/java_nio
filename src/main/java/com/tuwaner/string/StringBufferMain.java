package com.tuwaner.string;

/**
 * Created by wanglingyun on 2017/3/29.
 */
public class StringBufferMain {
    static Integer count = 0;

    public static void main(String[] args) {
        StringBufferMain main = new StringBufferMain();
        StringBuffer buffer = new StringBuffer("abc");
        for (int i = 0; i < 20; i++) {
            Thread t1 = new Thread(new Task(buffer, 1, i, main));
            t1.start();

        }

        /*Thread t1 = new Thread(new Task(buffer, 1, 1));
        t1.start();
        Thread t2 = new Thread(new Task(buffer, 1, 2));
        t2.start();
        Thread t3 = new Thread(new Task(buffer, 1, 3));
        t3.start();*/

    }

    public void inc() {
        synchronized (count){
            //++count;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(++count);
        }

        //System.out.println(++count);

    }
}

class Task implements Runnable {

    private StringBuffer buffer;

    int offset;

    int i;

    StringBufferMain main;

    public Task(StringBuffer buffer, int offset, int i, StringBufferMain main) {
        this.buffer = buffer;
        this.offset = offset;
        this.i = i;
        this.main = main;
    }

    public void run() {

        insert();
        main.inc();

        //System.out.println(i + " : " +buffer.toString());
    }

    private StringBuffer insert() {
        return buffer.insert(offset, (Object) i);
    }

    public StringBuffer getBuffer() {
        return buffer;
    }

    public int getOffset() {
        return offset;
    }

    public int getI() {
        return i;
    }

    public void setBuffer(StringBuffer buffer) {
        this.buffer = buffer;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setI(int i) {
        this.i = i;
    }
}
