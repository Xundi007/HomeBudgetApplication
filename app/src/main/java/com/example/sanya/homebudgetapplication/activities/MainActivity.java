package com.example.sanya.homebudgetapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.sanya.homebudgetapplication.R;
import com.example.sanya.homebudgetapplication.data.handler.HandlerCategory;
import com.example.sanya.homebudgetapplication.data.handler.HandlerItem;
import com.example.sanya.homebudgetapplication.data.handler.HandlerTransaction;
import com.example.sanya.homebudgetapplication.data.handler.HandlerUser;
import com.example.sanya.homebudgetapplication.model.User;
import com.example.sanya.homebudgetapplication.util.DyntellAppCompatActivity;


public class MainActivity extends DyntellAppCompatActivity {

    private final int LOGIN_REQUEST_CODE = 1;
	private User loggedInUser;

	private Button manageItems;
	private Button manageIncomeTransactions;
	private Button manageSpendingTransactions;
	private Button manageCategory;
	private Button manageStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		init();
		load();
		actions();
    }

    @Override
    public void init() {
		toolbar.setTitle(R.string.main_title);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivityForResult(intent, LOGIN_REQUEST_CODE);
		manageItems = (Button) findViewById(R.id.btn_manage_items);
		manageIncomeTransactions = (Button) findViewById(R.id.btn_add_income);
		manageSpendingTransactions = (Button) findViewById(R.id.btn_add_spending);
		manageCategory = (Button) findViewById(R.id.btn_categories);
		manageStatistics = (Button) findViewById(R.id.btn_statistics);

		// debug
		//loggedInUser = new User();
		//loggedInUser.setId(0);
    }

    @Override
    public void load() {

    }

    @Override
    public void actions() {
		manageItems.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, SelectorActivity.class);
				intent.putExtra("handler", new HandlerItem(MainActivity.this, loggedInUser.getId()));
				//startActivityForResult(intent, COMPANY_REQUEST_CODE);
				startActivity(intent);
			}
		});
		manageIncomeTransactions.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, SelectorActivity.class);
				intent.putExtra("handler", new HandlerTransaction(MainActivity.this));
				intent.putExtra("IS_INCOME", 1);
				intent.putExtra("USER", loggedInUser.getId());
				//startActivityForResult(intent, COMPANY_REQUEST_CODE);
				startActivity(intent);
			}
		});
		manageSpendingTransactions.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, SelectorActivity.class);
				intent.putExtra("handler", new HandlerTransaction(MainActivity.this));
				intent.putExtra("IS_INCOME", 0);
				intent.putExtra("USER", loggedInUser.getId());
				//startActivityForResult(intent, COMPANY_REQUEST_CODE);
				startActivity(intent);
			}
		});
		manageCategory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, SelectorActivity.class);
				intent.putExtra("handler", new HandlerCategory(MainActivity.this));
				//startActivityForResult(intent, COMPANY_REQUEST_CODE);
				startActivity(intent);
			}
		});
		manageStatistics.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, StatisticActivity.class));
			}
		});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case LOGIN_REQUEST_CODE:
					int loggedInId = data.getIntExtra("USER", 0);
					loggedInUser = new HandlerUser(MainActivity.this).getUserById(loggedInId);
                    break;
                default:
                    break;
            }
        }
    }
}
