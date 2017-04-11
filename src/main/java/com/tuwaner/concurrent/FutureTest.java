package com.tuwaner.concurrent;

import java.util.HashMap;
import java.util.concurrent.*;

/**
 * Created by wanglingyun on 2017/4/1.
 */
public class FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        Future future = service.submit(new Task());
        Object o = future.get();
        if (o != null) {
            ConcurrentHashMap map;
            HashMap hashMap;
        }
        System.out.print("end");
    }

    static class Task implements Callable {

        public Object call() throws Exception {
            Thread.sleep(3000);
            return null;
        }
    }

}
