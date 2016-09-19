package com.example.sanya.homebudgetapplication.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.sanya.homebudgetapplication.R;
import com.example.sanya.homebudgetapplication.fragments.FragmentMonth;
import com.example.sanya.homebudgetapplication.fragments.FragmentYear;
import com.example.sanya.homebudgetapplication.model.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanya.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
	private List<Transaction> incomeList = new ArrayList<>();
	private List<Transaction> spendingList = new ArrayList<>();
	Context context;
	FragmentRefresh fragmentRefresh;


	public FragmentAdapter(FragmentManager fm, List<Transaction> incomeList, List<Transaction> spendingList, Context context) {
		super(fm);
		this.incomeList = incomeList;
		this.spendingList = spendingList;
		this.context = context;
		this.fragmentRefresh = (FragmentRefresh) context;
	}

	@Override
	public Fragment getItem(int position) {
		if(position == 0)
			return new FragmentYear(incomeList, spendingList, fragmentRefresh);
		else
			return new FragmentMonth(incomeList, spendingList, position, fragmentRefresh);
	}

	@Override
	public int getCount() {
		return 13;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if(position == 0)
			return context.getString(R.string.statistics_year);
		else
			return "" + position + context.getString(R.string.statistics_months);
	}
}
