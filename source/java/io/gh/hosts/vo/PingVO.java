package io.gh.hosts.vo;

/**
 * @author OmO
 */
public class PingVO
{
  private String ip;
  /**
   * 记录ping总耗时
   */
  private Long ms;

  public PingVO(String ip, Long ms)
  {
    this.ip = ip;
    this.ms = ms;
  }

  public String getIp()
  {
    return ip;
  }

  public void setIp(String ip)
  {
    this.ip = ip;
  }

  public Long getMs()
  {
    return ms;
  }

  public void setMs(Long ms)
  {
    this.ms = ms;
  }

  @Override
  public String toString()
  {
    return "PingVO{" +
            "ip='" + ip + '\'' +
            ", ms=" + ms +
            '}';
  }
}
