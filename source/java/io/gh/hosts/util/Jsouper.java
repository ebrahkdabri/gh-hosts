package io.gh.hosts.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * @author OmO
 */
public class Jsouper
{
  public static String getHtml(String url) throws IOException
  {
    Connection connect = Jsoup.connect(url);
    return connect.execute().body();
  }
}
