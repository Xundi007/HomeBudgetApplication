package com.example.sanya.homebudgetapplication.data.handler;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import com.example.sanya.homebudgetapplication.activities.ItemActivity;
import com.example.sanya.homebudgetapplication.activities.SelectorActivity;
import com.example.sanya.homebudgetapplication.data.ConstantsDatabase;
import com.example.sanya.homebudgetapplication.data.Database;
import com.example.sanya.homebudgetapplication.model.Item;

/**
 * Created by Sanya.
 */
public class HandlerItem extends Handler implements Parcelable{
	private int userId;

	public HandlerItem(Context context) {
		super(context, ConstantsDatabase.TABLE_ITEM);
	}

	public HandlerItem(Context context, int userId) {
		super(context, ConstantsDatabase.TABLE_TRANSACTION);
		this.userId = userId;
	}

//	public List<User> getAllUsers() {
//		List<User> userList = new ArrayList<>();
//
//		SQLiteDatabase db = Database.getDatabase(context);
//		Cursor cursor = db.rawQuery("SELECT " + ConstantsDatabase.KEY_NAME + ", " + ConstantsDatabase.KEY_USER_PASSWORD + " FROM " + ConstantsDatabase.TABLE_USER, null);
//
//		if (cursor.moveToFirst()) {
//			do {
//				User user = new User();
//				user.setName(cursor.getString(0));
//				user.setPassword(cursor.getString(1));
//				userList.add(user);
//			} while (cursor.moveToNext());
//		}
//		cursor.close();
//		db.close();
//
//		return userList;
//	}

	public Item getItemById(int id) {
		Item item = null;
		SQLiteDatabase db = Database.getDatabase(context);
		String selectQuery = "SELECT " + ConstantsDatabase.KEY_ID + ", " + ConstantsDatabase.KEY_NAME + ", " + ConstantsDatabase.KEY_CATEGORY_ID + ", " + ConstantsDatabase.KEY_LAST_VALUE + ", " + ConstantsDatabase.KEY_IS_INCOME + " FROM " + ConstantsDatabase.TABLE_ITEM + " where " + ConstantsDatabase.KEY_ID + " = '" + id + "';";
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			item = new Item();
			item.setId(cursor.getInt(0));
			item.setName(cursor.getString(1));
			item.setCategoryId(cursor.getInt(2));
			item.setLastValue(cursor.getInt(3));
			if (cursor.getInt(4) == 1)
				item.setIncome(true);
			else
				item.setIncome(false);
		}
		cursor.close();
		db.close();
		return item;
	}

	public Item getItemByName(String name) {
		Item item = null;
		SQLiteDatabase db = Database.getDatabase(context);
		String selectQuery = "SELECT " + ConstantsDatabase.KEY_ID + ", " + ConstantsDatabase.KEY_NAME + ", " + ConstantsDatabase.KEY_CATEGORY_ID + ", " + ConstantsDatabase.KEY_LAST_VALUE + ", " + ConstantsDatabase.KEY_IS_INCOME + " FROM " + ConstantsDatabase.TABLE_ITEM + " where " + ConstantsDatabase.KEY_NAME + " = '" + name + "';";
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			item = new Item();
			item.setId(cursor.getInt(0));
			item.setName(cursor.getString(1));
			item.setCategoryId(cursor.getInt(2));
			item.setLastValue(cursor.getInt(3));
			if (cursor.getInt(4) == 1)
				item.setIncome(true);
			else
				item.setIncome(false);
		}
		cursor.close();
		db.close();
		return item;
	}

	public void addItem(String name, int categoryId, int lastValue, Boolean isIncome) {
		SQLiteDatabase db = Database.getDatabase(context);
		DatabaseUtils.InsertHelper ih = new DatabaseUtils.InsertHelper(db, ConstantsDatabase.TABLE_ITEM);
		db.beginTransaction();
		try {
			ih.prepareForInsert();
			ih.bind(2, name);
			ih.bind(3, categoryId);
			ih.bind(4, lastValue);
			ih.bind(5, isIncome);
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

	public void updateItem(Item item){
		SQLiteDatabase db = Database.getDatabase(context);
		db.execSQL("UPDATE " + ConstantsDatabase.TABLE_ITEM
				+ " SET " + ConstantsDatabase.KEY_NAME + " = '" + item.getName()
				+ "', " + ConstantsDatabase.KEY_CATEGORY_ID + " = '" + item.getCategoryId()
				+ "', " + ConstantsDatabase.KEY_LAST_VALUE + " = '" + item.getLastValue()
				+ "', " + ConstantsDatabase.KEY_IS_INCOME + " = '" + item.getIncome()
				+ "' WHERE " + ConstantsDatabase.KEY_ID + " = " + item.getId()
		);
		db.close();
	}

	public void deleteItem(int id) {
		SQLiteDatabase db = Database.getDatabase(context);
		db.beginTransaction();
		try {
			db.execSQL("DELETE FROM " + ConstantsDatabase.TABLE_ITEM + " where " + ConstantsDatabase.KEY_ID + " = " + id);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.setLockingEnabled(true);
			db.close();
		}
	}

	@Override
	public void setContext(Context context) {
		this.context = context;
		this.tableName = ConstantsDatabase.TABLE_ITEM;
	}

	@Override
	public void addNew(SelectorActivity selectorActivity, int isIncome, int userId) {
		selectorActivity.startActivity(new Intent(selectorActivity, ItemActivity.class).putExtra("KEY_USER", userId));

	}

	@Override
	public void showDetails(SelectorActivity selectorActivity, Integer id, int isIncome, int userId) {
		selectorActivity.startActivity(new Intent(selectorActivity, ItemActivity.class).putExtra("KEY_ID", id).putExtra("KEY_USER", userId));

	}

	@Override
	public void deleteById(Integer id) {
		deleteItem(id);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {

	}

	protected HandlerItem(Parcel in) {
	}

	public static final Parcelable.Creator<HandlerItem> CREATOR = new Parcelable.Creator<HandlerItem>() {
		@Override
		public HandlerItem createFromParcel(Parcel source) {
			return new HandlerItem(source);
		}

		@Override
		public HandlerItem[] newArray(int size) {
			return new HandlerItem[size];
		}
	};
}
