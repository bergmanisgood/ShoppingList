package database;


import database.DBContactLevel.ItemFields;
import database.DBContactLevel.ListFields;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/*
 * 
 * Класс создающий, редактирующий и удаляющий БД
 * 
 *   */
public class DBCreationLevel extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "db.db";
	private static final int DATABASE_VERSION = 1;
	
	// Вспомогательные вещи, необходимые для ведения лога работы БД
	private static final String DEBUG_TAG = DBCreationLevel.class.getSimpleName();
	private static final boolean LOGV = false;

	public DBCreationLevel(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/*
	 * 
	 * Удаление таблиц из БД
	 * 
	 */
	public void dropTables(SQLiteDatabase db) {

		if (LOGV) {
			Log.d(DEBUG_TAG, "onDropTables called");
		}
		db.execSQL("DROP TABLE IF EXISTS " + ItemFields.ITEM_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + ListFields.LIST_TABLE_NAME);
	}
	
	
	/*
	 * 
	 * Создание таблиц в БД	
	 * 
	 * (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {

		if (LOGV) {
			Log.v(DEBUG_TAG, "onCreate()");
		}
		
		// ShoppingList
		db.execSQL("CREATE TABLE " + ListFields.LIST_TABLE_NAME + " (" 
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " 
				+ ListFields.ListNamesColumns.LIST_TITLE + " TEXT NOT NULL );");
		
		// ShoppingItem
		db.execSQL("CREATE TABLE " + ItemFields.ITEM_TABLE_NAME + " (" 
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " 
				+ ItemFields.ItemNamesColumns.ITEM_NAME + " TEXT NOT NULL, " 
				+ ItemFields.ItemNamesColumns.ITEM_COUNT + " INTEGER NOT NULL, "
				+ ItemFields.ItemNamesColumns.ITEM_TYPE_COUNT + " TEXT NOT NULL, "
				+ ItemFields.ItemNamesColumns.ITEM_LIST_ID + " INTEGER NOT NULL, " 
				+ ItemFields.ItemNamesColumns.ITEM_STATE + " INTEGER NOT NULL );");
	}
	
	/*
	 * 
	 * Обновление таблицы БД
	 * 
	 * (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.d(DEBUG_TAG, "onUpgrade called");
	}
}
