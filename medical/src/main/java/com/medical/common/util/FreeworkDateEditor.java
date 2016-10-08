package com.medical.common.util;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 自动日期转换类
 * @author 
 * @time 下午4:45:38
 */
public class FreeworkDateEditor extends PropertyEditorSupport {

    public static final FreeworkDateEditor INSTANCE=new FreeworkDateEditor();
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private final long startTime;


    private FreeworkDateEditor() {
        try {
            startTime = new SimpleDateFormat(
                    YYYY_MM_DD_HH_MM_SS).parse("1970-01-01 00:00:00").getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Parse the Date from the given text, using the specified DateFormat.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.hasText(text)) {
            // Treat empty String as null value.
            setValue(null);
        } else if (text.trim().matches("\\d+")) {
            Long mis = Long.parseLong(text.trim());
            if (mis > startTime) {
                setValue(new Date(mis));
            } else {
                throw new IllegalArgumentException("Could not parse date: " + text);
            }
        } else {
            try {
                Date date = parseDate(text);
                setValue(date);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
            }
        }
    }

    public static Date parseDate(String text)  {
        try {
            return DateUtils.parseDate(text, YYYY_MM_DD_HH_MM_SS, "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH","yyyy-MM-dd HH:mm", "yyyy-MM", "yyyyMM", "yyyyMMdd", "yyyyMMddHHmmss","HH:mm");
        } catch (ParseException e) {
            Throwables.propagate(e);
        }
        return null;
    }
    
    public static String formatHHMM(Object date) {
    	DateFormat format = new SimpleDateFormat("HH:mm");
    	return format.format(date);
    }
    
    public static String formatYYYYMMDD(Object date) {
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	return format.format(date);
    }
    
    public static String formatYYYYMMDDHHMMSS(Object date) {
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return format.format(date);
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        return (value != null ? new SimpleDateFormat(
                YYYY_MM_DD_HH_MM_SS).format(value) : "");
    }

    public static void main(String[] args) {
        System.out.println("hello");
        String time="2014-9-11 01:10:00";
        Date date = null;
        try {
            date = DateUtils.parseDate(time, YYYY_MM_DD_HH_MM_SS, "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date.toLocaleString());
    }


}
