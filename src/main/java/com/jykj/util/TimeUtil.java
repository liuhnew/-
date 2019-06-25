/*
 *
 */
package com.jykj.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述：日期处理类.
 */
@SuppressWarnings("all")
public class TimeUtil {
    /**
     * one day millisecond count
     */
    public static final long ONE_DAY_MILLISECONDS = 1000 * 3600 * 24;

    public static final long ONE_HOUR_MILLISECONDS = 1000 * 3600;

    public static final long ONE_MIN_MILLISECONDS = 1000 * 60;

    /**
     * 时间日期格式化到年月日时分秒.
     */
    public static String dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String dateFormatYMDHMS_f = "yyyyMMddHHmmss";
    public static String dateFormatMDHM = "MM-dd HH:mm";
    public static String dateFormat = "yyyy-MM-dd HH:mm";
    /**
     * 时间日期格式化到年月日.
     */
    public static String dateFormatYMD = "yyyy-MM-dd";

    /**
     * 时间日期格式化到年月日时分.中文显示
     */
    public static String dateFormatYMDHMofChinese = "yyyy年MM月dd日 HH:mm";

    /**
     * 时间日期格式化到年月日.中文显示
     */
    public static String dateFormatYMDofChinese = "yyyy年MM月dd日";
    /**
     * 时间日期格式化到月日.中文显示
     */
    public static String dateFormatMDofChinese = "MM月dd日";
    /**
     * 时间日期格式化到月.中文显示
     */
    public static String dateFormatMofChinese = "MM月";
    /**
     * 时间日期格式化到年月.
     */
    public static String dateFormatYM = "yyyy-MM";

    /**
     * 时间日期格式化到年月日时分.
     */
    public static String dateFormatYMDHM = "yyyy-MM-dd HH:mm";//2018-04-23 15:15:46

    /**
     * 时间日期格式化到月日.
     */
    public static String dateFormatMD = "MM/dd";
    public static String dateFormatM_D = "MM-dd";

    public static String dateFormatM = "MM月";
    public static String dateFormatD = "dd";
    public static String dateFormatM2 = "MM";

    public static String dateFormatMDHMofChinese = "MM月dd日HH时mm分";
    public static String dateFormatHMofChinese = "HH时mm分";

    /**
     * 时分秒.
     */
    public static String dateFormatHMS = "HH:mm:ss";

    /**
     * 时分.
     */
    public static String dateFormatHM = "HH:mm";


    /**
     * 上午/下午时分
     */
    public static String dateFormatAHM = "aHH:mm";

    public static String dateFormatYMDE = "yyyy/MM/dd E";
    public static String dateFormatYMD2 = "yyyy/MM/dd";

    private static String dateFormatYYYYMM = "yyyyMM";

