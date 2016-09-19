package com.example.sanya.homebudgetapplication.data.handler;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import com.example.sanya.homebudgetapplication.activities.SelectorActivity;
import com.example.sanya.homebudgetapplication.data.ConstantsDatabase;
import com.example.sanya.homebudgetapplication.data.Database;
import com.example.sanya.homebudgetapplication.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanya.
 */
public class HandlerUser extends Handler{

	public HandlerUser(Context context) {
		super(context, ConstantsDatabase.TABLE_USER);
	}

	public List<String> getAllUserNames() {
		List<String> userNameList = new ArrayList<>();

		SQLiteDatabase db = Database.getDatabase(context);
		Cursor cursor = db.rawQuery("SELECT " + ConstantsDatabase.KEY_NAME + " FROM " + ConstantsDatabase.TABLE_USER, null);

		if (cursor.moveToFirst()) {
			do {
				userNameList.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return userNameList;
	}

	public User getUserByName(String name) {
		User user = null;
		SQLiteDatabase db = Database.getDatabase(context);
		String selectQuery = "SELECT " + ConstantsDatabase.KEY_ID + ", " + ConstantsDatabase.KEY_NAME + ", " + ConstantsDatabase.KEY_USER_PASSWORD + " FROM " + ConstantsDatabase.TABLE_USER + " where " + ConstantsDatabase.KEY_NAME + " = '" + name + "';";
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			user = new User();
			user.setId(cursor.getInt(0));
			user.setName(cursor.getString(1));
			user.setPassword(cursor.getString(2));
		}
		cursor.close();
		db.close();
		return user;
	}

	public User getUserById(int id) {
		User user = null;
		SQLiteDatabase db = Database.getDatabase(context);
		String selectQuery = "SELECT " + ConstantsDatabase.KEY_ID + ", " + ConstantsDatabase.KEY_NAME + ", " + ConstantsDatabase.KEY_USER_PASSWORD + " FROM " + ConstantsDatabase.TABLE_USER + " where " + ConstantsDatabase.KEY_ID + " = '" + id + "';";
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			user = new User();
			user.setId(cursor.getInt(0));
			user.setName(cursor.getString(1));
			user.setPassword(cursor.getString(2));
		}
		cursor.close();
		db.close();
		return user;
	}

	public void addUser(String name, String password) {
		SQLiteDatabase db = Database.getDatabase(context);
		DatabaseUtils.InsertHelper ih = new DatabaseUtils.InsertHelper(db, ConstantsDatabase.TABLE_USER);
		db.beginTransaction();
		try {
			ih.prepareForInsert();
			ih.bind(2, name);
			ih.bind(3, password);
			ih.execute();
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (ih != null) {
				ih.close();
			}
			db.setLockingEnabled(true);
			db.close();
		}
	}

	@Override
	public void setContext(Context context) {
		this.context = context;
		this.tableName = ConstantsDatabase.TABLE_USER;
	}

	@Override
	public void addNew(SelectorActivity selectorActivity, int isIncome, int userId) {
		//selectorActivity.startActivity(new Intent(selectorActivity, CompanyAdd.class));
	}

	@Override
	public void showDetails(SelectorActivity selectorActivity, Integer id, int isIncome, int userId) {
		//selectorActivity.startActivity(new Intent(selectorActivity, CompanyAdd.class).putExtra("KEY_ID", id));
	}

	@Override
	public void deleteById(Integer id) {
		//deleteCompany(id);
	}
}
