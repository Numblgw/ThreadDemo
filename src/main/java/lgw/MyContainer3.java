package lgw;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName MyContainer3
 * @Description MyContainer2 的 lock/Condition 实现
 * @Author Numblgw
 * @Date 2019/6/2 19:25
 */
public class MyContainer3<T> {

	private Lock lock = new ReentrantLock();

	/**
	 * 生产者条件对象
	 */
	private Condition producer = lock.newCondition();

	/**
	 * 消费者条件对象
	 */
	private Condition consumer = lock.newCondition();

	private List<T> list = new LinkedList<>();

	private final int MAX_SIZE = 10;

	public void put(T t) {
		lock.lock();
		try{
			// 当容器满了的时候让生产者等待
			while(list.size() == MAX_SIZE) {
				producer.await();
			}
			list.add(t);
			System.out.println(Thread.currentThread().getName() + "生产了一个元素，当前有 " + list.size() + " 个");
			// 唤醒消费者
			consumer.signalAll();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	public void get() {
		lock.lock();
		try{
			// 当容器空了的时候让消费者等待
			while(list.size() == 0) {
				consumer.await();
			}
			list.remove(0);
			System.out.println(Thread.currentThread().getName() + "消费了一个元素，当前有 " + list.size() + " 个");
			producer.signalAll();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		MyContainer3 container = new MyContainer3();
		// 两个生产者线程
		for(int i = 2 ; i > 0 ; i--) {
			new Thread(() -> {
				while(true) {
					container.put(new Object());
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		// 十个消费者线程
		for(int i = 10 ; i > 0 ; i--) {
			new Thread(() -> {
				while(true) {
					container.get();
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}