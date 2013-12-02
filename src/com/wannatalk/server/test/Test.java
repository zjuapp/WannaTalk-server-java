package com.wannatalk.server.test;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class Test {
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost("localhost",8081,"http");
		PostMethod method = new PostMethod("/api/register");
		NameValuePair []user = {new NameValuePair("username", "test"), new NameValuePair("password", "123")};
		method.setRequestBody(user);
		client.executeMethod(method);
		System.out.println(method.getStatusLine());
		String response = method.getResponseBodyAsString();
		System.out.println(response);
	}
}
