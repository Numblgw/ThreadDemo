package lgw.java_concurrent_container;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo1
 * @Description 使用非同步容器模拟售票，演示在多线程环境下存在的问题
 * @Author Numblgw
 * @Date 2019/6/3 12:29
 */
public class Demo1 {
	private static List<String> tickets = new LinkedList<>();

	static {
		for(int i = 1000 ; i > 0 ; i--) {
			tickets.add(i + " 号票");
		}
	}

	public static void main(String[] args) {
		for(int i = 10 ; i > 0 ; i--) {
			new Thread(() -> {
				// 为做同步处理，当多个线程同时运行时，会出现并发问题。例如：卖掉同一张票，卖掉负数标号的票等。
				while(tickets.size() > 0) {
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("卖掉了 " + tickets.remove(0));
				}
			}).start();
		}
	}
}
