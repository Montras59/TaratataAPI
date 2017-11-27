package fr.doctorwho.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum DateUnit
{
  SECONDE("s", 1), 
  MINUTE("min", 60), 
  HOUR("h", 3600), 
  DAY("d", 86400), 
  WEEK("w", 604800), 
  MONTH("m", 2419200), 
  YEAR("y", 31556952);

  String dateName;
  int millis;

  private DateUnit(String dateName, int millis) { 
	  this.dateName = dateName;
	  this.millis = millis; 
  }

  public static long getDate(String name, int time)
  {
    for (DateUnit date : values()) {
      if (name.equals(date.dateName)) {
        return date.millis * time * 1000L;
      }
    }
    return 0L;
  }

  public static String MillistoDate(long millis) {
    SimpleDateFormat date = new SimpleDateFormat("dd MM yyyy � HH:mm:ss");
    Date resultdate = new Date(millis);
    return date.format(resultdate);
  }
  
  public String getDateName()
  {
	  return dateName;
  }
}