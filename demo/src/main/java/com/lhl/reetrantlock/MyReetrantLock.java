package com.lhl.reetrantlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lihongli on 2019/1/19
 */
public class MyReetrantLock extends Thread {

    TestReentrantLock lock;
    private int id;

    public MyReetrantLock(TestReentrantLock lock, int id) {
        this.lock = lock;
        this.id = id;
    }

    public void run(){
        lock.print(id);
    }

    public static void main(String[] args){
        ExecutorService service = Executors.newCachedThreadPool();
        TestReentrantLock lock = new TestReentrantLock();
        for (int i = 0; i < 10; i++) {
            service.submit(new MyReetrantLock(lock, i));
        }
        service.shutdown();
    }
}

class TestReentrantLock {
    private ReentrantLock lock = new ReentrantLock();

    public void print(int str) {
        try {
            lock.lock();
            System.out.println(str + "获得");
            Thread.sleep((int) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(str + "释放");
            lock.unlock();
        }
    }
}

