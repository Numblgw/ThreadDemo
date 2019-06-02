package lgw.java_volatile;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo2
 * @Description volatile 不能代替 synchronized
 * @Author Numblgw
 * @Date 2019/6/2 13:02
 */
public class Demo2 {

	/**
	 * 加 volatile，不加 synchronized 在并发修改时还是会出现问题，即不能替代。
	 */
	private volatile int count = 10;

	private /* synchronized */ void countDown() {
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		count--;
		System.out.println(Thread.currentThread().getName() + "..." + count);
	}

	public static void main(String[] args) {
		Demo2 demo2 = new Demo2();
		for(int i = 5 ; i > 0 ; i--) {
			new Thread(demo2::countDown).start();
		}
	}
}
