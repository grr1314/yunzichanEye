package com.zchk.yunzichan.utils.downLoad;

public abstract class DataFactory {

	/**
	 * ��������Ĳ�Ʒ����
	 * @param cls
	 * @return
	 */
	public abstract <T extends DataUtils> T createData(Class<T> cls);
}
