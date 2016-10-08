package com.medical.common.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工具类
 *
 */
public class Util implements Serializable {

	private static final Logger log = LoggerFactory.getLogger(Util.class);
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * 按指定格式获得当前日期字符串
	 * @param format 格式字符串
	 */
	public static String getDate(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 将long类型日期转换成String
	 */
	public static String getStrDate(long publishTime){
		String strDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( publishTime );
		return strDate;

	}
	
	/**
	 * 按指定格式获得指定日期字符串
	 * @param format 格式字符串
	 * @param date 指定日期
	 */
	public static String getDate(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	  /**
     * 方法描述：时间比较方法<br>
     * date1>date2时返回1， date1=date2时返回0， date1<date2时返回-1
     * @param date1 比较源时间
     * @param date2 被比较的时间
     * @return int型结果
     */
    public static int compare(Date date1, Date date2) {
        long l1 = date1.getTime();
        long l2 = date2.getTime();
        if(l1 > l2) {
            return 1;
        } else if(l1 == l2) {
            return 0;
        } else if(l1 < l2) {
            return -1;
        }
        return 0;
    }
	/**
	 * 获得指定日期前面或后面某天的日期
	 * @param date 日期
	 * @param i 相差的天数(负值为前面的日期)
	 */
	@SuppressWarnings("static-access")
	public static Date relationDate(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(cal.DATE, i);
		return cal.getTime();
	}

	/**
	 * 转换日期字符串成日期对象
	 * @param s 日期字符串
     * @throws	Exception 如果发生异常
	 */
	public static Date parseDate(String s) throws Exception {
		DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT,
												   new Locale("zh", "CN"));
		return df.parse(s);
	}
	
	/**
     * 转换日期字符串成日期对象
     * @param s 日期字符串
     * @throws  Exception 如果发生异常
     */
    public static Date parseDate(String s, String formart) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(formart);
        return sdf.parse(s);
    }

	/**
	 * 安全分隔字符串方法
	 * @param s 字符串
     * @param regex 分隔正则表达式
     */
    public String[] split(String s, String regex) {
        return split(s, regex, -1);
	}

	/**
	 * 安全分隔字符串方法
	 * @param s 字符串
     * @param regex 分隔正则表达式
     * @param limit 限定起始下标
     */
    public String[] split(String s, String regex, int limit) {
        if (null == s) {
            return null;
        } else {
            return s.split(regex, limit);
        }
    }

	/**
	 * 转换字符串为大写
	 * @param s 字符串
	 */
	public String toUpperCase(String s) {
		return varFormat(s).toUpperCase();
	}

	/**
	 * 转换字符串为小写
	 * @param s 字符串
	 */
	public String toLowerCase(String s) {
		return varFormat(s).toLowerCase();
	}

	/**
	 * 获取固定长度字符串
	 * @param s 字符串
	 * @param len 长度
	 */
	public String limitFormat(String s, int len) {
		return limitFormat(s, len, true, "");
	}

	/**
	 * 获取固定长度字符串
	 * @param s 字符串
	 * @param len 长度
	 * @param c 后缀
	 */
	public String limitFormat(String s, int len, String c) {
		return limitFormat(s, len, true, c);
	}

	/**
	 * 获取固定长度字符串
	 * @param s 字符串
	 * @param len 长度
	 * @param b 方向
	 */
	public String limitFormat(String s, int len, boolean b) {
		return limitFormat(s, len, b, "");
	}

	/**
	 * 获取固定长度字符串
	 * @param s 字符串
	 * @param len 长度
	 * @param b 方向
	 * @param c 前/后缀
	 */
	public String limitFormat(String s, int len, boolean b, String c) {
		s = varFormat(s);
		c = varFormat(c);
		if(s.length() > len) {
			//需要截取
			if(b) {
				//从前向后截取
                if("".equals(c)) {
                    return (s.substring(0, len));
                } else {
                    //加后缀
                    return (s.substring(0, len)+c);
                }
			} else {
				//从后向前截取
                if("".equals(c)) {
                    return (s.substring(s.length()-len, s.length()));
                } else {
                    //加前缀
                    return (c+s.substring(s.length()-len, s.length()));
                }
			}
		} else {
			//不需要截取
			return (s);
		}
	}

