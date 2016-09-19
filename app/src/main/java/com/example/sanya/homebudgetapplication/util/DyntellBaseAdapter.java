package com.example.sanya.homebudgetapplication.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.sanya.homebudgetapplication.R;

import java.util.List;

/**
 * Created by Sanya.
 */
public abstract class DyntellBaseAdapter extends BaseAdapter {
	protected List<?> list;
	protected Context context;

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getLayoutId(), parent, false);
		if (position % 2 == 0) {
			view.setBackgroundResource(R.color.listItemBackgroundC1);
		} else {
			view.setBackgroundResource(R.color.listItemBackgroundC2);
		}
		return view;
	}

	protected abstract int getLayoutId();
}
