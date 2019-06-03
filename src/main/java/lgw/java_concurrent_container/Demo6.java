package lgw.java_concurrent_container;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo6
 * @Description 使用 SynchronousQueue 模拟生产者消费者模式，
 * 				生产者生产的元素直接交给消费者，不加入队列中。
 * @Author Numblgw
 * @Date 2019/6/3 17:00
 */
public class Demo6 {
	public static void main(String[] args) {
		SynchronousQueue<String> queue = new SynchronousQueue<>();

		for(int i = 5 ; i > 0 ; i--) {
			new Thread(() -> {
				while(true) {
					String s = null;

					try {
						s = queue.take();
					} catch(InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println(Thread.currentThread().getName() + " 拿到了" +s);
					System.out.println("当前队列中元素个数为 " + queue.size());

					try {
						TimeUnit.SECONDS.sleep(1);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, "消费者 " + i).start();
		}

		for(int i = 2 ; i > 0 ; i--) {
			new Thread(() -> {
				while(true) {
					try {
						queue.put(Thread.currentThread().getName() + " 生产的元素");
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, "生产者 " + i).start();
		}
	}
}
