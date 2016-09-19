package com.example.sanya.homebudgetapplication.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.sanya.homebudgetapplication.R;
import com.example.sanya.homebudgetapplication.model.Transaction;
import com.example.sanya.homebudgetapplication.util.FragmentRefresh;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Sanya.
 */
@SuppressLint("ValidFragment")
public class FragmentMonth extends Fragment {
	private List<Transaction> incomeList = new ArrayList<>();
	private List<Transaction> spendingList = new ArrayList<>();
	private FragmentRefresh fragmentRefresh;
	private int position;
	private long incomeSum = 0;
	private long spendingSum = 0;
	private long diff = 0;

	public FragmentMonth(List<Transaction> incomeList, List<Transaction> spendingList, int position, FragmentRefresh fragmentRefresh){
		this.incomeList = incomeList;
		this.spendingList = spendingList;
		this.position = position;
		this.fragmentRefresh = fragmentRefresh;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout, null);

		load();
		((TextView)view.findViewById(R.id.txt_sum_income)).setText(getString(R.string.sum_income) + incomeSum);
		((TextView)view.findViewById(R.id.txt_sum_spending)).setText(getString(R.string.sum_spending) + spendingSum);
		((TextView)view.findViewById(R.id.txt_diff)).setText(getString(R.string.diff) + diff);
		return view;
	}

	private void load(){
		incomeSum = 0;
		spendingSum = 0;
		diff = 0;

		for (Transaction e : incomeList ) {
			Date date1 = new Date(e.getCreatedTime());
			Date after = new Date(date1.getYear(), position-1, 1);
			Date before = new Date(date1.getYear(), position, 1);
			if(date1.after(after) && date1.before(before))
				incomeSum += e.getValue();
		}
		for (Transaction e : spendingList ) {
			Date date1 = new Date(e.getCreatedTime());
			Date after = new Date(date1.getYear(), position-1, 1);
			Date before = new Date(date1.getYear(), position, 1);
			if(date1.after(after) && date1.before(before))
				spendingSum += e.getValue();
		}
		diff = incomeSum - spendingSum;
	}
}
