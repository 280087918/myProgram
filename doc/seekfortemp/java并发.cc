1.Thread������ֻ��Ҫһ��runnable���󣬵���Thread�����star()��������������Runnable��run����������ִ�е���Thread��Runnable����

2.Executor
  2.1 CachedThreadPool
    java.util.concurrent���еĵ�Executor������������Thread����
    ��������������ǿ��Բ�����ʽ�Ĵ���Thread����
    �����뿴���´���:(myProgram/j2se/jdk/src/test/java/com/john/test/util/concurrent/ExecutorTest.testCachedThreadPool)
    ExecutorServie����Executors�ľ�̬���������̳߳أ��̳߳�Ϊÿһ�����񴴽�һ���̡߳�
    ��shutdown()��������ֹ�µ������ύ�����Executor,���Թ���ע�͵����д���Ļ����̻߳�����˳��ִ�е�
  
  2.2 FixThreadPool
    FixThreadPool���������̵߳�����������ϵͳ�Ͳ���Ϊÿ�����񶼴����̶߳����ϵͳ�ϵĶ��⿪����
    ������ϵͳ����Ҫ�̵߳�ʱ��ֱ�Ӵ��̳߳������ȡ��
    �����뿴���´���:(myProgram/j2se/jdk/src/test/java/com/john/test/util/concurrent/ExecutorTest.testFixThreadPool)
    �Ӳ��Խ�����Կ�������һ���߳�ִ������֮�󣬵�����̲߳ſ����õ���Ӧ���߳�ȥ����ҵ��
    ������һ���ص㣬���һ���̵߳��߳����Ƹ�����������߳�������һ���ģ����Դ���������ж��̳߳�ȷʵ�������̵߳Ĵ�С

  2.3 SingleThreadExecutor
    SingleThreadExcecutor�൱���̳߳�����Ϊ1��FixThreadPool������ж���߳���Ҫִ�еĻ����߳��൱���Ŷ��ڵȺ�ִ�С�

3.Callable
  Runnable�Ƕ������񣬲����᷵���κ�ֵ���������ϣ���������ʱ�ܷ���һ������ֵ����ô��Ӧ����Callable
  �����뿴���´���:
	(myProgram/j2se/jdk/src/test/java/com/john/thread/callable/TaskWithResult)
	(myProgram/j2se/jdk/src/test/java/com/john/test/thread/callable/TaskWithResultTest)
  ��TaskWithResult������Կ����߳�ִ�е��߼���call�������棬���Ҳ��ʵ����Callable�Ľӿڷ���������ֵ��ʵ�ֽӿڵ�ʱ����ķ���һ�¡�
  ��TaskWirhResultTest����Կ�����ExecutorService�����Callable��ʱ������sumit�ķ�ʽ����������Runnable����ֱ����execut(new Runnable())���ַ�ʽ

4.Thread.yield()
  �л�CPUִ��ʱ��Ƭ�Σ���˼�Ǳ��̵߳���Ҫ�߼��Ѿ������ˣ����Խ�cpu��һ���߳��л�����һ���̡߳�

5.Thread.currentThread().setPriority(int)
  �߳̿����������ȼ������ǲ�����˵�����ȼ��͵ľ���Զ��ִ�У�ֻ���̵߳�ִ��Ƶ�ʽϵ͡�
  �����õ����ּ���:Thread.MIN_PRIORITY, Thread.NORM_PRIORITY, Thread.MAX_PRIORITY

6.daemon
  �ػ����̣�Ҳ�к�̨�̣߳�
  �÷�:
  Thread thread = new Thread(new Runnable(����););
  thread.setDaemon(true);//���ػ�����
  ���Ҫ��thread����ǰ����

7.join()
  ���A�߳��������B�߳�ʵ����join()��������ôA�߳̽���ȴ�B�߳�ִ����֮��Żָ���
  һ������:A�̱߳����B��C�̷ֱ߳�ִ����֮���ټ���������ִ�С�
     ����ܼ�(ABC���ֱ�̳�Thread)����B��C�߳�ʵ���ŵ�Aʵ���У���A��run��������ֱ��B��C��ʵ������join()��B��C���߳���main���������ж�����ν������B��C�ǲ������е�.
  �����뿴���´���:(myProgram/j2se/jdk/src/test/java/com/john/test/thread/join/order/OrderObjTest)
  

