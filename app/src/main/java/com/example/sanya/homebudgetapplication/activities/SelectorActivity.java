package com.example.sanya.homebudgetapplication.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.sanya.homebudgetapplication.R;
import com.example.sanya.homebudgetapplication.data.handler.Selectable;
import com.example.sanya.homebudgetapplication.model.Entity;
import com.example.sanya.homebudgetapplication.util.DyntellAppCompatActivity;
import com.example.sanya.homebudgetapplication.util.SelectorAdapter;

import java.util.List;

/**
 * Created by Sanya.
 */
public class SelectorActivity extends DyntellAppCompatActivity {

	private ListView listView;
	private Selectable selectable;
	private final int DETAIL_REQUEST_CODE = 2;
	SelectorAdapter selectorAdapter;
	private List<Entity> entityList;
	private int isIncome = 0;
	private int userId;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selector);

		init();
		load();
		actions();
	}

	private void reloadListView() {
		if (getIntent().hasExtra("IS_INCOME")) {
			if (getIntent().getIntExtra("IS_INCOME", 0) == 1) {
				entityList = selectable.getIncomeEntity();
				isIncome = 1;
			} else {
				entityList = selectable.getSpendingEntity();
				isIncome = 0;
			}
		} else {
			entityList = selectable.getAllEntity();
			isIncome = 0;
		}
		selectorAdapter = new SelectorAdapter(this, entityList);
		listView.setAdapter(selectorAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem menuItem = menu.add(R.string.addNew).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				selectable.addNew(SelectorActivity.this, isIncome, userId);
				return false;
			}
		}).setIcon(android.R.drawable.ic_menu_add);
		MenuItemCompat.setShowAsAction(menuItem , MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case DETAIL_REQUEST_CODE:
					if (data.getIntExtra("DELETE", 0) == 1) {
						reloadListView();
					}
					break;
				default:
					break;
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		reloadListView();
	}

	@Override
	public void init() {
		getSupportActionBar().setTitle(R.string.title_selector);
		listView = (ListView) findViewById(R.id.list);

		if (getIntent().hasExtra("USER"))
			userId = getIntent().getIntExtra("USER", 0);
	}

	@Override
	public void load() {
		selectable = getIntent().getParcelableExtra("handler");
		selectable.setContext(this);
	}

	@Override
	public void actions() {
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				selectable.showDetails(SelectorActivity.this, entityList.get(position).getId(), isIncome, userId);
			}
		});

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
				final Dialog dialog = new Dialog(SelectorActivity.this);
				dialog.setContentView(R.layout.delete_list_item_dialog);
				dialog.setTitle(R.string.delete);

				TextView textView = (TextView) dialog.findViewById(R.id.text);
				textView.setText(entityList.get(position).getName());

				Button dialogButton = (Button) dialog.findViewById(R.id.deleteButton);
				dialogButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						selectable.deleteById(entityList.get(position).getId());
						dialog.dismiss();
						reloadListView();
					}
				});
				dialog.show();
				return true;
			}
		});
	}
}
