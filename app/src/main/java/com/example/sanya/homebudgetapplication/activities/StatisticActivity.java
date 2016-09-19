package com.example.sanya.homebudgetapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import com.example.sanya.homebudgetapplication.R;
import com.example.sanya.homebudgetapplication.data.handler.HandlerTransaction;
import com.example.sanya.homebudgetapplication.model.Transaction;
import com.example.sanya.homebudgetapplication.util.DyntellAppCompatActivity;
import com.example.sanya.homebudgetapplication.util.FragmentAdapter;
import com.example.sanya.homebudgetapplication.util.FragmentRefresh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanya.
 */
public class StatisticActivity extends DyntellAppCompatActivity implements FragmentRefresh{
	private ViewPager viewPager1;
	private List<Transaction> incomeList = new ArrayList<>();
	private List<Transaction> spendingList = new ArrayList<>();
	private List<Transaction> transactionList = new ArrayList<>();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
		init();
		actions();
	}

	@Override
	public void actions() {
		transactionList = new HandlerTransaction(StatisticActivity.this).getAllTransactions();
		for (Transaction e : transactionList ) {
			if(e.getIncome()){
				incomeList.add(e);
			} else {
				spendingList.add(e);
			}
		}
		viewPager1.setAdapter(new FragmentAdapter(getSupportFragmentManager(), incomeList, spendingList, StatisticActivity.this));

	}

	@Override
	public void init() {
		viewPager1 = (ViewPager) findViewById(R.id.pager);
	}

	@Override
	public void load() {

	}

	@Override
	public void refresh() {
		viewPager1.setAdapter(new FragmentAdapter(getSupportFragmentManager(), incomeList, spendingList, StatisticActivity.this));
	}
}
