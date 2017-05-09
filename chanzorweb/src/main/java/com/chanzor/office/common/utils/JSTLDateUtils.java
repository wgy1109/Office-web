package com.chanzor.office.common.utils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class JSTLDateUtils extends TagSupport {
	private static final long serialVersionUID = -3354015192721342312L;
	private String value;
	private String parttern;
    public void setValue(String value) {
        this.value = value;
    }
   
    public void setParttern(String parttern) {
		this.parttern = parttern;
	}
    
	public int doStartTag() throws JspException{
		try { 
			String vv = String.valueOf(value);  
	        if(StringUtils.isEmpty(vv)){
	        	pageContext.getOut().write("");
	        }else{
		        long time = Long.valueOf(vv);  
		        Calendar c = Calendar.getInstance();  
		        c.setTimeInMillis(time);  
		        SimpleDateFormat dateformat =new SimpleDateFormat(parttern);  
		        String s = dateformat.format(c.getTime());  
	            pageContext.getOut().write(s);  
	        }
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return super.doStartTag();
    }
    
}