    /**
     * 时间戳转特定格式时间
     *
     * @param dataFormat
     * @param timeStamp
     * @return
     */
    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp;
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        return format.format(new Date(timeStamp));
    }

    /*字符串转时间戳*/
    public static long parseTime(String strTime, String dataFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        long time = 0L;
        try {
            time = format.parse(strTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 将毫秒转换成秒
     *
     * @param time
     * @return
     */
    public static int convertToSecond(Long time) {
        Date date = new Date();
        date.setTime(time);
        return date.getSeconds();
    }

    /**
     * 描述：时间戳转化为Date类型.
     *
     * @param strDate String形式的日期时间
     * @param format  毫秒级时间戳
     * @return Date Date类型日期时间
     */
    public static Date getDateByFormat(long longDate, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = mSimpleDateFormat.parse(mSimpleDateFormat.format(longDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 描述：String类型的日期时间转化为Date类型.
     *
     * @param strDate String形式的日期时间
     * @param format  格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return Date Date类型日期时间
     */
    public static Date getDateByFormat(String strDate, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = mSimpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 描述：String类型的日期时间转化为Date类型.
     *
     * @param strDate String形式的日期时间
     * @param format  格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return Date Date类型日期时间
     */
    public static Date getDateByFormat(String strDate) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormatYMDHMS);
        Date date = null;
        try {
            date = mSimpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 描述：获取偏移之后的Date.
     *
     * @param date          日期时间
     * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return Date 偏移之后的日期时间
     */
    public Date getDateByOffset(Date date, int calendarField, int offset) {
        Calendar c = new GregorianCalendar();
        try {
            c.setTime(date);
            c.add(calendarField, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 描述：获取指定日期时间的字符串(可偏移).
     *
     * @param strDate       String形式的日期时间
     * @param format        格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return String String类型的日期时间
     */
    public static String getStringByOffset(String strDate, String format, int calendarField, int offset) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(mSimpleDateFormat.parse(strDate));
            c.add(calendarField, offset);
            mDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /**
     * 描述：Date类型转化为String类型(可偏移).
     *
     * @param date          the date
     * @param format        the format
     * @param calendarField the calendar field
     * @param offset        the offset
     * @return String String类型日期时间
     */
    public static String getStringByOffset(Date date, String format, int calendarField, int offset) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(date);
            c.add(calendarField, offset);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * from yyyy-MM-dd HH:mm:ss to MM-dd HH:mm
     */
    public static String formatDate(String before) {
        String after;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    .parse(before);
            after = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            return before;
        }
        return after;
    }

    /**
     * CST转string
     */
    public static String formatCST(String before) {
        String after;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            Date d = sdf.parse(before);
            after = new SimpleDateFormat("yyyy-MM-dd ").format(d);
        } catch (ParseException e) {
            return before;
        }
        return after;
    }

    /**
     * CST转string
     */
    public static String formatCST(String before, String pattern) {
        String after;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            Date d = sdf.parse(before);
            after = new SimpleDateFormat(pattern).format(d);
        } catch (ParseException e) {
            return before;
        }
        return after;
    }

    /**
     * 获取日期字符串（eg:180525）
     *
     * @return
     */
    public static String getDateStr() {
        Calendar now = Calendar.getInstance();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        //(now.get(Calendar.YEAR) - 1900), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.HOUR_OF_DAY), 0;
        //String time =now.get(Calendar.YEAR)+""+ (now.get(Calendar.MONTH)+1)+""+now.get(Calendar.DAY_OF_MONTH)+""+now.get(Calendar.HOUR_OF_DAY);
        String year = (now.get(Calendar.YEAR) + "").substring(2);
        String month = (now.get(Calendar.MONTH) + 1 + "").length() == 1 ? "0" + (now.get(Calendar.MONTH) + 1) : (now.get(Calendar.MONTH) + 1) + "";
        String day = (now.get(Calendar.DAY_OF_MONTH) + "").length() == 1 ? "0" + now.get(Calendar.DAY_OF_MONTH) : now.get(Calendar.DAY_OF_MONTH) + "";
        String hour = (now.get(Calendar.HOUR_OF_DAY) + "").length() == 1 ? "0" + now.get(Calendar.HOUR_OF_DAY) : now.get(Calendar.HOUR_OF_DAY) + "";

        return year + month + day + hour;
    }

    /**
     * 获取当日凌晨时间戳
     */
    public static Long getEarlyTime() {
        //获取当日凌晨时间戳
        long now = System.currentTimeMillis() / 1000L;
        long daySecond = 60 * 60 * 24;
        long dayTime = now - (now + 8 * 3600) % daySecond;
        return dayTime;
    }


    /**
     * 描述：Date类型转化为String类型.
     *
     * @param date   the date
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getStringByFormat(Date date, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        String strDate = null;
        try {
            strDate = mSimpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取指定日期时间的字符串,用于导出想要的格式.
     *
     * @param strDate String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式
     * @param format  输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 转换后的String类型的日期时间
     */
    public static String getStringByFormat(String strDate, String format) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(mSimpleDateFormat.parse(strDate));
            SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
            mDateTime = mSimpleDateFormat2.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /**
     * 描述：获取milliseconds表示的日期时间的字符串.
     *
     * @param format 格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 日期时间字符串
     */
    public static String getStringByFormat(long milliseconds, String format) {
        String thisDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            thisDateTime = mSimpleDateFormat.format(milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thisDateTime;
    }

    /**
     * 描述：获取表示当前日期时间的字符串.
     *
     * @param format 格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String String类型的当前日期时间
     */
    public static String getCurrentDate(String format) {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;

    }


    //获取当前系统当天日期
    public static String getCurrentDay() {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormat);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DAY_OF_MONTH, 0);
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    //获取当前系统当天日期
    public static String getCurrentDayToSecond() {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormatYMDHMS);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DAY_OF_MONTH, 0);
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    //获取当前系统当天日期
    public static String getCurrentDay2() {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormatYMDHMS);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DAY_OF_MONTH, 0);
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    //获取当前系统前后第几天
    public static String getNextDay(int i) {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormat);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DAY_OF_MONTH, i);
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    //获取当前系统前后第几小时
    public static String getNextHour(int i) {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormat);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.HOUR_OF_DAY, i);
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    /**
     * 描述：获取表示当前日期时间的字符串(可偏移).
     *
     * @param format        格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return String String类型的日期时间
     */
    public static String getCurrentDateByOffset(String format, int calendarField, int offset) {
        String mDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            c.add(calendarField, offset);
            mDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;

    }

    /**
     * 描述：计算两个日期所差的天数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的天数
     */
    public static int getOffectDay(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        //先判断是否同年
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);
        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int maxDays = 0;
        int day = 0;
        if (y1 - y2 > 0) {
            maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 + maxDays;
        } else if (y1 - y2 < 0) {
            maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 - maxDays;
        } else {
            day = d1 - d2;
        }
        return day;
    }

    /**
     * 描述：计算两个日期所差的小时数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的小时数
     */
    public static int getOffectHour(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int h1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int h2 = calendar2.get(Calendar.HOUR_OF_DAY);
        int h = 0;
        int day = getOffectDay(date1, date2);
        h = h1 - h2 + day * 24;
        return h;
    }

    /**
     * 描述：计算两个日期所差的分钟数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的分钟数
     */
    public static int getOffectMinutes(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int m1 = calendar1.get(Calendar.MINUTE);
        int m2 = calendar2.get(Calendar.MINUTE);
        int h = getOffectHour(date1, date2);
        int m = 0;
        m = m1 - m2 + h * 60;
        return m;
    }

    /**
     * 描述：获取本周一.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfWeek(String format) {
        return getDayOfWeek(format, Calendar.MONDAY);
    }

    public static String getFirstDayOfWeek(String format, Long time) {

        SimpleDateFormat sdf = new SimpleDateFormat(format); // 设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.set(GregorianCalendar.HOUR_OF_DAY, 0);
        cal.set(GregorianCalendar.MINUTE, 0);
        cal.set(GregorianCalendar.SECOND, 0);
        String imptimeBegin = sdf.format(cal.getTime());
        System.out.println("所在周星期一的日期：" + imptimeBegin);
//        cal.add(Calendar.DATE, 2);
//        String imptimeMi = sdf.format(cal.getTime());
//        System.out.println("所在周星期三的日期：" + imptimeMi);
//        cal.add(Calendar.DATE, 2);
//        String imptimeEnd = sdf.format(cal.getTime());
//        System.out.println("所在周星期五的日期：" + imptimeEnd);
        return imptimeBegin;
    }


    /**
     * 描述：获取本周日.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getLastDayOfWeek(String format) {
        return getDayOfWeek(format, Calendar.SUNDAY);
    }


    /**
     * 描述：获取本周的某一天.
     *
     * @param format        the format
     * @param calendarField the calendar field
     * @return String String类型日期时间
     */
    private static String getDayOfWeek(String format, int calendarField) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            int week = c.get(Calendar.DAY_OF_WEEK);
            if (week == calendarField) {
                strDate = mSimpleDateFormat.format(c.getTime());
            } else {
                int offectDay = calendarField - week;
                if (calendarField == Calendar.SUNDAY) {
                    offectDay = 7 - Math.abs(offectDay);
                }
                c.add(Calendar.DATE, offectDay);
                strDate = mSimpleDateFormat.format(c.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取本月第一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfMonth(String format) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            //当前月的第一天
            c.set(GregorianCalendar.DAY_OF_MONTH, 1);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取本月第一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfMonth(String format, Long date) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            //当前月的第一天
            if (date != null) {
                c.setTime(new Date(date));
            }
            c.set(GregorianCalendar.DAY_OF_MONTH, 1);
            c.set(GregorianCalendar.HOUR_OF_DAY, 0);
            c.set(GregorianCalendar.MINUTE, 0);
            c.set(GregorianCalendar.SECOND, 0);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取本月第一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfYear(String format, Long date) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            //当前月的第一天
            if (date != null) {
                c.setTime(new Date(date));
            }
            c.set(GregorianCalendar.DAY_OF_YEAR, 1);
            c.set(GregorianCalendar.HOUR_OF_DAY, 0);
            c.set(GregorianCalendar.MINUTE, 0);
            c.set(GregorianCalendar.SECOND, 0);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }


    /**
     * 描述：获取本月最后一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getLastDayOfMonth(String format) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            // 当前月的最后一天
            c.set(Calendar.DATE, 1);
            c.roll(Calendar.DATE, -1);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }


    /**
     * 描述：获取表示当前日期的0点时间毫秒数.
     *
     * @return the first time of day
     */
    public static long getFirstTimeOfDay() {
        Date date = null;
        try {
            String currentDate = getCurrentDate(dateFormatYMD);
            date = getDateByFormat(currentDate + " 00:00:00", dateFormatYMDHMS);
            return date.getTime();
        } catch (Exception e) {
        }
        return -1;
    }

    /**
     * 描述：获取表示当前日期24点时间毫秒数.
     *
     * @return the last time of day
     */
    public static long getLastTimeOfDay() {
        Date date = null;
        try {
            String currentDate = getCurrentDate(dateFormatYMD);
            date = getDateByFormat(currentDate + " 24:00:00", dateFormatYMDHMS);
            return date.getTime();
        } catch (Exception e) {
        }
        return -1;
    }

    /**
     * 描述：判断是否是闰年()
     * <p>(year能被4整除 并且 不能被100整除) 或者 year能被400整除,则该年为闰年.
     *
     * @param year 年代（如2012）
     * @return boolean 是否为闰年
     */
    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 400 != 0) || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 取指定日期为星期几
     *
     * @param strDate  指定日期
     * @param inFormat 指定日期格式
     * @return String   星期几
     */
    public static String getWeekNumber(String strDate, String inFormat) {
        String week = "星期日";
        Calendar calendar = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat(inFormat);
        try {
            calendar.setTime(df.parse(strDate));
        } catch (Exception e) {
            return "错误";
        }
        int intTemp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (intTemp) {
            case 0:
                week = "星期日";
                break;
            case 1:
                week = "星期一";
                break;
            case 2:
                week = "星期二";
                break;
            case 3:
                week = "星期三";
                break;
            case 4:
                week = "星期四";
                break;
            case 5:
                week = "星期五";
                break;
            case 6:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * 距离当前多少个小时
     *
     * @param dateStr
     * @return
     */
    public static int getExpiredHour(String dateStr) {
        int ret = -1;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date;
        try {
            date = sdf.parse(dateStr);
            Date dateNow = new Date();

            long times = date.getTime() - dateNow.getTime();
            if (times > 0) {
                ret = ((int) (times / ONE_HOUR_MILLISECONDS));
            } else {
                ret = -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

    /**
     * 友好显示时间差
     *
     * @param diff 毫秒
     * @return
     */
    public static String getFriendTimeOffer(long diff) {
        int day = (int) (diff / (24 * 60 * 60 * 1000));
        if (day > 0)
            return day + "天";
        int time = (int) (diff / (60 * 60 * 1000));
        if (time > 0)
            return time + "小时";
        int min = (int) (diff / (60 * 1000));
        if (min > 0)
            return min + "分钟";
        int sec = (int) diff / 1000;
        if (sec > 0)
            return sec + "秒";
        return "1秒";
    }

    /**
     * 友好的时间间隔
     *
     * @param duration 秒
     * @return
     */
    public static String getFriendlyDuration(long duration) {
        String str = "";
        long tmpDuration = duration;
        str += (tmpDuration / 60 > 10 ? tmpDuration / 60 : "0" + tmpDuration / 60) + ":";
        tmpDuration %= 60;
        str += (tmpDuration / 1 >= 10 ? tmpDuration / 1 : "0" + tmpDuration / 1);
        tmpDuration %= 1;
        return str;
    }

    /**
     * 友好的时间间隔2
     *
     * @param duration 秒
     * @return
     */
    public static String getFriendlyDuration2(long duration) {
        String str = "";
        long tmpDuration = duration;
        str += (tmpDuration / 60 > 0 ? tmpDuration / 60 + "'" : "");
        tmpDuration %= 60;
        str += (tmpDuration / 1 >= 10 ? tmpDuration / 1 + "''" : "0" + tmpDuration / 1 + "''");
        tmpDuration %= 1;
        return str;
    }

    public static String getFriendlyMusicDuration(long duration) {
        String str = "-";
        int tmpDuration = (int) (duration / 1000);//秒
        str += (tmpDuration / 3600 > 10 ? tmpDuration / 3600 : "0" + tmpDuration / 3600) + ":";
        tmpDuration %= 3600;
        str += (tmpDuration / 60 > 10 ? tmpDuration / 60 : "0" + tmpDuration / 60) + ":";
        tmpDuration %= 60;
        str += (tmpDuration / 1 >= 10 ? tmpDuration / 1 : "0" + tmpDuration / 1);
        tmpDuration %= 1;
        return str;
    }

    /**
     * 通过日期来确定星座
     *
     * @param mouth
     * @param day
     * @return
     */
    public static String getStarSeat(int mouth, int day) {
        String starSeat = null;
        if ((mouth == 3 && day >= 21) || (mouth == 4 && day <= 19)) {
            starSeat = "白羊座";
        } else if ((mouth == 4 && day >= 20) || (mouth == 5 && day <= 20)) {
            starSeat = "金牛座";
        } else if ((mouth == 5 && day >= 21) || (mouth == 6 && day <= 21)) {
            starSeat = "双子座";
        } else if ((mouth == 6 && day >= 22) || (mouth == 7 && day <= 22)) {
            starSeat = "巨蟹座";
        } else if ((mouth == 7 && day >= 23) || (mouth == 8 && day <= 22)) {
            starSeat = "狮子座";
        } else if ((mouth == 8 && day >= 23) || (mouth == 9 && day <= 22)) {
            starSeat = "处女座";
        } else if ((mouth == 9 && day >= 23) || (mouth == 10 && day <= 23)) {
            starSeat = "天秤座";
        } else if ((mouth == 10 && day >= 24) || (mouth == 11 && day <= 22)) {
            starSeat = "天蝎座";
        } else if ((mouth == 11 && day >= 23) || (mouth == 12 && day <= 21)) {
            starSeat = "射手座";
        } else if ((mouth == 12 && day >= 22) || (mouth == 1 && day <= 19)) {
            starSeat = "摩羯座";
        } else if ((mouth == 1 && day >= 20) || (mouth == 2 && day <= 18)) {
            starSeat = "水瓶座";
        } else {
            starSeat = "双鱼座";
        }
        return starSeat;
    }

    /**
     * 获取指定时间的毫秒值
     */
    public static long getDatelongMills(String fomat, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(fomat);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long longDate = date.getTime();
        return longDate;
    }

    /**
     * 两个日期比较
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() - dt2.getTime() > 0) {//date1>date2
                return 1;
            } else {
                return -1;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static boolean isValidDate(String str) {
        if (str == null) return false;

        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(true);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    public static boolean isValidDate(String str, String pattern) {
        if (str == null) return false;

        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(true);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }


    public static List<String> findDatesDay(Date dBegin, Date dEnd) {
        List<String> lDate = new ArrayList<String>();
        SimpleDateFormat sd = new SimpleDateFormat(TimeUtil.dateFormatYMD);
        lDate.add(sd.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(sd.format(calBegin.getTime()));
        }
        return lDate;
    }

    /**
     * @param minDate 最小时间  2015-01
     * @param maxDate 最大时间 2015-10
     * @return 日期集合 格式为 年-月
     * @throws Exception
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat(TimeUtil.dateFormatYMD);//格式化为年月

        try {
            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();

            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }

            return result;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 当前时间所在一周的周一和周日时间
     *
     * @param time 当前时间
     * @return
     */
    public static Map<String, String> getWeekDate(String time) {
        Map<String, String> map = new HashMap();
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date reDate = null;
        try {
            reDate = dateFormat2.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reDate);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        List<String> dateList = new ArrayList<String>();
        for (int i = 0; i < 7; i++) {
            String dayTemp = dateFormat2.format(calendar.getTime());
            dateList.add(dayTemp);
            calendar.add(Calendar.DATE, 1);
            if (i == 0) {
                map.put("mondayDate", dayTemp);
            }
            if (i == 6) {
                map.put("sundayDate", dayTemp);
            }
        }

        return map;
    }

    public static List<String> printWeekdays(String dateStr) {
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date reDate = null;
        try {
            reDate = dateFormat2.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reDate);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        List<String> dateList = new ArrayList<String>();
        for (int i = 0; i < 7; i++) {
            String dayTemp = dateFormat2.format(calendar.getTime());
            dateList.add(dayTemp);
            calendar.add(Calendar.DATE, 1);
        }
        return dateList;
    }

    /**
     * 获取某年某月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDayNumOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0); //输入类型为int类型
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 将 180817112342 转为yyyy-MM-dd HH:mm
     * @return String
     */
    public static String formatDateStr(String dateStr) {
        if (dateStr == null) return null;
        String year = dateStr.substring(0, 2);
        String month = dateStr.substring(2, 4);
        String day = dateStr.substring(4, 6);
        String hour = dateStr.substring(6, 8);
        String minute = dateStr.substring(8, 10);
        return "20" + year + "-" + month + "-" + day + " " + hour + ":" + minute;
    }

    /**
     * 将 180817112342 转为yyyy-MM-dd HH:mm:ss
     * @return String
     */
    public static String formatDateStr2(String dateStr) {
        if (dateStr == null) return null;
        String year = dateStr.substring(0, 2);
        String month = dateStr.substring(2, 4);
        String day = dateStr.substring(4, 6);
        String hour = dateStr.substring(6, 8);
        String minute = dateStr.substring(8, 10);
        String second = dateStr.substring(dateStr.length() - 2, dateStr.length());
        return "20" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }

    /**
     * 将 yyyy-MM-dd HH:mm:ss 转为 180817112342
     * @return String
     */
    public static String formatStrDate(String str){
       String str1 = str.replace("-","").replace(":","").replace(" ","");
       str = str1.substring(2);
       return str;
    }

    public static String formatThisMonth() {
        SimpleDateFormat dateFormat2 = new SimpleDateFormat(dateFormatYYYYMM);
        return dateFormat2.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(formatStrDate("2019-03-07 00:00:00"));
    }

    /**
     * 将 180817112342 转为 时间戳
     * @return String
     */
    public static Long formatDateStr3(String dateStr) {
        if (dateStr == null) return null;
        String year = dateStr.substring(0, 2);
        String month = dateStr.substring(2, 4);
        String day = dateStr.substring(4, 6);
        String hour = dateStr.substring(6, 8);
        String minute = dateStr.substring(8, 10);
        String second = dateStr.substring(dateStr.length() - 2, dateStr.length());

        String time = "20" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        return parseTime(time, dateFormatYMDHMS);
    }

    /**
     * 获取当前时间或时间
     *
     * @param theTime theTime
     */
    public static String getTimeOrTimeNow(Date theTime) {
        return getDateTime(dateFormatYMDHMS, theTime);
    }

    /**
     * 获取时间
     *
     * @param pattern pattern
     * @param aDate   aDate
     */
    private static String getDateTime(String pattern, Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";
        if (aDate == null) {
            System.out.print("aDate is null!");
        } else {
            df = new SimpleDateFormat(pattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 获取当前日期的时间戳
     */
    public static long getNowTimeStamp() {
        long returnTimeStamp;
        Date aDate = null;
        aDate = convertStringToDate("yyyy-MM-dd HH:mm:ss", getFullNowDateTime());
        if (aDate == null) {
            returnTimeStamp = 0;
        } else {
            returnTimeStamp = aDate.getTime();
        }
        return returnTimeStamp;
    }

    /**
     * 将字符串转成时间
     *
     * @param datePattern 格式
     * @param strDate     字符串的时间
     */
    public static Date convertStringToDate(String datePattern, String strDate) {
        SimpleDateFormat df;
        Date date;
        //传入的时间是以 / 分割
        int length = 2;
        if (strDate.split("_").length < length) {
            strDate = strDate.replace("/", "_");
        }
        if (strDate.split(" ").length > 1) {
            datePattern = dateFormatYMDHMS;
        }
        df = new SimpleDateFormat(datePattern);
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            return null;
        }
        return (date);
    }

    /**
     * 获取当前的完整的日期和时间
     */
    public static String getFullNowDateTime() {
        String strReturn;
        Date now = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            strReturn = sdf.format(now);
        } catch (Exception e) {
            strReturn = "";
        }
        return strReturn;
    }
}
