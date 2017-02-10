package com.zchk.yunzichan.ui.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.zchk.yunzichan.utils.Constant;

import java.util.List;


/**
 * Created by HP on 2016/7/1.
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class PageRecyclerView extends RecyclerView {

	private static final String TAG = "PageRecyclerView";

	private Context mContext = null;

	private PageAdapter myAdapter = null;

	private int shortestDistance; // �����˾���Ļ�������Ч
	private float slideDistance = 0; // �����ľ���
	private float scrollX = 0; // X�ᵱǰ��λ��

	private int spanRow = 1; // ����
	private int spanColumn = 3; // ÿҳ����
	private int totalPage = 0; // ��ҳ��
	private int currentPage = 1; // ��ǰҳ

	private int itemMarginH = 0;// �����item���
	private int itemMarginV = 0;// �����item���
	private int pageMargin = 0; // ҳ���

	private PageIndicatorView mIndicatorView = null; // ָʾ������

	private int realPosition = 0; // ������λ�ã����ϵ��´����ҵ����з�ʽ�任�ɴ����Ҵ��ϵ��µ����з�ʽ���λ�ã�

	/*
	 * 0: ֹͣ��������ָ�ƿ�; 1: ��ʼ����; 2: ��ָ�����׵Ķ�������ָ�뿪��Ļǰ����������һ�£�
	 */
	private int scrollState = 0; // ����״̬

	public PageRecyclerView(Context context) {
		this(context, null);
	}

	public PageRecyclerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PageRecyclerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		defaultInit(context);
	}

	// Ĭ�ϳ�ʼ��
	private void defaultInit(Context context) {
		this.mContext = context;
		setLayoutManager(new AutoGridLayoutManager(mContext, spanRow,
				AutoGridLayoutManager.HORIZONTAL, false, 0));
		setOverScrollMode(OVER_SCROLL_NEVER);
	}

	/**
	 * ����������ÿҳ����
	 * 
	 * @param spanRow
	 *            ������<=0��ʾʹ��Ĭ�ϵ�����
	 * @param spanColumn
	 *            ÿҳ������<=0��ʾʹ��Ĭ��ÿҳ����
	 */
	@SuppressLint("NewApi")
	public void setPageSize(int spanRow, int spanColumn) {
		this.spanRow = spanRow <= 0 ? this.spanRow : spanRow;
		this.spanColumn = spanColumn <= 0 ? this.spanColumn : spanColumn;
		setLayoutManager(new AutoGridLayoutManager(mContext, this.spanRow,
				AutoGridLayoutManager.HORIZONTAL, false, 0));
	}

	/**
	 * ����ҳ���
	 * 
	 * @param pageMargin
	 *            ���(px)
	 */
	public void setPageMargin(int pageMargin) {
		this.pageMargin = pageMargin;
	}

	/**
	 * ����ָʾ��
	 * 
	 * @param indicatorView
	 *            ָʾ������
	 */
	public void setIndicator(PageIndicatorView indicatorView) {
		this.mIndicatorView = indicatorView;
		if (mIndicatorView != null) {
			AutoGridLayoutManager autoGridLayoutManager = new AutoGridLayoutManager(
					mContext, this.spanRow, AutoGridLayoutManager.HORIZONTAL,
					false, Constant.BOTTOMBAR_HEIGHT
							+ Constant.INDICATOR_HEIGHT);

			Log.e(TAG, "" + mIndicatorView.getMeasuredHeight());
			setLayoutManager(autoGridLayoutManager);
			requestLayout();
		}

	}

	@Override
	protected void onMeasure(int widthSpec, int heightSpec) {
		super.onMeasure(widthSpec, heightSpec);
		shortestDistance = getMeasuredWidth() / 3;
	}

	@Override
	public void setAdapter(Adapter adapter) {
		super.setAdapter(adapter);
		this.myAdapter = (PageAdapter) adapter;
		update();
	}

	// ����ҳ��ָʾ�����������
	private void update() {
		// ������ҳ��
		int temp = ((int) Math.ceil(myAdapter.dataList.size()
				/ (double) (spanRow * spanColumn)));
		if (temp != totalPage) {
			mIndicatorView.initIndicator(temp);
			// ҳ������ҵ�ǰҳΪ���һҳ
			if (temp < totalPage && currentPage == totalPage) {
				currentPage = temp;
				// ִ�й���
				smoothScrollBy(-getWidth(), 0);
			}
			mIndicatorView.setSelectedPage(currentPage - 1);
			totalPage = temp;
		}
	}

	@Override
	public void onScrollStateChanged(int state) {
		switch (state) {
		case 2:
			scrollState = 2;
			break;
		case 1:
			scrollState = 1;
			break;
		case 0:
			if (slideDistance == 0) {
				break;
			}
			scrollState = 0;
			if (slideDistance < 0) { // ��ҳ
				currentPage = (int) Math.ceil(scrollX / getWidth());
				if (currentPage * getWidth() - scrollX < shortestDistance) {
					currentPage += 1;
				}
			} else { // ��ҳ
				currentPage = (int) Math.ceil(scrollX / getWidth()) + 1;
				if (currentPage <= totalPage) {
					if (scrollX - (currentPage - 2) * getWidth() < shortestDistance) {
						// �����һҳ�������벻�㣬��λ��ǰһҳ
						currentPage -= 1;
					}
				} else {
					currentPage = totalPage;
				}
			}
			// ִ���Զ�����
			smoothScrollBy((int) ((currentPage - 1) * getWidth() - scrollX), 0);
			// �޸�ָʾ��ѡ����
			mIndicatorView.setSelectedPage(currentPage - 1);
			slideDistance = 0;
			break;
		}
		super.onScrollStateChanged(state);
	}

	public void onScrolled(int dx, int dy) {
		scrollX += dx;
		if (scrollState == 1) {
			slideDistance += dx;
		}

		super.onScrolled(dx, dy);
	}

	/**
	 * ����������
	 */
	public class PageAdapter extends Adapter {

		private List<?> dataList = null;
		private CallBack mCallBack = null;
		private int itemWidth = 0;
		private int itemCount = 0;

		/**
		 * ʵ����������
		 * 
		 * @param data
		 * @param callBack
		 */
		public PageAdapter(List<?> data, CallBack callBack) {
			this.dataList = data;
			this.mCallBack = callBack;
			itemCount = dataList.size() + spanRow * spanColumn;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent,
				int viewType) {
			if (itemWidth <= 0) {
				// ����Item�Ŀ��
				itemWidth = (parent.getWidth() - pageMargin * 2) / spanColumn;
			}

			ViewHolder holder = mCallBack.onCreateViewHolder(
					parent, viewType);

			holder.itemView.measure(0, 0);
			holder.itemView.getLayoutParams().width = itemWidth;
			holder.itemView.getLayoutParams().height = holder.itemView
					.getMeasuredHeight();

			return holder;
		}

		@Override
		public void onBindViewHolder(ViewHolder holder,
				int position) {
			if (spanColumn == 1) {
				// ÿ��Item�������������pageMargin
				holder.itemView.getLayoutParams().width = itemWidth
						+ pageMargin * 2;
				holder.itemView.setPadding(pageMargin, 0, pageMargin, 0);
			} else {
				int m = position % (spanRow * spanColumn);
				if (m < spanRow) {
					// ÿҳ����Item�������pageMargin
					holder.itemView.getLayoutParams().width = itemWidth
							+ pageMargin;
					holder.itemView.setPadding(pageMargin + itemMarginH,
							itemMarginV, itemMarginH, itemMarginV);
				} else if (m >= spanRow * spanColumn - spanRow) {
					// ÿҳ�Ҳ��Item�����ұ�pageMargin
					holder.itemView.getLayoutParams().width = itemWidth
							+ pageMargin;
					holder.itemView.setPadding(itemMarginH, itemMarginV,
							pageMargin + itemMarginH, itemMarginH);
				} else {
					// �м��������ʾ
					holder.itemView.getLayoutParams().width = itemWidth;
					holder.itemView.setPadding(itemMarginH, itemMarginV,
							itemMarginH, itemMarginV);
				}
			}

			countRealPosition(position);

			holder.itemView.setTag(realPosition);

			setListener(holder);

			if (realPosition < dataList.size()) {
				holder.itemView.setVisibility(View.VISIBLE);
				mCallBack.onBindViewHolder(holder, realPosition);
			} else {
				holder.itemView.setVisibility(View.INVISIBLE);
			}

		}

		@Override
		public int getItemCount() {
			return itemCount;
		}

		private void countRealPosition(int position) {
			// Ϊ��ʹItem�����Ҵ��ϵ������У���Ҫposition��ֵ
			int m = position % (spanRow * spanColumn);
			switch (m) {
			case 1:
			case 5:
				realPosition = position + 2;
				break;
			case 3:
			case 7:
				realPosition = position - 2;
				break;
			case 2:
				realPosition = position + 4;
				break;
			case 6:
				realPosition = position - 4;
				break;
			case 0:
			case 4:
			case 8:
				realPosition = position;
				break;
			}
		}

		private void setListener(ViewHolder holder) {
			// ���ü���
			holder.itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mCallBack.onItemClickListener(v, (Integer) v.getTag());
				}
			});

			holder.itemView.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					mCallBack.onItemLongClickListener(v, (Integer) v.getTag());
					return true;
				}
			});
		}

		/**
		 * ɾ��Item
		 * 
		 * @param position
		 *            λ��
		 */
		public void remove(int position) {
			if (position < dataList.size()) {
				// ɾ������
				dataList.remove(position);
				itemCount--;
				// ɾ��Item
				notifyItemRemoved(position);
				// ���½����Ϸ����ı��Item
				notifyItemRangeChanged(
						(currentPage - 1) * spanRow * spanColumn, currentPage
								* spanRow * spanColumn);
				// ����ҳ��ָʾ��
				update();
			}
		}

	}

	public interface CallBack {

		/**
		 * ����VieHolder
		 * 
		 * @param parent
		 * @param viewType
		 */
		ViewHolder onCreateViewHolder(ViewGroup parent,
									  int viewType);

		/**
		 * �����ݵ�ViewHolder
		 * 
		 * @param holder
		 * @param position
		 */
		void onBindViewHolder(ViewHolder holder, int position);

		void onItemClickListener(View view, int position);

		void onItemLongClickListener(View view, int position);

	}
}