package com.lhl.car_parking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 以一个停车场是运作为例。为了简单起见，假设停车场只有三个车位，一开始三个车位都是空的。
 * 这时如果同时来了五辆车，看门人允许其中三辆不受阻碍的进入，然后放下车拦，剩下的车则必须在入口等待，
 * 此后来的车也都不得不在入口处等待。这时，有一辆车离开停车场，看门人得知后，打开车拦，放入一辆，
 * 如果又离开两辆，则又可以放入两辆，如此往复。
 * 这个停车系统中，每辆车就好比一个线程，看门人就好比一个信号量，看门人限制了可以活动的线程。
 * 假如里面依然是三个车位，但是看门人改变了规则，要求每次只能停两辆车，那么一开始进入两辆车，
 * 后面得等到有车离开才能有车进入，但是得保证最多停两辆车。对于Semaphore类而言，就如同一个看门人，限制了可活动的线程数。
 * Created by lihongli on 2019/1/21
 */
public class CarParkingThread extends Thread {

    private Semaphore semaphore;
    private String name;

    public CarParkingThread(Semaphore semaphore, String name) {
        this.semaphore = semaphore;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            if (semaphore.availablePermits() < 1)
                System.err.println(name + " 想进入停车场，需排队," + System.currentTimeMillis());
            semaphore.acquire();
            System.out.println(name + " 进入停车场," + System.currentTimeMillis());
            Thread.sleep((int)(Math.random()*10000));
            System.out.println(name + " 开出了停车位," + System.currentTimeMillis());
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(5, true);
        for (int i = 0; i < 10; i++) {
            service.execute(new CarParkingThread(semaphore, "车辆" + i));
        }
        semaphore.acquire(5);
        semaphore.release(5);
        service.shutdown();
    }
}
