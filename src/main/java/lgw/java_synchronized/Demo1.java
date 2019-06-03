package lgw.java_synchronized;

/**
 * @ClassName Demo1
 * @Description 演示多线程并发操作同一个变量存在的问题，synchronized的基本使用。
 * @Author Numblgw
 * @Date 2019/6/1 19:08
 */
public class Demo1{

	private int count = 10;

	private Object o = new Object();

	/**
	 * 没有加锁，不能实现同步。
	 * 运行会出现 “ 不同的线程打印的值相同 ” 的问题
	 */
	private void run1() {
		count--;
		// 当线程 1 运行到这里，把变量修改了之后还没有打印该变量时，线程 2 获取了执行权并且再一次对变量减一
		// 导致线程 1 在次访问 count时得到的是线程 2 修改之后的值
		System.out.println(Thread.currentThread().getName() + "count:" + count);
	}

	/**
	 * 使用 synchronized 对 this对象 加锁
	 * 可以使用代码块，也可以写在方法上，写在方法上是给 this 对象加锁
	 *
	 * 所有调用这个方法的线程都要先获取 this 对象的锁，也就是 下面的 demo1 引用
	 * 的对象。如果某一个线程已经获取了这个对象的锁，那么其他线程就只能等该线程释
	 * 放锁之后再获取锁。在方法抛出异常或者方法结束是会自动释放锁
	 */
	private /*synchronized*/ void run2() {
		synchronized(this) {
			count--;
			System.out.println(Thread.currentThread().getName() + "count:" + count);
		}
	}

	/**
	 * 给对象 o 加锁，一个 Demo1 对象中只会有一个 o 对象，所以给 o 加锁也可以保证
	 * 多线程访问Demo1对象的时候只有一个线程持有o的锁
	 */
	private void run3() {
		synchronized(o) {
			count--;
			System.out.println(Thread.currentThread().getName() + "count:" + count);
		}
	}

	/**
	 * 给静态方法加锁，锁住的是   Demo1.class 对象相当于 run5() 方法
	 */
	private static synchronized void run4() {

	}

	private static void run5() {
		synchronized(Demo1.class) {

		}
	}

	public static void main(String[] args) {
		Demo1 demo1 = new Demo1();
		for(int i = 0 ; i < 5 ; i++) {
			new Thread(demo1::run1).start();
			// new Thread(demo1::run2).start();
			// new Thread(demo1::run3).start();
		}
	}
}
