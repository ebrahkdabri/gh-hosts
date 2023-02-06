package io.gh.hosts;

import com.jd.platform.async.executor.Async;
import com.jd.platform.async.wrapper.WorkerWrapper;
import io.gh.hosts.config.TheadPoolExecutorConfig;
import io.gh.hosts.util.FileUtils;
import io.gh.hosts.val.ConstVal;
import io.gh.hosts.worker.IPWorker;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author OmO
 */
public class Main
{
  public static void main(String[] args) throws ExecutionException, InterruptedException
  {
    StringBuilder sb = new StringBuilder();
    sb.append("#\n");
    sb.append("# gh\n");
    sb.append(String.format("# refresh in %s\n", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    sb.append("#\n");

    List<WorkerWrapper<String, String>> workerWrapperList = new ArrayList<>();

    Arrays.stream(ConstVal.DOMAINS_GH).forEach(domain -> workerWrapperList.add(new WorkerWrapper.Builder<String, String>().worker(new IPWorker()).param(domain).build()));
    Async.beginWork(1000 * 60 * 15, TheadPoolExecutorConfig.THREAD_POOL_EXECUTOR, workerWrapperList.toArray(new WorkerWrapper[]{}));
    workerWrapperList.stream().filter(workerWrapper -> workerWrapper.getWorkResult().getResult() != null).forEach(workerWrapper -> sb.append(workerWrapper.getWorkResult().getResult()));
    Async.shutDown();

    // 写hosts文件
    FileUtils.write(new File(System.getProperty("user.dir") + "/hosts"), sb.toString());
  }
}
