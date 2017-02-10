package com.zchk.yunzichan.ui.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.zchk.yunzichan.R;

public class MyDialog extends Dialog {

	protected MyDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public static class Builder {
		private Context context;
		private String message;
		private String title;
		private String PosBtnText;
		private String NaBtnText;

		private TextView dialog_btn_neg;// ȷ��
		private TextView dialog_btn_pos;// ȡ��

		private OnClickListener neg_Listener;
		private OnClickListener pos_Listener;

		public Builder(Context context) {
			this.context = context;
		}

		// ����title
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		// ����content
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		// ����ȷ����ť
		public Builder setNegativeButton(String negativeButtonText,
				OnClickListener lis_neg) {
			NaBtnText = negativeButtonText;
			this.neg_Listener = lis_neg;
			return this;
		}

		// ����ȡ����ť
		public Builder setPositiveButton(String str,
				OnClickListener lis_pos) {
			PosBtnText = str;
			this.pos_Listener = lis_pos;
			return this;
		}

		// ����ͼ��
		public Builder setIcon(int ic) {

			return this;
		}

		public MyDialog create() {
			// ����һ��LayoutInflater
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.dialog, null);
			final MyDialog dialog = new MyDialog(context, R.style.Dialog);

			dialog.addContentView(view, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			// �O��title
			TextView tv_title = (TextView) view.findViewById(R.id.diglog_title);
			tv_title.setText(title);
			// �O��message
			TextView tv_message = (TextView) view
					.findViewById(R.id.dialog_message);
			tv_message.setText(message);

			// ���ð�ť
			dialog_btn_neg = (TextView) view.findViewById(R.id.dialog_btn_neg);
			dialog_btn_neg.setText(NaBtnText);
			dialog_btn_neg.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					neg_Listener.onClick(dialog,
							DialogInterface.BUTTON_NEGATIVE);
				}
			});

			dialog_btn_pos = (TextView) view.findViewById(R.id.dialog_btn_pos);
			dialog_btn_pos.setText(PosBtnText);
			dialog_btn_pos.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					pos_Listener.onClick(dialog,
							DialogInterface.BUTTON_POSITIVE);
				}
			});
			//����ͼ��ӵ�windows�����
			dialog.setContentView(view);
			return dialog;
		}
	}

}
