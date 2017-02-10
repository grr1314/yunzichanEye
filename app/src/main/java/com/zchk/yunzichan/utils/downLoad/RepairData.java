package com.zchk.yunzichan.utils.downLoad;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;


public class RepairData extends DataUtils {
	@Override
	public void downLoad(String url, String param, Callback callback) {
		// TODO Auto-generated method stub
		OkHttpUtils.postString().url(url).content(param)// ����
		.build().execute(callback);
	}

	@Override
	public void upLoad(String url, String param, Callback callback) {
		// TODO Auto-generated method stub
	}


}
