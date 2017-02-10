package com.zchk.yunzichan.utils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

public class HttpMethods {
	/**
	 *
	 * @param url
	 * @param param
	 * @return
	 */
	public static void postString(String url, String param, Callback call) {
		OkHttpUtils.postString().url(url).content(param)
				.build().connTimeOut(20000).readTimeOut(20000)
				.writeTimeOut(20000).execute(call);
	}
	
	/**
	 *
	 * @param url
	 * @param param
	 * @param call
	 */
	public static void getString(String url, String param, Callback call) {
		// TODO Auto-generated method stub

	}
}
