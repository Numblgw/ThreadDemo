package lgw.java_threadlocal;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo1
 * @Description ThreadLocal -- 线程局部变量
 *              使用线程池，线程结束后不会销毁，这样 ThreadLocal 会出现内存泄漏？   ######### 疑问标记 ##########
 * @Author Numblgw
 * @Date 2019/6/2 19:43
 */
public class Demo1 {
	/**
	 * ThreadLocal 是线程局部变量，每个线程都会创建一个单独的变量，不与其他线程共享，加 volatile 也没用
	 */
	private volatile ThreadLocal<Integer> count = new ThreadLocal<>();

	public static void main(String[] args) {
		Demo1 demo1 = new Demo1();
		// 线程一设置count的值
		new Thread(() -> {
			demo1.count.set(200);
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		// 线程二获取不到线程一设置的值
		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(demo1.count.get());
		}).start();
	}
}
