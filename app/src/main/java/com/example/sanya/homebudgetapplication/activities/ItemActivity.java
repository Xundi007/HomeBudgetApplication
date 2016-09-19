package com.example.sanya.homebudgetapplication.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.example.sanya.homebudgetapplication.R;
import com.example.sanya.homebudgetapplication.data.handler.HandlerCategory;
import com.example.sanya.homebudgetapplication.data.handler.HandlerItem;
import com.example.sanya.homebudgetapplication.data.handler.HandlerUser;
import com.example.sanya.homebudgetapplication.model.Category;
import com.example.sanya.homebudgetapplication.model.Entity;
import com.example.sanya.homebudgetapplication.model.Item;
import com.example.sanya.homebudgetapplication.model.User;
import com.example.sanya.homebudgetapplication.util.DyntellAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanya.
 */
public class ItemActivity extends DyntellAppCompatActivity {

	private Button btnSave;
	private EditText txtItemName;
	private EditText txtItemValue;
	private CheckBox chkIsIncome;
	private User user;
	private Item item;

	private Spinner spinnerCategory;
	private List<Entity> allCategories = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_manage);

		if(getIntent().hasExtra("KEY_ID")){
			item = new HandlerItem(this).getItemById(getIntent().getIntExtra("KEY_ID", 0));
		}

		if(getIntent().hasExtra("KEY_USER")){
			user = new HandlerUser(this).getUserById(getIntent().getIntExtra("KEY_USER", 0));
			//debug
			//user = new User();
			//user.setId(0);
		}

		init();
		load();
		actions();
	}

	@Override
	public void init() {
		btnSave = (Button) findViewById(R.id.btn_save);
		txtItemName = (EditText) findViewById(R.id.edit_item_name);
		txtItemValue = (EditText) findViewById(R.id.edit_item_value);
		spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
		chkIsIncome = (CheckBox) findViewById(R.id.chk_is_income);
		getSupportActionBar().setTitle(R.string.title_item);
	}

	@Override
	public void load() {
		allCategories = new HandlerCategory(this).getAllEntity();
		List<String> categoryNames = new ArrayList<>();
		for (Entity e : allCategories) {
			categoryNames.add(e.getName());
		}

		if (item != null) {
			txtItemName.setText(item.getName());
			txtItemValue.setText("" + item.getLastValue());
			chkIsIncome.setChecked(item.getIncome());
		}

		ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryNames);
		spinnerCategory.setAdapter(categoryAdapter);
	}

	@Override
	public void actions() {
		txtItemName.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				txtItemName.setError(null);
			}
		});

		txtItemValue.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				txtItemValue.setError(null);
			}
		});

		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (validate()) {
					Category category;
					if(spinnerCategory.getSelectedItem() != null){
						category = new HandlerCategory(ItemActivity.this).getCategoryByName(spinnerCategory.getSelectedItem().toString());
					} else {
						category = new Category();
						category.setId(0);
					}
					if (item != null) {
						item.setName(txtItemName.getText().toString());
						item.setLastValue(Integer.parseInt(txtItemValue.getText().toString()));
						item.setCategoryId(category.getId());
						item.setIncome(chkIsIncome.isChecked());
						new HandlerItem(ItemActivity.this).updateItem(item);
					} else {
						new HandlerItem(ItemActivity.this).addItem(txtItemName.getText().toString(), category.getId(), Integer.parseInt(txtItemValue.getText().toString()), chkIsIncome.isChecked());
					}
					finish();
				} else {
					Snackbar.make(view, R.string.snackbar_tipp, Snackbar.LENGTH_LONG)
							.setAction("Action", null).show();
				}
			}
		});
	}

	private boolean validate() {
		if (txtItemName.getText().toString().isEmpty()) {
			txtItemName.setError(getString(R.string.item_edit_name_error));
			return false;
		} else if (txtItemValue.getText().toString().isEmpty()) {
			txtItemValue.setError(getString(R.string.item_edit_value_error));
			return false;
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}