package lgw.java_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Demo2
 * @Description ReentrantLock 与 synchronized 区别
 * 				1 ) tryLock 尝试锁定,也可以指定尝试的时间
 * 				2 ) lockInterruptibly 可以相应中断。
 * 				3 ) new ReentrantLock(true)	指定为公平锁
 * 				RenntrantLock 比 synchronized 更加灵活
 * @Author Numblgw
 * @Date 2019/6/2 18:15
 */
public class Demo2 {
	private Lock lock = new ReentrantLock();

	/**
	 * 获取锁执行死循环，无法释放锁
	 */
	private void run1() {
		lock.lock();
		System.out.println("run111");
		try{
			while(true) {

			}
		}finally {
			lock.unlock();
		}
	}

	/**
	 * 执行 run2 方法的线程调用 tryLock 方法，尝试获取锁，可以通过是否判断是否获取到锁执行不同的代码。
	 */
	private void run2() {

		boolean locked = false;
		// 尝试获取锁，可以判断是否获取到
		// locked = lock.tryLock();

		// 尝试获取锁，等待5秒
		try {
			locked = lock.tryLock(5, TimeUnit.SECONDS);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

		if(locked) {
			try{
				System.out.println("run222..." + locked);
			}finally {
				lock.unlock();
			}
		}else {
			System.out.println("获取不到锁");
		}
	}

	/**
	 * run3 方法使用 lockInterruptibly 获取锁，可以被别的线程打断该线程
	 */
	private void run3() {
		try {
			try{
				// 可以相应其他线程调用的 interrupt 方法，打断该线程，普通的 lock.lock() 不打断
				lock.lockInterruptibly();
				while(true) {
					TimeUnit.MILLISECONDS.sleep(500);
					System.out.println("run333");
				}
			}finally {
				lock.unlock();
			}
		} catch(Exception e) {
			System.out.println("interrupt!");
		}
	}

	public static void main(String[] args) {
		Demo2 demo2 = new Demo2();

		// 线程1 死循环，持续占有锁
		// new Thread(demo2::run1).start();

		// 线程2 尝试获取线程1占有的锁
		// new Thread(demo2::run2).start();

		// 线程3 通过lockInterruptibly获取锁，可以被外部打断
		Thread t3 = new Thread(demo2::run3);
		t3.start();
		// 线程4 在未执行线程1的情况下执行，证明当线程3获取到锁的时候，仍然可以通过 t3.interrupt方法打断 t3 线程，并且会释放锁。
		new Thread(demo2::run1).start();
		try {
			TimeUnit.MILLISECONDS.sleep(800);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		t3.interrupt();
	}
}
