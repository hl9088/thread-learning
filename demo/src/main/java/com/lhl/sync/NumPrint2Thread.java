package com.lhl.sync;

/**
 * 线程123打印顺序不能保证
 * Created by lihongli on 2019/1/14
 */
public class NumPrint2Thread implements Runnable {
    private Object self;
    private Object prev;
    private int threadId;
    private int printNum;

    public NumPrint2Thread(Object prev, Object self, int threadId, int printNum) {
        this.prev = prev;
        this.self = self;
        this.threadId = threadId;
        this.printNum = printNum;
    }

    public void run() {
        System.out.println("当前正在运行的线程" + Thread.currentThread().getName());
        while (printNum < 75) {
            synchronized (prev) {
                synchronized (self) {
                    for (int i = 0; i < 5; i++) {
                        System.out.println("线程" + threadId + " : " + ++printNum);
                    }
                    printNum += 10;
                    self.notify();
                }
                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();


        Thread t1 =new Thread(new NumPrint2Thread(c, a, 1, 0));
        t1.setName("线程1");
        t1.start();

        Thread t2 = new Thread(new NumPrint2Thread(a, b, 2, 5));
        t2.setName("线程2");
        t2.start();

        Thread t3 =new Thread(new NumPrint2Thread(b, c, 3, 10));
        t3.setName("线程3");
        t3.start();
    }
}
