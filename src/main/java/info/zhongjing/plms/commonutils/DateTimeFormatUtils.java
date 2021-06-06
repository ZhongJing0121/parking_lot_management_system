package info.zhongjing.plms.commonutils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhongJing </p>
 * @Description 解析日期时间的工具类 </p>
 * @date 2021/3/12 2:15 下午 </p>
 */
public class DateTimeFormatUtils {

    /**
     * 返回当前日期时间的字符串指定格式
     * 指定格式："yyyy-MM-dd hh:mm:ss"
     * @return 指定格式的时间字符串
     */
    public static String getNowDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormatter.format(now);
    }

    /**
     * 计算两个字符串格式的时间之间间隔的天数、小时、分钟数
     * 将这些间隔的时间分别以day、hour、minute作为键，间隔的时长作为值，放到一个Map集合中返回
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 返回一个Map集合，可以通过map.get("day"),map.get("hour"),map.get("minute")来获取间隔的天、小时、分钟数
     */
    public static Map<String, Integer> intervalTime(String startTime, String endTime) {
        Map<String, Integer> map = new HashMap<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime start = LocalDateTime.parse(startTime, dateTimeFormatter);
        LocalDateTime end = LocalDateTime.parse(endTime, dateTimeFormatter);

        Duration duration = Duration.between(start, end);
        long minutes = duration.toMinutes();

        long hour = minutes / 60;
        long minute = minutes % 60;
        long day = hour / 24;
        hour = hour % 24;

        map.put("day", new Long(day).intValue());
        map.put("hour", new Long(hour).intValue());
        map.put("minute", new Long(minute).intValue());

        return map;
    }

    /**
     * 用于计算两个字符串格式的日期之间间隔的年、月、日
     * 将这些间隔的时间分别以year、month、day作为键，间隔的时长作为值，放到一个Map集合中返回
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 返回一个Map集合，可以通过map.get("year"),map.get("month"),map.get("day")来获取间隔的天、小时、分钟数
     */
    public static Map<String, Integer> intervalDate(String startDate, String endDate) {
        Map<String, Integer> map = new HashMap<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate start = LocalDate.parse(startDate, dateTimeFormatter);
        LocalDate end = LocalDate.parse(endDate, dateTimeFormatter);

        Period period = Period.between(start, end);

        map.put("year", period.getYears());
        map.put("month", period.getMonths());
        map.put("day", period.getDays());

        return map;
    }

    /**
     * 用于判断字符串格式的目标日期是否在当前日期之前
     * @param targetTimeStr 字符串格式的目标日期
     * @return true表示在当前日期之前，false表示不在当前日期之前
     */
    public static boolean isBefore(String targetTimeStr) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate now = LocalDate.now();

        LocalDate targetTime = LocalDate.parse(targetTimeStr, dateTimeFormatter);

        return targetTime.isBefore(now);

    }

    /**
     * 将第一个LocalDate型的字符串，转换为LocalDateTime，并与第二个LocalDateTime字符串比较时间的早晚
     * @param localDateStr LocalDate的字符串
     * @param localDateTimeStr LocalDateTime的字符串
     * @return true表示第一个比第二个早，false表示第一个比第二个晚
     */
    public static boolean isa(String localDateStr, String localDateTimeStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(localDateStr, dateTimeFormatter);

        LocalDateTime localDateTime = localDate.atTime(0, 0, 0);

        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime2 = LocalDateTime.parse(localDateTimeStr, dateTimeFormatter);

        return localDateTime.isBefore(localDateTime2);

    }
}
