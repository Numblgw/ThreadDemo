package lgw.java_concurrent_container;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo2
 * @Description 使用同步容器 Vector 模拟售票
 * @Author Numblgw
 * @Date 2019/6/3 12:44
 */
public class Demo2 {
	private static Vector<String> tickets = new Vector<>(1000);

	static {
		for(int i = 1000 ; i > 0 ; i--) {
			tickets.add(i + " 号票");
		}
	}

	/**
	 * 虽然使用了同步容器，但是仍然会出现问题。
	 * 虽然 tickets.size() 和 tickets.remove(0) 都是同步方法，但是在两个方法之间仍然有可能被别的线程抢占执行权。
	 * 也就是说，判断逻辑 和 业务逻辑分离了，应该把判断和删除变成一个原子操作（打开下面的注释加锁，加锁之后可以使用非同步容器）。
	 */
	public static void main(String[] args) {
		for(int i = 10 ; i > 0 ; i--) {
			new Thread(() -> {
				// synchronized(tickets) {
					while(tickets.size() > 0) {
						try {
							TimeUnit.MILLISECONDS.sleep(10);
						} catch(InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("卖掉了 " + tickets.remove(0));
					}
				// }
			}).start();
		}
	}
}
