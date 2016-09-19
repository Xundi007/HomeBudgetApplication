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
import com.example.sanya.homebudgetapplication.data.handler.HandlerItem;
import com.example.sanya.homebudgetapplication.data.handler.HandlerTransaction;
import com.example.sanya.homebudgetapplication.data.handler.HandlerUser;
import com.example.sanya.homebudgetapplication.model.Item;
import com.example.sanya.homebudgetapplication.model.Transaction;
import com.example.sanya.homebudgetapplication.model.User;
import com.example.sanya.homebudgetapplication.util.DyntellAppCompatActivity;

import java.util.Date;

/**
 * Created by Sanya.
 */
public class TransactionActivity extends DyntellAppCompatActivity {

	private Button btnSave;
	private EditText txtItemName;
	private EditText txtItemValue;
	private Transaction transaction;
	private User user;

	private Boolean chkIsIncome = false;
	private int DEFAULT_CATEGORY = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction_manage);

		if(getIntent().hasExtra("KEY_ID")){
			transaction = new HandlerTransaction(this).getTransactionById(getIntent().getIntExtra("KEY_ID", 0));
		}

		if(getIntent().hasExtra("KEY_USER")){
			user = new HandlerUser(this).getUserById(getIntent().getIntExtra("KEY_USER", 0));
			//debug
			//user = new User();
			//user.setId(0);
		}

		if(getIntent().hasExtra("KEY_IS_INCOME")){
			if(getIntent().getIntExtra("KEY_IS_INCOME", 0) == 1){
				chkIsIncome = true;
			};
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
		getSupportActionBar().setTitle(R.string.title_transaction);
	}

	@Override
	public void load() {
		if (transaction != null) {
			Item item = new HandlerItem(this).getItemById(transaction.getItemId());
			txtItemName.setText(item.getName());
			txtItemValue.setText("" + transaction.getValue());
		}
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
					Item item = new HandlerItem(TransactionActivity.this).getItemByName(txtItemName.getText().toString());
					if(item == null){
						new HandlerItem(TransactionActivity.this).addItem(txtItemName.getText().toString(), DEFAULT_CATEGORY, Integer.parseInt(txtItemValue.getText().toString()), chkIsIncome);
						item = new HandlerItem(TransactionActivity.this).getItemByName(txtItemName.getText().toString());
					}
					if (transaction != null) {
						transaction.setItemId(item.getId());
						transaction.setValue(Integer.parseInt(txtItemValue.getText().toString()));
						new HandlerTransaction(TransactionActivity.this).updateTransaction(transaction);
					} else {
						Date now = new Date();
						new HandlerTransaction(TransactionActivity.this).addTransaction(user.getId(), item.getId(), Integer.parseInt(txtItemValue.getText().toString()), now.getTime(), chkIsIncome);
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
			txtItemName.setError(getString(R.string.transaction_edit_name_error));
			return false;
		} else if (txtItemValue.getText().toString().isEmpty()) {
			txtItemValue.setError(getString(R.string.transaction_edit_value_error));
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