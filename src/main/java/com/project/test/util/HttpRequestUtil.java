package com.project.test.util;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;


public class HttpRequestUtil {


    public static String getRealIP(String nameHeader,HttpServletRequest req) throws IOException {

        Enumeration<String> headerNames = req.getHeaderNames();
        String ip=null;


        while (headerNames.hasMoreElements()) {

            String headerName = headerNames.nextElement();

            Enumeration<String> headers = req.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                if(headerName.equalsIgnoreCase(nameHeader)){
                    ip=headerValue;
                    break;
                }

            }
            if(ip!=null){
                break;
            }

        }
        if(ip==null){
            ip=req.getRemoteAddr();
        }
        return ip;


    }
}
