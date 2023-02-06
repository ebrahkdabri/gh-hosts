package io.gh.hosts.util;

import io.gh.hosts.vo.PingVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author OmO
 */
public class PingUtils
{
  public static String getFastIp(List<String> ips) throws IOException, InterruptedException
  {
    // 如果只有一个IP，直接返回该IP
    if(ips.size() == 1)
    {
      return ips.get(0);
    }

    List<PingVO> list = new ArrayList<>();
    for (String ip : ips)
    {
      list.add(new PingVO(ip, pingAvg(ip, 10)));
    }
    return list.stream().sorted(Comparator.comparingLong(PingVO::getMs)).collect(Collectors.toList()).get(0).getIp();
  }

  public static long pingAvg(String ip, int count) throws IOException, InterruptedException
  {
    // 总耗时
    long total = 0;
    // 计算ping成功次数
    int pingCount = 0;
    for (int i = 0; i < count; i++)
    {
      long ms = ping(ip);
      // ping成功
      if (ms > 0)
      {
        total += ms;
        pingCount++;
      }
    }
    // 一次也没ping成功
    if(pingCount == 0) {
      return 0;
    }
    return total/pingCount;
  }

  public static long ping(String ip) throws IOException, InterruptedException
  {
    long start = System.nanoTime();
    boolean status = Runtime.getRuntime().exec("ping -c 1 " + ip).waitFor(1L, TimeUnit.SECONDS);
    long end = System.nanoTime();
    // 如果ping成功，返回耗时
    if (status)
    {
      return end - start;
    }
    return -1;
  }
}
