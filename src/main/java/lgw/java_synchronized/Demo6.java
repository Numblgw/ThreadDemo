package lgw.java_synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo6
 * @Description 模拟死锁
 * @Author Numblgw
 * @Date 2019/6/2 11:58
 */
public class Demo6 {

	private Object o1 = new Object();

	private Object o2 = new Object();

	/**
	 * 方法一先获取 o1 的锁 然后睡眠，再获取 o2 的锁
	 */
	private void run1() {
		synchronized(o1) {
			System.out.println(Thread.currentThread().getName() + "线程持有了 o1 的锁，想要获取 o2 的锁");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			synchronized(o2) {
				System.out.println(Thread.currentThread().getName() + "线程持有了 o2 的锁");
			}
		}
	}

	/**
	 * 方法二先获取 o2 的锁 然后睡眠，再获取 o1 的锁
	 */
	private void run2() {
		synchronized(o2) {
			System.out.println(Thread.currentThread().getName() + "线程持有了 o2 的锁，想要获取 o1 的锁");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			synchronized(o1) {
				System.out.println(Thread.currentThread().getName() + "线程持有了 o1 的锁");
			}
		}
	}

	public static void main(String[] args) {
		Demo6 demo6 = new Demo6();
		new Thread(demo6::run1).start();
		new Thread(demo6::run2).start();
	}
}
