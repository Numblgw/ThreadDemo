package lgw.java_volatile;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo1
 * @Description 使用程序证明volatile的作用
 * @Author Numblgw
 * @Date 2019/6/2 12:37
 */
public class Demo1 {
	/**
	 * 如果不加volatile，一个线程改变了这个变量的值时不会马上通知其他线程的
	 * 其他线程使用的值仍然是线程工作内存中存储的值，它可能过一段时间自动去堆内存更新值，也可能不更新。
	 * 加上volatile关键字之后，当某个线程修改了这个值，就会通知其他线程刷新缓存
	 */
	private /* volatile */ boolean flag = true;

	public void run() {
		System.out.println("开始了。。。。。。。");
		while(flag) {

		}
		System.out.println("停下来了。。。。。。");
	}

	public void setFlag() {
		this.flag = false;
	}

	public static void main(String[] args) {
		Demo1 demo1 = new Demo1();
		new Thread(demo1::run).start();

		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		demo1.setFlag();
	}
}
