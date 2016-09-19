package com.example.sanya.homebudgetapplication.data.handler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.sanya.homebudgetapplication.data.ConstantsDatabase;
import com.example.sanya.homebudgetapplication.data.Database;
import com.example.sanya.homebudgetapplication.model.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanya.
 */
public abstract class Handler implements Selectable {
	protected Context context;
	protected String tableName;

	public Handler() {
	}

	public Handler(Context context, String tableName) {
		this.context = context;
		this.tableName = tableName;
	}

	protected List<Entity> getEntities(String query) {
		List<Entity> entityList = new ArrayList<>();

		SQLiteDatabase db = Database.getDatabase(context);
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				Entity entity = new Entity();
				entity.setId(cursor.getInt(0));
				entity.setName(cursor.getString(1));
				entityList.add(entity);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return entityList;
	}

	@Override
	public List<Entity> getAllEntity() {
		List<Entity> entityList = new ArrayList<>();

		SQLiteDatabase db = Database.getDatabase(context);
		Cursor cursor = db.rawQuery("SELECT " + ConstantsDatabase.KEY_ID + ", " + ConstantsDatabase.KEY_NAME + " FROM " + tableName, null);

		if (cursor.moveToFirst()) {
			do {
				Entity entity = new Entity();
				entity.setId(cursor.getInt(0));
				entity.setName(cursor.getString(1));
				entityList.add(entity);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return entityList;
	}

	@Override
	public List<Entity> getIncomeEntity() {
		//TODO
		return null;
	}

	@Override
	public List<Entity> getSpendingEntity() {
		//TODO
		return null;
	}

	@Override
	public abstract void setContext(Context context);
}
