package lgw;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MyContainer1
 * @Description 使用 wait/notify 模拟生产者消费者模式
 * 				有 put get getCount方法，线程安全
 * @Author Numblgw
 * @Date 2019/6/2 19:00
 */
public class MyContainer1<T> {
	private List<T> list = new LinkedList<>();

	private final int MAX_SIZE = 10;

	public int getCount() {
		return list.size();
	}

	public synchronized void put(T t) {
		// 这里必须用 while 因为当该线程 被唤醒时，容器可能还是满的，必须再次判断。
		while(list.size() == MAX_SIZE) {
			try {
				// wait 一般与 while 连用
				wait();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		list.add(t);
		System.out.println(Thread.currentThread().getName() + "生产一个元素，当前有 " + list.size() + " 个");
		// 这里必须用 notifyAll 不能用 notify 生产者唤醒的线程可能还是生产者，这样会让程序死掉。
		notifyAll();
	}

	public synchronized void get() {
		while(list.size() == 0) {
			try {
				wait();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		list.remove(0);
		System.out.println(Thread.currentThread().getName() + "消费一个元素，当前有 " + list.size() + " 个");
		notifyAll();
	}

	public static void main(String[] args) {
		MyContainer1<Object> container = new MyContainer1<>();
		// 两个生产者线程
		for(int i = 2 ; i > 0 ; i--) {
			new Thread(() -> {
				while(true) {
					container.put(new Object());
				}
			}).start();
		}
		// 十个消费者线程
		for(int i = 10 ; i > 0 ; i--) {
			new Thread(() -> {
				while(true) {
					container.get();
				}
			}).start();
		}
	}
}
