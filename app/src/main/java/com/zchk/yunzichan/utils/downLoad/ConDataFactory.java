package com.zchk.yunzichan.utils.downLoad;

public class ConDataFactory extends DataFactory {

	@Override
	public <T extends DataUtils> T createData(Class<T> cls) {
		// TODO Auto-generated method stub
		DataUtils dUtils=null;
		try {
			dUtils=(DataUtils) Class.forName(cls.getName()).newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return (T) dUtils;
	}

}
