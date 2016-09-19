package com.example.sanya.homebudgetapplication.data.handler;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import com.example.sanya.homebudgetapplication.activities.CategoryActivity;
import com.example.sanya.homebudgetapplication.activities.SelectorActivity;
import com.example.sanya.homebudgetapplication.data.ConstantsDatabase;
import com.example.sanya.homebudgetapplication.data.Database;
import com.example.sanya.homebudgetapplication.model.Category;

/**
 * Created by Sanya.
 */
public class HandlerCategory extends Handler implements Parcelable{

	public HandlerCategory(Context context) {
		super(context, ConstantsDatabase.TABLE_CATEGORY);
	}

	public Category getCategoryByName(String name) {
		Category category = null;
		SQLiteDatabase db = Database.getDatabase(context);
		String selectQuery = "SELECT " + ConstantsDatabase.KEY_ID + ", " + ConstantsDatabase.KEY_NAME + " FROM " + ConstantsDatabase.TABLE_CATEGORY + " where " + ConstantsDatabase.KEY_NAME + " = '" + name + "';";
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			category = new Category();
			category.setId(cursor.getInt(0));
			category.setName(cursor.getString(1));
		}
		cursor.close();
		db.close();
		return category;
	}

	public Category getCategoryById(int id) {
		Category category = null;
		SQLiteDatabase db = Database.getDatabase(context);
		String selectQuery = "SELECT " + ConstantsDatabase.KEY_ID + ", " + ConstantsDatabase.KEY_NAME + " FROM " + ConstantsDatabase.TABLE_CATEGORY + " where " + ConstantsDatabase.KEY_ID + " = '" + id + "';";
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			category = new Category();
			category.setId(cursor.getInt(0));
			category.setName(cursor.getString(1));
		}
		cursor.close();
		db.close();
		return category;
	}

	public void addCategory(String name) {
		SQLiteDatabase db = Database.getDatabase(context);
		DatabaseUtils.InsertHelper ih = new DatabaseUtils.InsertHelper(db, ConstantsDatabase.TABLE_CATEGORY);
		db.beginTransaction();
		try {
			ih.prepareForInsert();
			ih.bind(2, name);
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

	public void updateCategory(Category category){
		SQLiteDatabase db = Database.getDatabase(context);
		db.execSQL("UPDATE " + ConstantsDatabase.TABLE_CATEGORY
				+ " SET " + ConstantsDatabase.KEY_NAME + " = '" + category.getName()
				+ "' WHERE " + ConstantsDatabase.KEY_ID + " = " + category.getId()
		);
		db.close();
	}

	public void deleteCategory(int id) {
		SQLiteDatabase db = Database.getDatabase(context);
		db.beginTransaction();
		try {
			db.execSQL("DELETE FROM " + ConstantsDatabase.TABLE_CATEGORY + " where " + ConstantsDatabase.KEY_ID + " = " + id);
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
		this.tableName = ConstantsDatabase.TABLE_CATEGORY;
	}

	@Override
	public void addNew(SelectorActivity selectorActivity, int isIncome, int userId) {
		selectorActivity.startActivity(new Intent(selectorActivity, CategoryActivity.class));
	}

	@Override
	public void showDetails(SelectorActivity selectorActivity, Integer id, int isIncome, int userId) {
		selectorActivity.startActivity(new Intent(selectorActivity, CategoryActivity.class).putExtra("KEY_ID", id));
	}

	@Override
	public void deleteById(Integer id) {
		deleteCategory(id);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {

	}

	protected HandlerCategory(Parcel in) {
	}

	public static final Parcelable.Creator<HandlerCategory> CREATOR = new Parcelable.Creator<HandlerCategory>() {
		@Override
		public HandlerCategory createFromParcel(Parcel source) {
			return new HandlerCategory(source);
		}

		@Override
		public HandlerCategory[] newArray(int size) {
			return new HandlerCategory[size];
		}
	};
}
