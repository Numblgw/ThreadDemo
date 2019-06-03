1) map/set
    非同步map HashMap TreeMap LinkedHashMap ....
    同步map 并发量低  HashTable（不建议） Collections.synchronizedMap(new HashMap<>())
    同步map 并发量高  ConcurrentHashMap  ConcurrentSkipListMap（有序的）
    
2) list
    非同步list ArrayList（数组实现） LinkList（链表实现）
    同步 并发量低的list vector（不建议）
    写时复制 CopyOnWriteArrayList   读取效率极高，修改新增效率极低。对于修改操作都是需要copy原内容，
        在copy上修改然后让引用指向copy。适用于写入次数少，读取次数多的场景。
        
3) queue
    add 方法 满了就会报异常
    offer 方法  满了不会报异常，但也不往里加
    
    ConcurrentQueue 内部加锁的queue
        ConcurrentLinkedQueue 无界队列，基于链表实现的单向队列
        ConcurrentLinkedDeque 双端队列，除了两端都可以加或者出，其他和ConcurrentLinkedQueue差不多。
    
    BlockingQueue  阻塞式queue
        添加元素调用 put 方法 如果队列已满就会使该线程阻塞等待，等待别的线程拿出元素在往里加
        取元素调用 take 方法 如果队列为空，也会阻塞
        LinkedBQ（无界）    ArrayBQ（有界）
      
    DelayQueue  用于执行定时任务
        该队列中的元素被加入之后需要等待一段时间才可以取出，可以指定等待的时间。
        默认是有序的，等待时间最长的会被优先取出。
        被加入的元素必须实现 java.util.concurrent.Delayed 接口 否则无法加入。   
        
    TransferQueue   
        transfer 方法 会检查是否有需要获得元素的线程，如果有则直接将元素传递过去，不向队列中加入。如果没有就会阻塞。
        也可以调用add方法直接向队列中加元素。
        适用于高并发场景，生产者直接向消费者传递资源，不经过队列，效率更高。 
        
    SynchronusQueue
        容量为 0 的 TransferQueue，不能调用 add 方法向队列中加元素，必须调用 put 方法。
        put 方法就相当于 TransferQueue 的 transfer 方法，会阻塞
  
     