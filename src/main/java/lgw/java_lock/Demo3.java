package lgw.java_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName Demo3
 *
 * @Description	读写锁，读锁排斥写锁，可以被多个读操作共用。写锁排斥其他所有的写或者读操作。
 * @Author Numblgw
 * @Date 2019/6/5 10:43
 */
public class Demo3 {

	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private Lock readLock = readWriteLock.readLock();

	private Lock writeLock = readWriteLock.writeLock();

	public String get() {
		readLock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + " 获得了读锁进入死循环,其他线程仍可以继续获取读锁");
			while(true) {

			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			readLock.unlock();
		}
		return "lgw";
	}

	public void set() {
		writeLock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + " 获得了写锁进入死循环，其他线程不可以继续获取写锁和读锁");
			while(true) {

			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			writeLock.unlock();
		}
	}

	public static void main(String[] args) {
		Demo3 demo3 = new Demo3();


		for(int i = 3 ; i > 0 ; i--) {
			new Thread(demo3::set).start();
		}

		for(int i = 3 ; i > 0 ; i--) {
			new Thread(demo3::get).start();
		}
	}
}
