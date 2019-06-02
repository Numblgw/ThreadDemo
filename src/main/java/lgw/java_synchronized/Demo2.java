package lgw.java_synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo2
 * @Description 证明 synchronized是给堆内存的对象加锁，而不是引用变量。
 * @Author Numblgw
 * @Date 2019/6/1 20:08
 */
public class Demo2 {

	private Object o = new Object();

	/**
	 * 当线程A获取了锁之后一直在进行死循环，不会释放锁，所以其他线程永远不会执行。
	 */
	private void run1() {
		synchronized(o) {
			while(true) {
				System.out.println(Thread.currentThread().getName() + "正在运行。。。");
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void main(String[] args) {
		Demo2 demo2 = new Demo2();

		new Thread(demo2::run1).start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		// 改变了引用o指向的对象，使得下面的线程在执行时只需要获取新的对象的锁
		demo2.o = new Object();
		// 如果下面的线程可以执行，就说明 synchronized 是给堆内存中的对象加锁
		new Thread(demo2::run1).start();
	}
}
