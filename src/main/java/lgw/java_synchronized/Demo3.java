package lgw.java_synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo3
 * @Description 证明线程A获取到某对象的所之后，线程B可以继续访问该对象的非加锁方法。
 * @Author Numblgw
 * @Date 2019/6/2 11:25
 */
public class Demo3 {

	/**
	 * 方法一对this加锁,并且死循环导致不会释放锁
	 */
	private synchronized void run1() {
		while(true) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(11111);
		}
	}

	/**
	 * 方法二不加锁
	 */
	private void run2() {
		while(true) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(22222);
		}
	}

	private static void main(String[] args) {
		Demo3 demo3 = new Demo3();
		// 先启动一个线程执行方法一，给对象加锁
		new Thread(demo3::run1).start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		// 如果线程二成功执行了方法二那么就证明，对象被加锁时其他线程仍然可以调用非同步方法。
		new Thread(demo3::run2).start();
	}
}
