package com.sokrati.sokexmobile.httpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;

public class HttpTrackingClient
{
    private static int TIMEOUT = 360000; // 6 minutes

    private static String ENCODING = "UTF-8";
    private static String PARAM_LANGUAGE = "Content-Language";
    private static String VALUE_LANGUAGE = "en-US";

    private static String PARAM_CONTENT_TYPE = 
        "Content-Type";
    private static String VALUE_CONTENT_TYPE = 
        "application/x-www-form-urlencoded;charset="+ ENCODING;

    private static String PARAM_CONTENT_LENGTH = "Content-Length";
    
    
    public static String postData(String url, String postData) 
        throws IOException
    {
        URL urlObj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
        conn.setRequestMethod("POST");
        setRequestProperties(conn, postData );
        writeDataToConnection(conn, postData);
        return processResponse(conn);
    }

    public static String getData(String url)
        throws IOException
    {
        URL urlObj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
        conn.setRequestMethod("GET");
        return processResponse(conn);
    }

    /*
     * Process response obtained on the connection
     * */
    private static String processResponse(HttpURLConnection conn) 
        throws SocketException, IOException
    {
        int code;
        try
        {
            code = conn.getResponseCode();
        }
        catch (SocketException e)
        {
            throw e;
        }
        boolean success = false;
        if ((code >= 200) && (code < 300))
        {
            success = true;
        }

        StringBuffer response = new StringBuffer();

        if (success)
        {
            InputStream connStream = conn.getInputStream();
            BufferedReader connReader =
                new BufferedReader(new InputStreamReader(connStream));
            String line = null;
            while ((line = connReader.readLine()) != null)
            {
                response.append(line);
                response.append('\r');
            }
            connReader.close();
        }
        else 
        {
            InputStream connStream = conn.getErrorStream();
            BufferedReader connReader =
                new BufferedReader(new InputStreamReader(connStream));
            String line = null;
            response = new StringBuffer();
            while ((line = connReader.readLine()) != null)
            {
                response.append(line);
                response.append('\r');
            }
            connReader.close();
        } 
        return response.toString();
    }

    private static void setRequestProperties(HttpURLConnection conn,
                                             String data)
        throws UnsupportedEncodingException
    {
        // Set headers       
        conn.setConnectTimeout(TIMEOUT);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        conn.setInstanceFollowRedirects(false);

        conn.setRequestProperty(PARAM_LANGUAGE, VALUE_LANGUAGE);
        conn.setRequestProperty(PARAM_CONTENT_TYPE, VALUE_CONTENT_TYPE);
        conn.setRequestProperty(PARAM_CONTENT_LENGTH,                               //setting http header fields data
                                Integer.toString(data.getBytes(ENCODING).length));

    }

    private static void writeDataToConnection(HttpURLConnection conn,
                                              String requestData)
        throws IOException
    {
        DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
        writer.writeBytes(requestData);
        writer.flush();
        writer.close();
    }
}
