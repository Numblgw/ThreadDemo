package lgw.java_concurrent_container;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo3
 * @Description 使用 并发容器 模拟抢票
 * @Author Numblgw
 * @Date 2019/6/3 13:00
 */
public class Demo3 {

	/**
	 * 支持并发的基于链表实现的 fifo队列
	 */
	private static Queue<String> tickets = new ConcurrentLinkedQueue<>();

	static {
		for(int i = 1000 ; i > 0 ; i--) {
			tickets.add(i + " 号票");
		}
	}

	public static void main(String[] args) {
		for(int i = 10 ; i > 0 ; i--) {
			new Thread(() -> {
				// 初步判断剩余票数，后面的操作并不依赖于这个判断
				while(tickets.size() > 0) {
					try {
						TimeUnit.MILLISECONDS.sleep(10);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}

					// 进行买票操作，然后根据操作的结果进行不同的处理，以操作的结果为主，不依赖于上面的判断。该容器中 poll 操作是原子性的。
					String ticket = tickets.poll();
					if(ticket != null) {
						System.out.println(Thread.currentThread().getName() + " 号售票员，卖掉了" + ticket);
					}else {
						System.out.println(Thread.currentThread().getName() + " 票莫得了");
						break;
					}
				}
			}).start();
		}
	}
}
