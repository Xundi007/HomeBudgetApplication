package com.example.sanya.homebudgetapplication.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.sanya.homebudgetapplication.R;
import com.example.sanya.homebudgetapplication.data.handler.HandlerCategory;
import com.example.sanya.homebudgetapplication.model.Category;
import com.example.sanya.homebudgetapplication.util.DyntellAppCompatActivity;

/**
 * Created by Sanya.
 */
public class CategoryActivity extends DyntellAppCompatActivity {

	private Button btnSave;
	private EditText txtCategoryName;
	private Category category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_manage);

		if(getIntent().hasExtra("KEY_ID")){
			category = new HandlerCategory(this).getCategoryById(getIntent().getIntExtra("KEY_ID", 0));
		}

		init();
		load();
		actions();
	}

	@Override
	public void init() {
		btnSave = (Button) findViewById(R.id.btn_save);
		txtCategoryName = (EditText) findViewById(R.id.edit_cat_name);
		getSupportActionBar().setTitle(R.string.title_category);
	}

	@Override
	public void load() {
		if (category != null) {
			txtCategoryName.setText(category.getName());
		}
	}

	@Override
	public void actions() {
		txtCategoryName.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				txtCategoryName.setError(null);
			}
		});

		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (validate()) {
					if (category != null) {
						category.setName(txtCategoryName.getText().toString());
						new HandlerCategory(CategoryActivity.this).updateCategory(category);
					} else {
						new HandlerCategory(CategoryActivity.this).addCategory(txtCategoryName.getText().toString());
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
		if (txtCategoryName.getText().toString().isEmpty()) {
			txtCategoryName.setError(getString(R.string.category_edit_name_error));
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