package com.zchk.yunzichan.ui.fragment.base;

import com.zchk.yunzichan.utils.HttpMethods;
import com.zhy.http.okhttp.callback.Callback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ViewStub;


/**
 * 所有Fregment的父类，与BaseActivity的功能类似。
 *
 * @author SenseTech
 *
 */
public abstract class BaseFragment extends Fragment {

	private static final String TAG = "BaseFragment";

	// 判断当前的Fragment是否可见
	private boolean isVisible;
	private boolean isPrepared;
	private boolean isFirst = true;
	private ViewStub vs_loding;

	/**
	 * 重写Fragment的setUserVisibleHint() 注意这个方法在onCreateView()方法之前执行
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			// 表示可见
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//        Log.d("TAG", "fragment->onActivityCreated");
		isPrepared = true;
		isFirst=true;
		onVisible();
	}
	/**
	 * 可见
	 */
	protected void onVisible() {
		if (!isPrepared || !isVisible || !isFirst) {
			Log.e(TAG,"isFalse");
			Log.e(TAG,"isPrepared"+isPrepared);
			Log.e(TAG,"isVisible"+isVisible);
			Log.e(TAG,"isFirst"+isFirst);
			return;
		}
		Log.e(TAG,"isTrue");
		onLoad();
		isFirst=false;
	}

	/**
	 * 不可见
	 */
	protected void onInvisible() {

	}

	protected abstract void onLoad();

	public void FragmentHttps(String url, int type, String param, Callback call) {
		if (type == 1) {
			HttpMethods.postString(url, param, call);
		} else {
			HttpMethods.getString(url, param, call);
		}
	}

}
