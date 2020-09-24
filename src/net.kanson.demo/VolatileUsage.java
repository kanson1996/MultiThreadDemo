package net.kanson.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 一个使用volatile关键字保证不同线程间共享资源可见性的demo
 * @Version:
 * @Author: Kanson
 * @Date: 2020-09-24 16:45:23
 */
public class VolatileUsage {

    private volatile static List list = new ArrayList(); // 使用volatile修饰

    public void add() {
        list.add("item");
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {

        final VolatileUsage v = new VolatileUsage();

        // t1线程执行添加元素操作
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        v.add();
                        System.out.println("当前线程：" + Thread.currentThread().getName() + "添加1个元素，v.size = " + v.size());
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");

        // t2线程读取元素数目并抛出异常
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (v.size() == 5) {
                        System.out.println("当前线程：" + Thread.currentThread().getName() + "收到v.size = 5的通知，线程终止！");
                        throw new RuntimeException();
                    }
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }

}
