package so.contacts.hub.basefunction.net.manager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.protocol.HTTP;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;

import android.R.integer;
import android.text.TextUtils;
import so.contacts.hub.basefunction.net.BasePTStrRequest;
import so.contacts.hub.basefunction.net.CMSRequest;
import so.contacts.hub.basefunction.net.VolleyQueue;
import so.contacts.hub.basefunction.net.bean.BaseOldRequestData;
import so.contacts.hub.basefunction.net.bean.BaseRequestData;

/**
 * ****************************************************************
 * 文件名称 : PTHTTPImpl.java
 * 作 者 :   Robin
 * 创建时间 : 2015-11-7 下午3:34:08
 * 文件描述 : 葡萄服务请求接口实现类
 * 版权声明 : 深圳市葡萄信息技术有限公司 版权所有
 * 修改历史 : 2015-11-7 1.00 初始版本
 *****************************************************************
 */
public class PTHTTPImpl implements IPTHTTP
{
    /**
     * request类型
     */
    private int mHttpRequestType = PTHTTPManager.PT_HTTP_IMPL;

    /**
     * 超时时间
     */
    private static final long TIME_OUT = 30000;
    
    /**
     * 请求头部信息
     */
    private HashMap<String, String> defaultHeaders = new HashMap<String, String>();
    
    public PTHTTPImpl()
    {
    }

    public PTHTTPImpl(int httpRequestType)
    {
        this.mHttpRequestType = httpRequestType;
    }

    @Override
    public String post(String url, String queryStr)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String post(String url, BaseRequestData requestData)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void asynPost(String url, String queryStr, IResponse cb)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void asynPost(String url, BaseRequestData requestData, IResponse cb)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String get(String url, String queryStr)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String get(String url, BaseRequestData requestData)
    {
        //将url转成拼接后的字符串
        url = convertUrl(url, encodeParameters(requestData.getParams()));
        return sync(Request.Method.GET, url, requestData);
    }

    @Override
    public void asynGet(String url, String queryStr, IResponse cb)
    {
        
    }

    @Override
    public void asynGet(String url, BaseRequestData requestData, IResponse cb)
    {
        
    }

    @Override
    public void setDefaultHeader(String key, String value)
    {
        
    }

    /**
     * 解码请求参数，Map-String
     * 方法表述
     * @param params
     * @return
     * String
     */
    private String encodeParameters(Map<String, String> params)
    {
        StringBuilder parambBuilder = new StringBuilder();
        try
        {
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                parambBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                parambBuilder.append("=");
                parambBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                parambBuilder.append("&");
            }
            String encodeStr = "";
            switch (mHttpRequestType)
            {
                case PTHTTPManager.PT_HTTP_IMPL:
                    encodeStr = parambBuilder.toString();
                    break;
                case PTHTTPManager.CMS_HTTP_IMPL:
                    encodeStr = parambBuilder.deleteCharAt(parambBuilder.length() - 1).toString();
                    break;
                default:
                    break;
            }
            return encodeStr;
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("Encoding not supported: " + "UTF-8", e);
        }
    }
    /**
     * 拼接url信息
     * 方法表述
     * @param url
     * @param queryStr
     * @return
     * String
     */
    private String convertUrl(String url, String queryStr)
    {
        if (TextUtils.isEmpty(url))
        {
            return "";
        }
        if (TextUtils.isEmpty(queryStr))
        {
            return url;
        }
        if (url.endsWith("?"))
        {
            url += queryStr;
        }
        else
        {
            url += "?" + queryStr;
        }
        return url;
    }
    
    /**
     * 
     * 通用的同步请求方法,get或者post
     * @param method:get or post
     * @param url:拼接后的url
     * @param requestData：请求参数类
     * @return
     * String
     */
    private String sync(int method, String url, BaseRequestData requestData)
    {
        //根据requestType的不同，实例化请求request
        RequestFuture<String> future = RequestFuture.newFuture();
        BasePTStrRequest request = null;
        switch (mHttpRequestType)
        {
            case PTHTTPManager.PT_HTTP_IMPL:
                break;
            case PTHTTPManager.CMS_HTTP_IMPL:
                request = new CMSRequest(method, url, requestData, future, future);
                break;
            default:
                break;
        }
        //将request添加到volley请求队列中
        setDefaultHeader(request);
        VolleyQueue.getQueue().add(request);
        String content = null;
        try
        {
            content = future.get(TIME_OUT, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        catch (TimeoutException e)
        {
            e.printStackTrace();
        }
        return content;
    }
    
    private void setDefaultHeader(Request<?> request)
    {
        try
        {
            if (request != null)
            {
                Map<String, String> headers = request.getHeaders();
                for (String key : defaultHeaders.keySet())
                {
                    headers.put(key, defaultHeaders.get(key));
                }
            }
        }
        catch (AuthFailureError e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String syncPostString(String url, BaseOldRequestData baseOldRequestData)
    {
        RequestFuture<String> future = RequestFuture.newFuture();
        return null;
    }
    
    
}