8.�쳣�Ĳ���
  һ����˵���̵߳��쳣�ǲ��ܱ���׽���ġ�
  �ñ�˵����Thread��run���������׳�һ���쳣throw new RuntimeException();�����߳�start��ʱ��try-catch���޷���׽����,����˵���ᵽcatch����ȥ
  �����뿴���´���:(myProgram/j2se/jdk/src/test/java/com/john/test/thread/exception/ExceptionHandlerTest)
  ���ǿ��Կ���Executors.newCachedThreadPool��ʱ�򣬿��������̹߳���ThreadFactory��ThreadFactory��һ���ӿڡ�ʵ������������д�����߳����ɷ�������
    ���߳��������쳣������UncaughtExceptionHandler��ͬ����UncaughtExceptionHandlerҲ��һ���ӿڡ���һ����ʵ�����������Ǿ�ӵ��һ���쳣������
  ��һ�ַ�ʽ������ȫ������:Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    ����ϵͳ�����е��̶߳���������쳣��ȥ�����쳣��
  ���ַ�ʽҲ��try-catch��ס�ģ�ֻ�������ǿ������Զ�����쳣����������ȥ������Щ���쳣��

9.sychronized
  ���һ�����������һ�����Ϊsynchronized�������棬��ô����������������������֮����ܽ���
  ÿһ���ض��Ķ����϶�����һ�ѵ�һ����(Ҳ��Ϊ������),����ڶ����ϵ����������synchronized��������ô�������󶼻ᱻ��������������ϵ�����synchronized����ֻ�ܵ��������ִ����֮���ٽ��С�
  a)synchronized(this)��ô�����е�����synchronized�������ܱ������߳�ʹ�á�
  b)synchronized methodA �� synchronized methodB �ụ�⣬Ҳ����˵ͬһ������ͬһʱ��ֻ����һ��method������
  c)synchronized methodA ��������synchronized�����������⡣Ҳ����˵������synchronized����������ʹ��
  d)���ж���һ��Object��synchronized���Object����ô����С���ķ�Χ�����������������������synchronized(this)���⡣��������Ȼ�������Ͷ�������������Ӱ���ڲ���Object����
  e)һ���߳̿��Զ�λ��ĳ������

10.Lock�ӿ�
  java.util.concurrent.Lock
  ��������һ��ʵ�ַ�ʽLock lock = ne ReentrantLock();
  Lock��Ҫ��ʽ��lock,����unlock��һ��unlock()�������finnally����ִ�С�
  synchronized��Ҫд�Ĵ�����٣�����lock��ʱ���ͦ����Ϊsynchronizedһ����ȡ�������ͻ�һֱ�ȴ�������Lock������lock.tryLock(2, TimeUnit.SECONDS)ȥ��������һ��ʱ�����ȷʵ��ȡ�������Ļ�����ô���뿪���ؼ������ڳ��Ի�ȡ���ȴ���ʱ�����ԡ�

11.volatile
  ���ܱ�֤�����̶߳�ȡ��volatile���εı��������µ�ֵ�����ǲ����ܽ���������⣬��Ϊ�������ԭ�Ӳ������п��������߳�ͬʱ��ֵ�ӹ����ڴ�̻��������ڴ��ʱ������ͻ��

12.util����ԭ���๤��
  AtomicInteger,AtomicLong,AtomicReference
  �����뿴���´���:(myProgram/j2se/jdk/src/test/java/com/john/test/util/concurrent/atomic/AtomicTest)
  ��ʵ������֪�����Atomic��ԭ�ӹ�����ȷʵ���������ڸ߲�������

13.ThreadLocal
  ThreadLocal����Ϊ������Դ��������������ͬһ��������ʹ��ThreadLocalʱ�����һ�ݸ�������֤�߳�֮����Դ���Ὰ����ÿ���̲߳����Ķ����Լ����صĶ���

14.�̵߳�����״̬:
  a)�½�(new):���̱߳�����ʱ���ͻ���ݳ�������״̬�������ʸ��ȡCPUʱ��.
  b)����(Runnable):����״̬�£�ֻҪ��������ʱ�������̣߳���ô�߳̾Ϳ�������
  c)����(Blocked):�߳̿������У�����ĳ��������ֹ�������ˡ���ʱ��������κ�CPUʱ��
  d)����(Dead):���̵߳�����״̬���������ٱ�����

15.interrupt(�ж�)
  Thread.interrupt()�����жϱ�����������,���ǲ�����break,�������ѭ����û�취��������ֻ�Ǹ���Ŀǰ�̵߳�����
  ����߳�������ҪThread.sleep(xxx)ȥ�ȴ�ĳЩ��Դʱ����ʹ��interupt����,���ǻ��׳�InterrruptedException,����java��������Ǹ����㣬���Բ�������쳣�������ͷ������Դ��
  һ�������ֱ��ʹ��ExecutorServiceȥ�жϣ��������´���:
	ExecutorService exec = Executors.newCachedThreadPool();
	Future<?> future = exec.submit(new SleepBlocked());
	future.cancel(true);
	//exec.shutdownNow();
  �ر�˵��:˯�߾������������ǿ��Ա��жϵģ�����IO��synchronized�ǲ��ܱ��жϵģ���һ���ס���У����ϵ������Ѿ���֤��p696

16.�������
  ����Ϊsynchronized�����ǲ����Ա��жϵģ�����concurrent���ṩ��lock���Խ���������
  Lock lock = new ReentrantLock();
  ��lock��ס��Ҫ�Ĵ����(lock.lock())��������������ϣ���п��ܻ�ȡ�����������lock.lockInterruptibly.˵�����lock�ǿ��Ա��жϵ�
  ���ϲ�ֱ��ʹ��thread.interrupt����

17.wait,notify,notifyAll
  �⼸������û�����߳����棬��ֱ���ڶ�������ġ���Ϊ������һ�����ڶ����С�
  wait���õ�ǰ�̳߳��ڵȴ�״̬����Ҳ������Thread.sleep(xxx)һ����˯��һ��ʱ�䡣��sleep��ͬ���ǣ�wait���ͷŵ�ǰ������˼�ҵ�ǰ�����������ˣ��������ó�ȥ�ˡ�
  �����ڱ������У��������������֮��notifyAll��ʱ��Ϳ��Ի���wait�̡߳�

18.Condition
  Java��concurrent��������һ����ʽ�Ĺ��߿��Դ���notify
  Condition�Ĳ���:
	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();
  �̵߳ȴ�:condition.await();
  ����wait�е��߳�: condition.signalAll();

19.�̶߳���
  ����ʹ�ö���ʵ����������������߼�
  �ӿ���:BlockingQueue<MyRunnable> queue;
  ʵ������:LinkedBlockingQueue<MyRunnable>()���ޱ߽���С�,ArrayBlockingQueue<MyRunnable>(8)���б߽���У��������ƴ�С��,SynchronousQueue<MyRunnable>()����Сֻ��1��
  �����õ�ʱ��ֱ��new�Ϳ�����
  ����runnableԪ��:queue.put(MyRunnable);//������
  ����runnableԪ��:queue.take();//������

20.����
  �����ĸ�����:һ�������ڵȴ���һ������һֱ������ȥ������������������һ��������ɳ����޷���������ִ�С�
  �ĸ�����ͬʱ����ʱ���ͻ�����������
  a)��������������ʹ�õ���Դ������һ���ǲ��ܹ���ġ�
  b)���������Դ�����ҵȴ�����������е���Դ��
  c)��Դ���ܱ�������ռ
  e)������ѭ���ȴ�

21.java.util.concurrent.CountDownLatch
  ����˽��¾Ϳ����ˣ�
  countDownLatch�������һ����������һ��ʼ��ʱ���ʼ��һ����������֡����Խ�����������һ���߳�����ȥ����latch.countDown();
  ��ô֮ǰ���߳�(�����Ǻܶ��),�ھ���ҵ���߼�������ʵ��latch.await();
  �������������ȫcountDown��֮��latch.await()������߼��Ż�������С