	/**
	 * 处理字符串符合SQL格式
	 * @param s 字符串
	 */
    public static String sqlFormat(String s) {
        s = varFormat(s);
        if(s.indexOf("'") !=-1){
            s = s.replaceAll("'", "''");
        }
        if(s.indexOf("%")!=-1){
            s =s.replaceAll("%", "\\\\%");
        }
        return s;
    }

	/**
	 * 处理字符串符合URL格式
	 * @param s 字符串
	 */
	public static String urlFormat(String s) {
		return varFormat(s).replaceAll("&", "%26");
	}

	/**
	 * 处理字符串符合XML格式
	 * @param s 字符串
	 * @param c 需要编码字符
	 */
	public static String xmlFormat(String s, String c) {
		s = varFormat(s);
		c = varFormat(c);
		if (c.indexOf("&") != -1) {
			s = s.replaceAll("&", "&amp;");
		}
		if (c.indexOf("<") != -1) {
			s = s.replaceAll("<", "&lt;");
		}
		if (c.indexOf(">") != -1) {
			s = s.replaceAll(">", "&gt;");
		}
		if (c.indexOf("\"") != -1) {
			s = s.replaceAll("\"", "&quot;");
		}
		if (c.indexOf("'") != -1) {
			s = s.replaceAll("'", "&#39;");
		}
		if (c.indexOf(" ") != -1) {
			s = s.replaceAll(" ", "&nbsp;");
		}
		return s;
	}

	/**
	 * 处理字符串符合XML格式
	 * @param s 字符串
	 */
	public static String xmlFormat(String s) {
		return xmlFormat(s, "<'&\">");
	}
	
	public static String xmlMobileFormat(String s){
	    s = s.replaceAll("&", "");
	    return xmlFormat(s, "<'&\">");
	}
	

	/**
	 * 处理字符串符合XML格式且不生成空字符串
	 * @param s 字符串
	 * @param c 需要编码字符
	 */
	public String xmlSpanFormat(String s, String c) {
		s = xmlFormat(s, c);
		if(s.equals("")) {
			s = "　";
		}
		return s;
	}

	/**
	 * 处理字符串符合XML格式且不生成空字符串
	 * @param s 字符串
	 */
	public static String xmlSpanFormat(String s) {
		s = xmlFormat(s);
		if(s.equals("")) {
			s = "　";
		}
		return s;
	}

	/**
	 * 处理字符串符合JavaScript的字符串格式
	 * @param s 字符串
	 */
	public String jsStringFormat(String s) {
		if(s == null) {
			s = "";
		} else {
			s = s.replaceAll("\\\\", "\\\\\\\\");
			s = s.replaceAll("\b", "\\\\b");
			s = s.replaceAll("\f", "\\\\f");
			s = s.replaceAll("\n", "\\\\n");
			s = s.replaceAll("\r", "\\\\r");
			s = s.replaceAll("\t", "\\\\t");
			s = s.replaceAll("'", "\\\\'");
			s = s.replaceAll("\"", "\\\\\"");
		}
		return s;
	}



    @SuppressWarnings("static-access")
	public static Date relationMonth(Date date,int i){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(cal.MONTH, i);
		return cal.getTime();
    }


	//implements Serializable
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException,IOException {
		ois.defaultReadObject();
	}

    public long getLong(String num){
        if(num==null) return 0;
        else{
            String pattern = "[0-9]+";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(num);
            boolean b = m.matches();
            if(b){
                return Long.parseLong(num);
            }
        }
        return 0;
    }
    public double getDouble(String num){
        if(num==null) return 0;
        else{
            String pattern = "[0-9]+(.[0-9]+)?";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(num);
            boolean b = m.matches();
            if(b){
                return Double.parseDouble(num);
            }
        }
        return 0;
    }

