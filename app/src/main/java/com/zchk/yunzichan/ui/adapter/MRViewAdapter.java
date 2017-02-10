package com.zchk.yunzichan.ui.adapter;

import java.util.List;
import java.util.Map;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zchk.yunzichan.R;

public class MRViewAdapter extends
		RecyclerView.Adapter<MRViewAdapter.MRViewHolder> {

	private List<Map<String, Object>> list;
	private Context context;
	LayoutInflater mInflater;
	RecyclerItemClickListener recyclerItemClickListener;

	public MRViewAdapter(List<Map<String, Object>> list, Context context) {
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	public void setRecyclerItemClickListener(
			RecyclerItemClickListener recyclerItemClickListener) {
		this.recyclerItemClickListener = recyclerItemClickListener;
	}

	public interface RecyclerItemClickListener {
		public void OnItemClick(View v, int pos);
	}

	@Override
	public MRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = mInflater.inflate(R.layout.item_recyclerview, parent, false);
		MRViewHolder viewHolder = new MRViewHolder(view);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(MRViewHolder holder, final int position) {
		if (holder == null) {
			Log.e("ADapter", "holder is null");
		}
		if (list == null) {
			Log.e("ADapter", "list is not null" + list.size());
		}
		View view = holder.itemView;
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				recyclerItemClickListener.OnItemClick(view, position);
			}
		});
		Map<String,Object>item=list.get(position);
		holder.mTextView.setText(item.get("title").toString());
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	class MRViewHolder extends RecyclerView.ViewHolder {
		TextView mTextView;

		public MRViewHolder(View itemView) {
			super(itemView);
			mTextView = (TextView) itemView.findViewById(R.id.recyclerView_item_tv);
		}
	}

}
