package lgw.java_synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo5
 * @Description 证明线程抛出异常不进行 catch 会释放锁
 * @Author Numblgw
 * @Date 2019/6/2 11:50
 */
public class Demo5 {
	/**
	 * 方法一只抛出异常，不 catch 异常
	 */
	private synchronized void run() {
		for(int i = 10 ; i > 0 ; i--) {
			if(i == 5) {
				// 抛出异常前即使线程1 sleep 线程2也没有执行的机会，因为获取不到锁
				throw new RuntimeException();
				// 抛出异常后，线程1释放锁，线程2获取到锁，执行该方法
			}
			System.out.println(Thread.currentThread().getName() + "......" + i);
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 异常被 catch 之后不会释放锁
	 */
	private synchronized void run2() {
		for(int i = 10 ; i > 0 ; i--) {
			if(i == 5) {
				try {
					throw new RuntimeException();
				}catch(Exception e) {
					System.out.println("异常被 catch 了 。。。。。。。");
				}
			}
			System.out.println(Thread.currentThread().getName() + "......" + i);
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Demo5 demo5 = new Demo5();
		for(int i = 2 ; i > 0 ; i--) {
			// new Thread(demo5::run).start();
			new Thread(demo5::run2).start();
		}
	}
}
