package com.zchk.yunzichan.ui.activity.file;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.ui.activity.BaseActivity;


public class RelevantFileContentActivity extends BaseActivity {

	public TextView textView2;
	private StringBuilder text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.relevant_file_content);
		initViews();
		putDate();

		textView2 = (TextView) findViewById(R.id.textView2);
		textView2.setText(text.toString());

	}

	private void initViews() {
		// TODO Auto-generated method stub
		initTopBar("文档详情", true, false, true);
		initTopBarListener(null, null, new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void putDate() {
		text = new StringBuilder().append("    特创电辅助加热模块，解决热泵低温下加热效率低与加热满的难题，")
				.append("用水时开启MAX增容功能，超大热水量多至305% 基于逆卡诺原理，在气温-5至5的寒冷冬季，")
				.append("热泵加热胸膛那个的平均节能效率仅为最佳运行状态的34%，且加热时间会增加4倍以上。")
				.append("在低温环境下采用热泵系统加热，会造成系统高负荷运作，令压缩机等核心部件的寿命大大缩短。\n")
				.append("    A.O.史密斯特创辅助加热模块，在室外温度低于7摄氏度时，自动启动电辅助加热模块，确保在")
				.append("低温环境下快速获得舒适热水，且有效保护热泵加热系统，更能满足您的全天候热水需求。");
	}
}

