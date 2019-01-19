package com.lhl.completionservice;

import java.util.concurrent.*;

/**
 * Created by lihongli on 2019/1/19
 */
public class MyCompletionService implements Callable<String> {
    private int id;

    public MyCompletionService(int id) {
        this.id = id;
    }

    public static void main(String[] args) throws Exception{
        ExecutorService service = Executors.newCachedThreadPool();
        CompletionService<String> completion = new ExecutorCompletionService<>(service);
        for (int i = 0; i < 10; i++) {
            completion.submit(new MyCompletionService(i));
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(completion.take().get());
        }
        service.shutdown();
    }

    @Override
    public String call() throws Exception {
        int time = (int)(Math.random()*1000);
        System.out.println(this.id + " start");
        Thread.sleep(time);
        System.out.println(this.id + " end");
        return this.id + ":" + time;
    }
}
