package net.kanson.demo;

/**
 * @Description: 一个分开打印奇数、偶数的dmeo
 * @Author: Kanson
 * @Date: 2020-09-24
 */
public class PrintOddAndEven implements Runnable {

    int num = 1; // 待打印的数字

    final Object lock = new Object(); // 实例化一个lock

    @Override
    public void run() {
        try {
            synchronized (lock) {
                while (true) {
                    lock.notify(); // 唤醒其他线程
                    if (num > 100) break;
                    Thread.sleep(200);
                    System.out.printf("当前线程：%s，num: %d\n", Thread.currentThread().getName(), num++);
                    lock.wait(); // 阻塞当前线程，释放持有的锁
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PrintOddAndEven p = new PrintOddAndEven();
        Thread t1 = new Thread(p, "线程1");
        Thread t2 = new Thread(p, "线程2");
        t1.start();
        t2.start();
    }

}