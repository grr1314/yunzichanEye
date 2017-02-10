package com.zchk.yunzichan.utils.downLoad;

import java.io.IOException;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

public class CheckData extends DataUtils {

	@Override
	public void downLoad(String url, String param, Callback callback) {
		// TODO Auto-generated method stub
		try {
			OkHttpUtils.postString().url(url).content(param)// ����
			.build().execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void upLoad(String url, String param, Callback callback) {
		// TODO Auto-generated method stub
	}


}
