package com.medical.common.bean;

/**
 * 
 * note:返回结果
 * 
 */
public class ReturnResult {
    
    // success：成功； error：失败
    private String rtnCode;
    
    // 后台向页面返回的信息
    private String rtnMsg;
    
    // 
    private String url;
    
    //自定义返回信息
    private Object data;


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
     * @return 返回{@linkplain #rtnCode}
     */
    public String getRtnCode() {
        return rtnCode;
    }

    /**
     * @param 设定{@linkplain #rtnCode}
     */
    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    /**
     * @return 返回{@linkplain #rtnMsg}
     */
    public String getRtnMsg() {
        return rtnMsg;
    }

    /**
     * @param 设定{@linkplain #rtnMsg}
     */
    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    /**
     * @return 返回{@linkplain #url}
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param 设定{@linkplain #url}
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    
    

}
