package lgw.java_concurrent_container;

import sun.java2d.SurfaceDataProxy;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @ClassName Demo4
 * @Description 不同的 map 并发容器的效率比较
 * @Author Numblgw
 * @Date 2019/6/3 14:45
 */
public class Demo4 {

	/**
	 * 并发环境中，性能最好的是 ConcurrentHashMap,如果要求数据有序，就使用ConcurrentSkipListMap
	 */
	private static Map<Integer, Integer> map =
			// 支持并发的 Map    260 ~ 300 ms
			new ConcurrentHashMap();

			// 支持并发并且有序的 Map  330 ~ 370 ms
			// new ConcurrentSkipListMap<>();

			// HashTable 380 ~ 420 ms
			// new Hashtable<>();

			// 使用 Collections.synchronizedXXX 返回一个加锁的容器  380 ~ 400+ ms
			// Collections.synchronizedMap(new HashMap<>());

	public static void main(String[] args) {
		Thread[] threads = new Thread[100];
		CountDownLatch latch = new CountDownLatch(100);
		Random random = new Random();
		for(int i = 0 ; i < threads.length ; i++) {
			threads[i] = new Thread(() -> {
				for(int j = 10000 ; j > 0 ; j--) {
					map.put(random.nextInt(100000), random.nextInt(10000));
				}
				latch.countDown();
			});
		}
		long start = System.currentTimeMillis();
		Arrays.asList(threads).stream().forEach(Thread::start);
		try {
			latch.await();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
