package lgw;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MyContainer
 * @Description ###面试题###
 * 				实现一个容器，提供两个方法：add size
 * 				线程一向容器中添加10个元素，线程二监控容器中的个数，当容器中
 * 				元素个数为5个的时候，线程二提示然后线程二结束。
 * 				使用了 wait notify 和 CountDownLatch 分别实现了一次
 * @Author Numblgw
 * @Date 2019/6/2 15:01
 */
public class MyContainer<T> {
	private List<T> list = new ArrayList<>();

	public int size() {
		return list.size();
	}

	public void add(T t) {
		list.add(t);
	}

	public static void main(String[] args) {
		MyContainer container = new MyContainer();

		/**
		 * ####################  wait  notify 实现
		 * 注意 wait 会释放锁， notify 不释放锁
		 * 添加元素的线程如果只 notify 而不 wait 的话，别的线程无法获得锁，即使被唤醒也执行不了
		 */

//		new Thread(() -> {
//			synchronized(container) {
//				try {
//					container.wait();
//				} catch(InterruptedException e) {
//					e.printStackTrace();
//				}
//				System.out.println("监控结束");
//				container.notifyAll();
//			}
//		}).start();
//		try {
//			TimeUnit.MILLISECONDS.sleep(500);
//		} catch(InterruptedException e) {
//			e.printStackTrace();
//		}
//		new Thread(() -> {
//			synchronized(container) {
//				for(int i = 1 ; i <= 10 ; i++) {
//					try {
//						TimeUnit.MILLISECONDS.sleep(500);
//					} catch(InterruptedException e) {
//						e.printStackTrace();
//					}
//					container.add(new Object());
//					System.out.println("添加了第 " + i + " 个元素");
//					if(i == 5) {
//						container.notifyAll();
//						try {
//							container.wait();
//						} catch(InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		}).start();

		// #####################   CountDownLatch 实现

		/**
		 * CountDownLatch 不涉及锁定，当设置的值为零时程序继续执行，
		 * 适用于需要线程通讯，不需要同步的时候。比用 synchronized + wait、notify 性能好一些
		 */
		CountDownLatch latch = new CountDownLatch(1);

		new Thread(() -> {
			try {
				latch.await();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("监控结束");
		}).start();

		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

		new Thread(() -> {
			for(int i = 1 ; i <= 10 ; i++) {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				container.add(new Object());
				System.out.println("添加了第 " + i + " 个元素");
				if(i == 5) {
					latch.countDown();
				}
			}
		}).start();
	}
}
