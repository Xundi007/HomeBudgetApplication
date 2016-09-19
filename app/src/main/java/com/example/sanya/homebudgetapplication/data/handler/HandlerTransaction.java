package com.example.sanya.homebudgetapplication.data.handler;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import com.example.sanya.homebudgetapplication.activities.SelectorActivity;
import com.example.sanya.homebudgetapplication.activities.TransactionActivity;
import com.example.sanya.homebudgetapplication.data.ConstantsDatabase;
import com.example.sanya.homebudgetapplication.data.Database;
import com.example.sanya.homebudgetapplication.model.Entity;
import com.example.sanya.homebudgetapplication.model.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanya.
 */
public class HandlerTransaction extends Handler implements Parcelable {

	public HandlerTransaction(Context context) {
		super(context, ConstantsDatabase.TABLE_TRANSACTION);
	}


	public List<Transaction> getAllTransactions() {
		List<Transaction> transactionList = new ArrayList<>();

		SQLiteDatabase db = Database.getDatabase(context);
		Cursor cursor = db.rawQuery("SELECT " + ConstantsDatabase.KEY_ID + ", " + ConstantsDatabase.KEY_USER_ID + ", " + ConstantsDatabase.KEY_ITEM_ID + ", " + ConstantsDatabase.KEY_VALUE + ", " + ConstantsDatabase.KEY_CREATED_TIME + ", " + ConstantsDatabase.KEY_IS_INCOME + " FROM " + ConstantsDatabase.TABLE_TRANSACTION, null);
		if (cursor.moveToFirst()) {
			do {
				Transaction transaction = new Transaction();
				transaction.setId(cursor.getInt(0));
				transaction.setUserId(cursor.getInt(1));
				transaction.setItemId(cursor.getInt(2));
				transaction.setValue(cursor.getInt(3));
				transaction.setCreatedTime(cursor.getLong(4));
				if (cursor.getInt(5) == 1)
					transaction.setIncome(true);
				else
					transaction.setIncome(false);
				transactionList.add(transaction);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return transactionList;
	}

	@Override
	public List<Entity> getAllEntity() {
		List<Entity> entityList = new ArrayList<>();

		SQLiteDatabase db = Database.getDatabase(context);
		Cursor cursor = db.rawQuery("SELECT " + ConstantsDatabase.KEY_ID + ", " + ConstantsDatabase.KEY_VALUE + ", " + ConstantsDatabase.KEY_IS_INCOME + " FROM " + ConstantsDatabase.TABLE_TRANSACTION, null);

		if (cursor.moveToFirst()) {
			do {
				Entity entity = new Entity();
				entity.setId(cursor.getInt(0));
				if (cursor.getInt(2) == 1)
					entity.setName("BE - " + cursor.getInt(1));
				else
					entity.setName("KI - " + cursor.getInt(1));
				entityList.add(entity);

			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return entityList;
	}

	public List<Entity> getIncomeEntity() {
		List<Entity> entityList = new ArrayList<>();
		SQLiteDatabase db = Database.getDatabase(context);
		Cursor cursor = db.rawQuery("SELECT " + ConstantsDatabase.KEY_ID + ", " + ConstantsDatabase.KEY_VALUE + ", " + ConstantsDatabase.KEY_IS_INCOME + " FROM " + ConstantsDatabase.TABLE_TRANSACTION, null);
		if (cursor.moveToFirst()) {
			do {
				Entity entity = new Entity();
				entity.setId(cursor.getInt(0));
				if (cursor.getInt(2) == 1)
					entity.setName("BE - " + cursor.getInt(1));
				else
					entity.setName("KI - " + cursor.getInt(1));
				if (1 == cursor.getInt(2)) {
					entityList.add(entity);
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return entityList;
	}

	@Override
	public List<Entity> getSpendingEntity() {
		List<Entity> entityList = new ArrayList<>();
		SQLiteDatabase db = Database.getDatabase(context);
		Cursor cursor = db.rawQuery("SELECT " + ConstantsDatabase.KEY_ID + ", " + ConstantsDatabase.KEY_VALUE + ", " + ConstantsDatabase.KEY_IS_INCOME + " FROM " + ConstantsDatabase.TABLE_TRANSACTION, null);
		if (cursor.moveToFirst()) {
			do {
				Entity entity = new Entity();
				entity.setId(cursor.getInt(0));
				if (cursor.getInt(2) == 1)
					entity.setName("BE - " + cursor.getInt(1));
				else
					entity.setName("KI - " + cursor.getInt(1));
				if (0 == cursor.getInt(2)) {
					entityList.add(entity);
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return entityList;
	}

	public Transaction getTransactionById(int id) {
		Transaction transaction = null;
		SQLiteDatabase db = Database.getDatabase(context);
		String selectQuery = "SELECT " + ConstantsDatabase.KEY_ID + ", " + ConstantsDatabase.KEY_USER_ID + ", " + ConstantsDatabase.KEY_ITEM_ID + ", " + ConstantsDatabase.KEY_VALUE + ", " + ConstantsDatabase.KEY_CREATED_TIME + ", " + ConstantsDatabase.KEY_IS_INCOME + " FROM " + ConstantsDatabase.TABLE_TRANSACTION + " where " + ConstantsDatabase.KEY_ID + " = '" + id + "';";
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			transaction = new Transaction();
			transaction.setId(cursor.getInt(0));
			transaction.setUserId(cursor.getInt(1));
			transaction.setItemId(cursor.getInt(2));
			transaction.setValue(cursor.getInt(3));
			transaction.setCreatedTime(cursor.getLong(4));
			if (cursor.getInt(5) == 1)
				transaction.setIncome(true);
			else
				transaction.setIncome(false);
		}
		cursor.close();
		db.close();
		return transaction;
	}

	public void addTransaction(int userId, int itemId, int value, long createdTime, Boolean isIncome) {
		SQLiteDatabase db = Database.getDatabase(context);
		DatabaseUtils.InsertHelper ih = new DatabaseUtils.InsertHelper(db, ConstantsDatabase.TABLE_TRANSACTION);
		db.beginTransaction();
		try {
			ih.prepareForInsert();
			ih.bind(2, userId);
			ih.bind(3, itemId);
			ih.bind(4, value);
			ih.bind(5, createdTime);
			if (isIncome)
				ih.bind(6, 1);
			else
				ih.bind(6, 0);
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

	public void updateTransaction(Transaction transaction) {
		SQLiteDatabase db = Database.getDatabase(context);
		db.execSQL("UPDATE " + ConstantsDatabase.TABLE_TRANSACTION
				+ " SET " + ConstantsDatabase.KEY_USER_ID + " = '" + transaction.getUserId()
				+ "', " + ConstantsDatabase.KEY_ITEM_ID + " = '" + transaction.getItemId()
				+ "', " + ConstantsDatabase.KEY_VALUE + " = '" + transaction.getValue()
				+ "', " + ConstantsDatabase.KEY_CREATED_TIME + " = '" + transaction.getCreatedTime()
				+ "', " + ConstantsDatabase.KEY_IS_INCOME + " = '" + transaction.getIncome()
				+ "' WHERE " + ConstantsDatabase.KEY_ID + " = " + transaction.getId()
		);
		db.close();
	}

	public void deleteTransaction(int id) {
		SQLiteDatabase db = Database.getDatabase(context);
		db.beginTransaction();
		try {
			db.execSQL("DELETE FROM " + ConstantsDatabase.TABLE_TRANSACTION + " where " + ConstantsDatabase.KEY_ID + " = " + id);
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
		this.tableName = ConstantsDatabase.TABLE_TRANSACTION;
	}

	@Override
	public void addNew(SelectorActivity selectorActivity, int isIncome, int userId) {
		selectorActivity.startActivity(new Intent(selectorActivity, TransactionActivity.class).putExtra("KEY_USER", userId).putExtra("KEY_IS_INCOME", isIncome));
	}

	@Override
	public void showDetails(SelectorActivity selectorActivity, Integer id, int isIncome, int userId) {
		selectorActivity.startActivity(new Intent(selectorActivity, TransactionActivity.class).putExtra("KEY_ID", id).putExtra("KEY_USER", userId).putExtra("KEY_IS_INCOME", isIncome));
	}

	@Override
	public void deleteById(Integer id) {
		deleteTransaction(id);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {

	}

	protected HandlerTransaction(Parcel in) {
	}

	public static final Parcelable.Creator<HandlerTransaction> CREATOR = new Parcelable.Creator<HandlerTransaction>() {
		@Override
		public HandlerTransaction createFromParcel(Parcel source) {
			return new HandlerTransaction(source);
		}

		@Override
		public HandlerTransaction[] newArray(int size) {
			return new HandlerTransaction[size];
		}
	};
}
