package lgw.java_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Demo1
 * @Description ReentrantLock 的基本用法
 * @Author Numblgw
 * @Date 2019/6/2 18:01
 */
public class Demo1 {
	/**
	 * ReentrantLock 可以替代 synchronized 但是它无法自动释放锁，需要手动释放
	 */
	Lock lock = new ReentrantLock();

	Lock lock2 = new ReentrantLock();

	/**
	 * 使用 lock 锁 与 run2 方法使用同一把锁，互斥
	 */
	public void run1() {
		lock.lock();
		try{
			while(true) {
				System.out.println(Thread.currentThread().getName() + "...正在执行run111");
			}
		}finally {
			// 使用 ReentrantLock 必须手动释放锁，lock.unlock() 必须放到 finally 子句中，防止发生异常导致锁没释放。
			lock.unlock();
		}
	}

	/**
	 * 使用 lock 锁 与 run1 方法使用同一把锁，互斥
	 */
	public void run2() {
		lock.lock();
		try{
			while(true) {
				System.out.println(Thread.currentThread().getName() + "...正在执行run222");
			}
		}finally {
			lock.unlock();
		}
	}

	public void run3() {
		lock2.lock();
		try{
			while(true) {
				System.out.println(Thread.currentThread().getName() + "...正在执行run333");
			}
		}finally {
			lock2.unlock();
		}
	}

	public static void main(String[] args) {
		Demo1 demo1 = new Demo1();
		// 该线程执行 run1 方法与第二个线程互斥
		new Thread(demo1::run1).start();
		// 该线程执行 run2 方法，但锁被第一个线程持有，所有该线程一直等待第一个线程释放锁
		new Thread(demo1::run2).start();
		// 该线程执行 run3 方法，与上面两个方法使用不同的锁，所以该线程可以执行
		new Thread(demo1::run3).start();
	}
}
