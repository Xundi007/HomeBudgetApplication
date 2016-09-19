package com.example.sanya.homebudgetapplication.data.handler;

import android.content.Context;
import com.example.sanya.homebudgetapplication.activities.SelectorActivity;
import com.example.sanya.homebudgetapplication.model.Entity;

import java.util.List;

/**
 * Created by Sanya.
 */
public interface Selectable {
	List<Entity> getAllEntity();
	List<Entity> getIncomeEntity();
	List<Entity> getSpendingEntity();

	void setContext(Context context);

	void addNew(SelectorActivity selectorActivity, int isIncome, int userId);

	void showDetails(SelectorActivity selectorActivity, Integer id, int isIncome, int userId);

	void deleteById(Integer id);
}
