package io.gh.hosts.config;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author OmO
 * <p>
 * 核心线程数在2 - 4个之间，由CPU核心数 - 1决定；
 * 线程池的最大线程数为CPU核心数的2倍 + 1；
 * 核心线程和非核心线程数均有超时机制，时间为30s；
 * 任务队列的容量为50。
 * </p>
 */
public class TheadPoolExecutorConfig
{

  /**
   * 获取系统处理器个数，作为线程池数量
   */
  private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

  /**
   * We want at least 2 threads and at most 4 threads in the core pool,
   * preferring to have 1 less than the CPU count to avoid saturating
   * the CPU with background work
   */
  private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
  private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
  private static final int KEEP_ALIVE_SECONDS = 30;

  private static final ThreadFactory THREAD_FACTORY = new ThreadFactory()
  {
    private final AtomicInteger mCount = new AtomicInteger(1);

    public Thread newThread(Runnable r)
    {
      return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
    }
  };

  private static final BlockingQueue<Runnable> BLOCKING_QUEUE =
          new LinkedBlockingQueue<Runnable>(128);

  /**
   * An {@link Executor} that can be used to execute tasks in parallel.
   */
  public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;

  static
  {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
            BLOCKING_QUEUE, THREAD_FACTORY);
    threadPoolExecutor.allowCoreThreadTimeOut(true);
    THREAD_POOL_EXECUTOR = threadPoolExecutor;
  }
}
