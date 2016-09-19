package com.example.sanya.homebudgetapplication.data;

/**
 * Created by Sanya.
 */
public class ConstantsDatabase {
	public static final String DATABASE_NAME = "Database";
	public static final int DATABASE_VERSION = 11;

	public static final String TABLE_CATEGORY = "Category";
	public static final String TABLE_USER = "User";
	public static final String TABLE_ITEM = "Item";
	public static final String TABLE_TRANSACTION = "Transact";

	public static final String KEY_ID = "Id";
	public static final String KEY_NAME = "Name";
	public static final String KEY_USER_PASSWORD = "Password";
	public static final String KEY_CATEGORY_ID = "CategoryId";
	public static final String KEY_LAST_VALUE = "LastValue";
	public static final String KEY_USER_ID = "UserId";
	public static final String KEY_ITEM_ID = "ItemId";
	public static final String KEY_VALUE = "Value";
	public static final String KEY_CREATED_TIME = "CreatedTime";
	public static final String KEY_IS_INCOME = "IsIncome";


	public static final String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
			+ KEY_ID + " INTEGER PRIMARY KEY, "
			+ KEY_NAME + " TEXT )";

	public static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
			+ KEY_ID + " INTEGER PRIMARY KEY, "
			+ KEY_NAME + " TEXT , "
			+ KEY_USER_PASSWORD + " TEXT )";

	public static final String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEM + "("
			+ KEY_ID + " INTEGER PRIMARY KEY, "
			+ KEY_NAME + " TEXT , "
			+ KEY_CATEGORY_ID + " INTEGER , "
			+ KEY_LAST_VALUE + " INTEGER , "
			+ KEY_IS_INCOME + " BOOLEAN )";

	public static final String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_TRANSACTION + "("
			+ KEY_ID + " INTEGER PRIMARY KEY, "
			+ KEY_USER_ID + " INTEGER , "
			+ KEY_ITEM_ID + " INTEGER , "
			+ KEY_VALUE + " INTEGER , "
			+ KEY_CREATED_TIME + " INTEGER , "
			+ KEY_IS_INCOME + " BOOLEAN )";
}
