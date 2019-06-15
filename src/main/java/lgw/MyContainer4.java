package lgw;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MyContainer4
 * @Description BlockingQueue实现生产者消费者模式
 * @Author Numblgw
 * @Date 2019/6/7 20:07
 */
public class MyContainer4 {
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
						queue.put(Thread.currentThread().getName() + " 的元素");
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, "生产者 " + i).start();
		}
	}
}
