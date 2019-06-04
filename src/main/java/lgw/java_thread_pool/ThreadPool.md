**Executor** 接口（执行器）

**ExecutorService**（执行器服务）继承自Executor可以向ExecutorService提交一个任务（实现了Runnable或者Callable的任务），ExecutorService会
执行任务。
    
**Callable** 接口，与 Runnable 差不多，只不过 Callable 可以抛出异常，和返回结果，Runnable 不行。

**Executors** 类，操作 Executor、ExecutorService等类的工具类。

**ThreadPool** 线程池，实现了 ExecutorService 接口，Demo1演示ThreadPool的基本使用。

**FutureTask** 一个可以有返回值的任务，可以通过泛型指定返回值的类型，传入 Callable 的实现。Demo2演示基本用法。

**Future** 可以接收 Callable 执行的返回值。Demo2演示基本用法。


**线程池**：    
    线程池中的线程执行完任务时不会消失，再有新任务的时候可以再执行新任务。启动新线程会消耗系统资源，所以可以提前启动一
  些线程放到线程池中管理。 


**六种线程池**：
  1) FixedThreadPool 线程数固定的线程池，线程池中线程的数量不会动态的改变，Demo1演示。
  
  2) CacheThreadPool 缓存的线程池，初始没有线程，当分配任务时，如果没有空闲线程执行新分配的任务就会启动新的线程，被启动的线程
  空闲 60s 会自动销毁，也可以指定销毁的时间，Demo3演示。
  
  3) SingleThreadPool 只有一个线程的线程池，无论有几个任务排队，都只会有一个线程，当需要保证任务顺序执行的时候使用，Demo4演示。
  
  4) ScheduledThreadPool 定时器线程池，执行定时任务，其中的线程可以复用。可以调用方法以固定的频率执行任务，Demo5演示。
  
  5) WorkStealingThreadPool 工作窃取线程池。 线程池中每个线程都维护自己的任务队列，当有一个线程空闲的时候会去偷取别的线程的任务队
  列的任务。该线程池里的线程都是DaemonThread精灵线程（守护线程、后台线程），底层用 ForkJoinPool 实现。demo6演示。
  
  6) ForkJoinPool 可以将任务递归的切分成多个小任务，然后合并计算结果，切分的规则可以指定。类比归并排序。
  
  
  