    public static boolean checkNum(String num){
        boolean b = false;
        if(num==null || varCheckEmp(num) || num.length() != 11) {

        }else{
            String pattern = "[0-9]+";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(num);
             b = m.matches();
        }
        return b;
    }

	public static Calendar getCalendar(){
		Calendar calendar = new GregorianCalendar();
		setCalendar(calendar);
		return calendar;
	}

	private static void setCalendar(Calendar calendar){
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(1);
	}

	public static Calendar getCalendar(int year, int month, int day_of_month){
		Calendar calendar = new GregorianCalendar(year,month,day_of_month);
		setCalendar(calendar);
		return calendar;
	}

	public static String htmlStringFormat(String text) {
		  if(text==null || "".equals(text))
		      return "";
		  text = text.replace("<", "&lt;");
		  text = text.replace(">", "&gt;");
		  text = text.replace(" ", "&nbsp;");
		  text = text.replace("\"", "&quot;");
		  text = text.replace("\'", "&apos;");
		  return text.replace("\n", "<br/>");
	}

    /**
     * 处理字符串空指针
     * @param s 字符串
     */
    public static boolean varCheckEmp(String s) {
        boolean b = false;
        if(s == null || "".equals(s.trim())) {
            b = true;
        }
        return b;
    }

    /**
     * 处理字符串空指针
     * @param s 字符串
     */
    public static String varFormat(String s) {
        if(s == null || "".equals(s.trim())) {
            s = "";
        }
        return s;
    }

       /**
     * 处理字符串空指针
     * @param s 字符串
     */
    public static String varFormat1(String s) {
        if(s == null || "".equals(s.trim())) {
            s = "　";
        }
        return s;
    }

