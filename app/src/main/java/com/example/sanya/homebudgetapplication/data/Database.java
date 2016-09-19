package com.example.sanya.homebudgetapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sanya.
 */
public class Database extends SQLiteOpenHelper {
	private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

	public Database(Context context) {
		super(context, ConstantsDatabase.DATABASE_NAME, null, ConstantsDatabase.DATABASE_VERSION);
	}

	public static SQLiteDatabase getDatabase(Context context) {
		return new Database(context).getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ConstantsDatabase.CREATE_CATEGORY_TABLE);
		db.execSQL(ConstantsDatabase.CREATE_USER_TABLE);
		db.execSQL(ConstantsDatabase.CREATE_ITEM_TABLE);
		db.execSQL(ConstantsDatabase.CREATE_TRANSACTION_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		dropTables(db);
		onCreate(db);
	}

	private void dropTables(SQLiteDatabase db) {
		db.execSQL(DROP_TABLE + ConstantsDatabase.TABLE_CATEGORY);
		db.execSQL(DROP_TABLE + ConstantsDatabase.TABLE_USER);
		db.execSQL(DROP_TABLE + ConstantsDatabase.TABLE_ITEM);
		db.execSQL(DROP_TABLE + ConstantsDatabase.TABLE_TRANSACTION);
	}
}
