package io.gh.hosts.util;

import java.io.*;
import java.util.logging.Logger;

/**
 * @author OmO
 */
public class FileUtils
{
  private static final Logger log = Logger.getLogger(FileUtils.class.getName());

  public static void write(File file, String content)
  {
    FileOutputStream fos = null;
    try
    {

      if (!file.exists())
      {
        boolean newFile = file.createNewFile();
        if(newFile) {
          log.info(String.format("the %s file successfully created.", file.getName()));
        }
      }
      fos = new FileOutputStream(file);
      fos.write(content.getBytes());
    } catch (IOException e)
    {
      e.printStackTrace();
    } finally
    {
      try
      {
        if (null != fos)
        {
          fos.close();
        }
      } catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
}