    /**
    * 处理字符串空格
    * @param s 字符串
    */
   public static String varTrim(String s) {
       if(s == null) {
           s = "";
       } else {
           s = s.trim();
       }
       return s;
   }
   /**
	 * 向HttpServletResponse中写数据,设置ContentType为html/txt;charset=utf-8
	 * @param response
	 * @param text 要写入的数据
	 */
	public static void writeUtf8Text(HttpServletResponse response,String text){
		//response 相关处理
		response.setContentType("html/text;charset=utf-8");
		//response.setCharacterEncoding("utf-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		try {
			response.getWriter().write(text);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	   /**
		 * 向HttpServletResponse中写数据,设置ContentType为html/txt;charset=utf-8
		 * @param response
		 * @param text 要写入的数据
		 */
		public static void writeUtf8Text1(HttpServletResponse response,String text){
			//response 相关处理
	      response.setHeader("Pragma", "no-cache");  
          response.setHeader("Cache-Control", "no-cache");  
          response.setHeader("Expires", "0");  
          response.setContentType("text/html;charset=UTF-8"); 
			try {
				response.getWriter().write(text);
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		public static void writeUtf8JSON(HttpServletResponse response,String text){
			//response 相关处理
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			try {
				response.getWriter().write(text);
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

		/**
		 * 
		 * 方法描述：获取请求参数
		 * @date: 2014-4-2 上午11:10:11
		 * @param request
		 * @return
		 */
		public static String getRequestParam(HttpServletRequest request){
			InputStreamReader isr;
			StringBuffer sb = new StringBuffer();
			try {

				isr = new InputStreamReader(request.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				String s = "";
				while ((s = br.readLine()) != null) {
					sb.append(s);
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			return sb.toString();
		}
		public static Map<String,String> getRequestKeyValue(String param){
			Map<String,String> map=new HashMap<String,String>();
			if(StringUtils.isNotBlank(param)){
				String[] keyAndValues=param.split("&");
				for(String keyAndValue:keyAndValues){
					String[] kv=keyAndValue.split("=");
					if(kv!=null&&kv.length==2){
						map.put(kv[0], kv[1]);
					}
				}
			}
			return map;
			
		}
    /**
     * 获取知道日期的星期
     * 
     * @param date
     * @return
     * @throws Exception
     */
    public static String getWeek(Date date) {
          String week = "";
          Calendar cal = Calendar.getInstance();
          cal.clear();
          cal.setTime(date);
          if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
              week = "日";
          } else if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
              week = "一";
          } else if (cal.get(Calendar.DAY_OF_WEEK) == 3) {
              week = "二";
          } else if (cal.get(Calendar.DAY_OF_WEEK) == 4) {
              week = "三";
          } else if (cal.get(Calendar.DAY_OF_WEEK) == 5) {
              week = "四";
          } else if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
              week = "五";
          } else if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
              week = "六";
          }
          return week;
      }
    
    /**
     * 方法描述：获取访问客户端IP
     * @date: 2013-10-10 下午3:23:14
     * @param request
     * @return
     */
    public static String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    } 
	/**
	 * 
	 * 方法描述：转换对象为JSON字符串
	 * @param value
	 * @return
	 */
	public static String toJsonStr(Object value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (JsonGenerationException e) {
			log.error(e.getMessage());
		} catch (JsonMappingException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return "";
	}
	
	private static ObjectMapper mapper = new ObjectMapper();
	/**
	 * 
	 * 方法描述：转化json字符串为对象。
	 * @param context
	 * @param valueType
	 * @return
	 */
	public static <T> T readValue(String context,Class<T> valueType){
		try {
			return  mapper.readValue(context, valueType);
		} catch (JsonParseException e) {
			log.error(e.getMessage());
		} catch (JsonMappingException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}
	/**
	 * 方法描述：转换JSon对象为制定类对象
	 * @param context
	 * @param collectionClass
	 * @param elementCLasses
	 * @return
	 */
	public static <T> T readValue(String context, Class<?> collectionClass,  Class<?>... elementCLasses){
		try {
			//return  mapper.readValue(context, new TypeReference<SynRst<SynDepartment>>() {});
			return  mapper.readValue(context, getCollectionJavaType(collectionClass, elementCLasses));
		} catch (Exception e) {
			log.error("转化json字符串为对象异常", e);
		}
		return null;
	}
	
	/**
	 * 方法描述：根据类对象获得JavaType类型

	 * @param collectionClass
	 * @param elementCLasses
	 * @return
	 */
	private static JavaType getCollectionJavaType(Class<?> collectionClass, Class<?>... elementCLasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementCLasses);
	}
	
	/**
	 * 方法描述：字符串转成UTF8字符
	 * @param s
	 * @return
	 */
	public static String toUtf8String(String s){ 
	     StringBuffer sb = new StringBuffer(); 
	       for (int i=0;i<s.length();i++){ 
	           char c = s.charAt(i); 
	           if (c >= 0 && c <= 255){sb.append(c);} 
	         else{ 
	         byte[] b; 
	          try { b = Character.toString(c).getBytes("utf-8");} 
	          catch (Exception ex) { 
	                    b = new byte[0]; 
	          } 
	             for (int j = 0; j < b.length; j++) { 
	              int k = b[j]; 
	               if (k < 0) k += 256; 
	               sb.append("%" + Integer.toHexString(k).toUpperCase());  
	               } 
	     } 
	  } 
	  return sb.toString(); 
	}
	/**
	 * 判断字符串是否为空
	 * @param s
	 * @param trimFlag
	 * @return
	 */
	public static boolean isEmpty(String s, boolean trimFlag){
		if(s == null){
			return true;
		}
		if(trimFlag){
			s = s.trim();
		}
		if(s.length() == 0){
			return true;
		}
		return false;
	}
	/**
	 * 获取语言版本
	 * @param request
	 * @return
	 */
	public static String getLanguageTag(HttpServletRequest request){
		if(request == null){
			return "zh";
		}
		String languageTag = request.getLocale().toLanguageTag();
		if(languageTag == null || languageTag.length() <2){
			return "zh";
		}
		return languageTag.substring(0,2).toLowerCase();
	}
}

