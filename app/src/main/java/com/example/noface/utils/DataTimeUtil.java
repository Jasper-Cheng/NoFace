package com.example.noface.utils;


import android.content.Context;
import android.text.TextUtils;

import com.example.noface.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DataTimeUtil {
    private static String TAG = "DataTimeUtil";

    public static int getWeek(Long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return R.string.sunday;
            case 2:
                return R.string.monday;
            case 3:
                return R.string.tuesday;
            case 4:
                return R.string.wednesday;
            case 5:
                return R.string.thursday;
            case 6:
                return R.string.friday;
            case 7:
                return R.string.saturday;
            default:
                return 0;
        }
    }

    public static String getDate(Long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd ");
        return sf.format(d);
    }

    public static String getYYMMDDHHMM(Long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return sf.format(d);
    }

    public static String getLastWeekSaturday(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekSaturday(date));
        cal.add(Calendar.DATE, -7);
        return format.format(cal.getTime());
    }

    public static String getLastWeekFriday(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekSaturday(date));
        cal.add(Calendar.DATE, -1);
        return format.format(cal.getTime());
    }

    // ???????????????????????????
    public static String getYearMonth() {
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH);
        String m;
        if (month == 0) {
            m = "12";
        } else {
            m = month + "";
        }
        return m;
    }


    public static long getTimeByString(String data) {
        DateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        try {
            date = format1.parse(data);
            return date.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @return ????????????????????????0???
     */
    public static long getZeroTime(long time) {
        Date date = new Date(time);
        long l = 24 * 60 * 60 * 1000; //??????????????????
        //date.getTime()??????????????????????????? ?????? ???????????????????????????????????? ??????????????????%???????????????????????????????????????????????????????????????????????????????????????????????????UTC+0????????????
        //???8??????????????????????????????????????????????????????
        return (date.getTime() - (date.getTime() % l) - TimeZone.getDefault().getRawOffset());

        //return time / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
    }

    public static String getTime(Long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        int hour = c.get(Calendar.HOUR);
        int minuts = c.get(Calendar.MINUTE);
        return hour + ":" + minuts;
    }

    public static String getFormattedTime(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(new Date(time));
        return str;
    }

    public static String getFormatDate(long time, String format) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String parseLongTime(long time, String format) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(d);
    }

    public static String getFirstDay(long time, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        Date d = new Date(calendar.getTimeInMillis());
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(d);
    }

    public static Calendar parseStrTime(String format, String time) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(formatter.parse(time));
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMonthStr(Context context, int month) {
        String result = "";
        switch (month) {
            case 1:
                result = context.getString(R.string.January);
                break;
            case 2:
                result = context.getString(R.string.February);
                break;
            case 3:
                result = context.getString(R.string.March);
                break;
            case 4:
                result = context.getString(R.string.April);
                break;
            case 5:
                result = context.getString(R.string.May);
                break;
            case 6:
                result = context.getString(R.string.June);
                break;
            case 7:
                result = context.getString(R.string.July);
                break;
            case 8:
                result = context.getString(R.string.August);
                break;
            case 9:
                result = context.getString(R.string.September);
                break;
            case 10:
                result = context.getString(R.string.October);
                break;
            case 11:
                result = context.getString(R.string.November);
                break;
            case 12:
                result = context.getString(R.string.December);
                break;
            default:
                break;
        }
        return result;
    }

    public static String parseSecond(long second) {
        if (second < 10) {
            return "00:0" + second;
        }
        if (second < 60) {
            return "00:" + second;
        }
        if (second < 3600) {
            int minute = (int) (second / 60);
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                }
                return "0" + minute + ":" + second;
            }
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
        return null;
    }

    public static String timeParse(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((float) seconds / 1000);
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }

    public static String getCurrentYYMMDDHHMMSS() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(System.currentTimeMillis());
    }

    public static String getCurrentYYMMDDHHMMSS2() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return format.format(System.currentTimeMillis());
    }

    public static String getCurrentYYMMDD(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (TextUtils.isEmpty(time)) {
            return format.format(System.currentTimeMillis());
        } else {
            try {
                return format.format(time);
            } catch (Exception e) {
                LogUtil.i(TAG,"getCurrentYYMMDD:"+ e.getMessage());
                return format.format(System.currentTimeMillis());
            }
        }
    }

    public static String getCurrentYYMMDD2(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        if (time < 100) {
            return format.format(System.currentTimeMillis());
        } else {
            try {
                return format.format(time);
            } catch (Exception e) {
                LogUtil.i(TAG,"getCurrentYYMMDD2:"+ e.getMessage());
                return format.format(System.currentTimeMillis());
            }
        }
    }

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date getDateFromStringYYMMDD(String time) {
        DateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        try {
            date = format1.parse(time);
            return date;
        } catch (Exception e) {
            LogUtil.i(TAG,"getDateFromStringYYMMDD:"+ e.getMessage());
            return null;
        }
    }

    public static String formatDateTimeMMSS(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String format = sdf.format(new Date(time));
        return format;
    }

    public static String getDateFromStringYYMMDDHHMM2(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        try {
            return format.format(time);
        } catch (Exception e) {
            LogUtil.i(TAG,"getDateFromStringYYMMDDHHMM2:"+ e.getMessage());
            return format.format(System.currentTimeMillis());
        }
    }

    public static String getDateFromStringYYMMDD(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        try {
            return format.format(time);
        } catch (Exception e) {
            LogUtil.i(TAG,"getDateFromStringYYMMDDHHMM:"+ e.getMessage());
            return format.format(System.currentTimeMillis());
        }
    }

    public static String getDateFromStringYYMMDDHHMM(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        try {
            return format.format(Long.parseLong(time));
        } catch (Exception e) {
            LogUtil.i(TAG,"getDateFromStringYYMMDDHHMM:"+ e.getMessage());
            return format.format(System.currentTimeMillis());
        }
    }

    public static String getDateFromStringYYMMDD2(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        try {
            return format.format(Long.parseLong(time));
        } catch (Exception e) {
            LogUtil.i(TAG,"getDateFromStringYYMMDD:"+ e.getMessage());
            return format.format(System.currentTimeMillis());
        }
    }

    /**
     * ????????????????????????????????????
     *
     * @param day
     * @return
     */
    public static String getLastWeekDay(int day) {
        Calendar cal = Calendar.getInstance();
        //n?????????????????????1?????????-1?????????????????????2?????????????????????
        int n = -1;
        cal.add(Calendar.DATE, n * 7);
        //???????????????????????????Calendar.MONDAY???TUESDAY...???
        cal.set(Calendar.DAY_OF_WEEK, day);
        cal.add(Calendar.DAY_OF_YEAR, 0);
        return new SimpleDateFormat("yyyy.MM.dd").format(cal.getTime());
    }

    /**
     * ??????????????????
     * @return
     */
    public static String getLastMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // ?????????????????????
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // ?????????????????????
        date = calendar.getTime();
        String accDate = format.format(date);
        return accDate;
    }

    /**
     * ???????????????
     * @param date
     * @return
     */
    public static String geLastWeekSunday(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekSunday(date));
        cal.add(Calendar.DATE, -7);
        return format.format(cal.getTime());
    }

	public static String geLastWeekSaturday(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekSaturday(date));
        cal.add(Calendar.DATE, -7);
        return format.format(cal.getTime());
    }

	public static String geLastWeekFriday(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekSaturday(date));
        cal.add(Calendar.DATE, -1);
        return format.format(cal.getTime());
    }

    public static Date getThisWeekSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // ?????????????????????????????????????????????
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // ???????????????????????????????????????????????????????????????????????????????????????
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        // ?????????????????????????????????????????????
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // ???????????????????????????????????????????????????????????????????????????????????????
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

	public static Date getThisWeekSaturday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.SATURDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        //cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        if (day == Calendar.SATURDAY){
			cal.add(Calendar.DATE, 0);
		} else {
			cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - 7 - day);
		}        
        return cal.getTime();
    }
}
