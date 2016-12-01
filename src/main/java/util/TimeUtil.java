package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Hub on 15/10/8.
 */
public class TimeUtil {

  static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  static {
    TimeZone zone = TimeZone.getTimeZone("GMT+8:00");
    sdf.setTimeZone(zone);
  }

  public static Integer current() {
    long current = System.currentTimeMillis();
    return (int) (current / 1000);
  }

  public static String getCurrentTime() {
    return sdf.format(new Date());
  }

  /**
   * 设置时间的增减
   *
   * @param field  the calendar field, Calender.MONTH {@link Calendar}
   * @param amount the amount of date or time to be added to the field
   */
  public static String getAdjustTime(int field, int amount) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(new Date());
    calendar.add(field, amount);
    return sdf.format(calendar.getTime());
  }



  /**
   * 设置时间的增减,返回时间戳
   *
   * @param field  the calendar field, Calender.MONTH {@link Calendar}
   * @param amount the amount of date or time to be added to the field
   */
  public static Integer getAdjustTimeStamp(int field, int amount) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(new Date());
    calendar.add(field, amount);
    return (int)(calendar.getTimeInMillis()/1000);
  }


  public static void main(String[] args) {
    System.out.println(current());
  }

}
