package com.springboot.mmall.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	
	
   private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class) ;
	public static StringBuilder get(String url) throws IOException {
		StringBuilder builder = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		try {
			httpClient = HttpClients.createDefault();
			HttpGet get = new HttpGet(url);
			get.setHeader("Accept", "application/json;charset=utf-8");
			get.setHeader(new BasicHeader("Content-type", "application/json;charset=utf-8"));
			// 超时
			RequestConfig config = RequestConfig.custom().setConnectTimeout(125000).setConnectionRequestTimeout(12000)
					.setSocketTimeout(12500).build();
			get.setConfig(config);
			get.addHeader("Accept-Encoding", "gzip");
			httpResponse = httpClient.execute(get);
			builder = getResult(httpResponse.getEntity());
		} catch (Exception e) {
			logger.error("调用接口失败",e);
		} finally {
			if (null != httpClient) {
				httpClient.close();
			}
			if (null != httpResponse) {
				httpResponse.close();
			}
		}
		return builder;
	}

	public static StringBuilder post(String url, String data) throws IOException {
		StringBuilder stringBuilder = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		try {
			httpClient = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			post.setHeader("Accept", "application/json;charset=utf-8");
			post.setHeader(new BasicHeader("Content-type", "application/json;charset=utf-8"));
			// 超时时间
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(7500)
					.setConnectionRequestTimeout(7000).setSocketTimeout(8500).build();
			post.setConfig(requestConfig);
			post.setEntity(new StringEntity(data, "UTF-8"));
			httpResponse = httpClient.execute(post);
			stringBuilder = getResult(httpResponse.getEntity());
		} catch (Exception e) {
			logger.error("调用接口失败",e);
		} finally {
			if (httpClient != null) {
				httpClient.close();
			}
			if (httpResponse != null) {
				httpResponse.close();
			}
		}
		
		System.out.println(stringBuilder);
		return stringBuilder;
	}

	private static StringBuilder getResult(HttpEntity httpEntity)
			throws UnsupportedEncodingException, UnsupportedOperationException, IOException {
		StringBuilder stringBuilder = null;
		if (null != httpEntity) {
			BufferedReader brReader = new BufferedReader(new InputStreamReader(httpEntity.getContent(), "UTF-8"));
			stringBuilder = new StringBuilder();
			String line = "";
			while ((line = brReader.readLine()) != null) {
				stringBuilder.append(line);

			}
		}
		return stringBuilder;
	}
	
	public static void main(String[] args) throws IOException {
		
		//HttpClientUtil.post("http://dapiqa.saic-gm.com/folServiceAgent/invoiceTransfer/receiveInvoice", "{\"name\":\"周润发\",\"age\":23,\"birthday\":560016000000,\"email\":\"zhourunfa@qq.com\"}")
 //;
		
		HttpClientUtil.post("http://dapiqa.saic-gm.com/folServiceAgent/invoiceTransfer/receiveInvoice","{\"name\":\"周润发\",\"age\":23,\"birthday\":560016000000,\"email\":\"zhourunfa@qq.com\"}");
	}

}
