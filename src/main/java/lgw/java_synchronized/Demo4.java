package lgw.java_synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo4
 * @Description 证明一个同步方法可以调用另一个同步方法（用的同一把锁）
 * @Author Numblgw
 * @Date 2019/6/2 11:44
 */
public class Demo4 {

	private synchronized void run1() {
		// 方法一死循环，不会释放锁
		while(true) {
			System.out.println(111);
			// 在同步方法run1()中调用同步方法run2()
			run2();
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private synchronized void run2() {
		System.out.println(222);
	}

	private static void main(String[] args) {
		Demo4 demo4 = new Demo4();

		new Thread(demo4::run1).start();
	}
}
