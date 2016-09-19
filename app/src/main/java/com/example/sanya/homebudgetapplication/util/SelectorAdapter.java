package com.example.sanya.homebudgetapplication.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.sanya.homebudgetapplication.R;
import com.example.sanya.homebudgetapplication.model.Entity;

import java.util.List;

/**
 * Created by Sanya.
 */
public class SelectorAdapter extends DyntellBaseAdapter {

	public SelectorAdapter(Context context, List<Entity> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		view = super.getView(position, view, parent);

		((TextView) view.findViewById(R.id.name)).setText(((Entity) list.get(position)).getName());

		return view;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.list_item;
	}
}
