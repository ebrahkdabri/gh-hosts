package io.gh.hosts.worker;

import com.jd.platform.async.callback.IWorker;
import com.jd.platform.async.wrapper.WorkerWrapper;
import io.gh.hosts.util.Jsouper;
import io.gh.hosts.util.PingUtils;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author OmO
 */
public class IPWorker implements IWorker<String, String>
{
  private static final Logger log = Logger.getLogger(IPWorker.class.getName());
  private static final String REGEX_IP = "\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b";

  @Override
  public String action(String domain, Map<String, WorkerWrapper> allWrappers)
  {
    try
    {
      String html = Jsouper.getHtml("https://www.ipaddress.com/site/" + domain);

      // 使用正则提取所有IP
      Pattern pattern = Pattern.compile(REGEX_IP);
      Matcher matcher = pattern.matcher(Objects.requireNonNull(Jsoup.parse(html).getElementById("dns")).text());
      List<String> ips = new ArrayList<>();
      while (matcher.find())
      {
        ips.add(matcher.group());
      }
      return String.format("%s %s\n", PingUtils.getFastIp(ips), domain);
    } catch (IOException | InterruptedException e)
    {
      throw new RuntimeException(String.format("fetching %s data error via jsoup.", domain));
    }
  }

  @Override
  public String defaultValue()
  {
    return null;
  }
}
