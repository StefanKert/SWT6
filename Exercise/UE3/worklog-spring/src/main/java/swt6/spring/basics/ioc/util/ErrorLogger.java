package swt6.spring.basics.ioc.util;

import org.springframework.stereotype.Component;

@Component
//@Log(LoggerType.ERROR)
public class ErrorLogger implements Logger {
 
  private String prefix = "ErrorLog";
  
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }
  
  @Override
  public void log(String msg) {
    System.err.format("%s: %s\n", prefix, msg);
  }
}
