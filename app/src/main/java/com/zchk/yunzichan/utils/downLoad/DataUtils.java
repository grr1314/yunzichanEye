package com.zchk.yunzichan.utils.downLoad;

import com.zhy.http.okhttp.callback.Callback;

public abstract class DataUtils {
	/**
	 * ����
	 * @return
	 */
	public abstract void downLoad(String url,String param,Callback callback);

	/**
	 * �ϴ�
	 * @return
	 */
	public abstract void upLoad(String url,String param,Callback callback);
}
