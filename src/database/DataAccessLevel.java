package database;


import database.DBContactLevel.ItemFields;
import database.DBContactLevel.ListFields;
import database.DBContactLevel.ListFields.ListNamesColumns;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/*
 * Класс отвечающий за доступ и выборку данных непосредственно из БД
 * 
 */
public class DataAccessLevel {

	private static final boolean LOGV = false;
	private static int maxRowsInNames = -1;
	private static final String TAG = DataAccessLevel.class.getSimpleName();

	private DataAccessLevel() {

	}

	public static int getMaxRowsInNames() {

		return maxRowsInNames;
	}
	
	/*
	 * Метод возвращает все имеющиеся списки покупок из таблицы ShoppingList
	 */
	public static ArrayList<ListFields> readAllList(Context context) {

		ArrayList<ListFields> list = null;
		try {
			
			DBCreationLevel mainDB = new DBCreationLevel(context);
			SQLiteDatabase sqliteDB = mainDB.getReadableDatabase();
			String[] columnsToTake = { BaseColumns._ID, ListNamesColumns.LIST_TITLE };
			Cursor cursor = sqliteDB.query(ListFields.LIST_TABLE_NAME, columnsToTake, null, null, null, null,
					null);
			if (cursor.moveToFirst()) {
				list = new ArrayList<ListFields>();
			}
			while (cursor.moveToNext()) {
				ListFields oneRow = new ListFields();
				oneRow.setListID(cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID)));
				oneRow.setListTitle(cursor.getString(cursor.getColumnIndexOrThrow(ListNamesColumns.LIST_TITLE)));
				list.add(oneRow);
			}
			cursor.close();
			mainDB.close();
		} catch (Exception e) {
			Log.e(TAG, "Failed to select Names.", e);
		}
		return list;
	}

	public static void setMaxRowsInNames(int maxRowsInNames) {

		DataAccessLevel.maxRowsInNames = maxRowsInNames;
	}
	
	/*
	 * Метод, создающий запрос, который записывает данные в таблицу ShoppingList 
	 */
	public static void writeList(Context context, String _listTitle){

		try {

			DBCreationLevel mainDB = new DBCreationLevel(context);
			SQLiteDatabase sqliteDB = mainDB.getWritableDatabase();
			String _newQuery = null;
			int countRows = -1;

			Cursor cursor = sqliteDB.query(ListFields.LIST_TABLE_NAME, new String[] { "count(*)" }, null, null, null,
					null, null);
			if (cursor.moveToFirst()) {
				countRows = cursor.getInt(0);
				if (LOGV) {
					Log.v(TAG, "Count in Names table" + String.valueOf(countRows));
				}
			}
			cursor.close();
			if ((maxRowsInNames == -1) || (maxRowsInNames >= countRows)) {
				
				_newQuery = String.format("INSERT INTO %s (%s) VALUES (%s);",

						ListFields.LIST_TABLE_NAME,

						ListFields.ListNamesColumns.LIST_TITLE,

						_listTitle);
			}

			sqliteDB.execSQL(_newQuery);
			sqliteDB.close();
			mainDB.close();
		} catch (SQLiteException e) {
			Log.e(TAG, "Failed open rimes database. ", e);
		} catch (SQLException e) {
			Log.e(TAG, "Failed to insert Names. ", e);
		}
	}
	
	/*
	 * Универсальный метод удаляющий запись из таблицы по ее ID и названию
	 */
	public static void delete(Context _context, long _l, String _tbName) {

		try {
			DBCreationLevel mainDB = new DBCreationLevel(_context);
			SQLiteDatabase sqliteDB = mainDB.getWritableDatabase();
			
			sqliteDB.delete(_tbName, BaseColumns._ID  + " = " + _l, null);

			sqliteDB.close();
			mainDB.close();		
		} catch (SQLiteException e) {
			Log.e(TAG, "Failed open rimes database. ", e);
		} catch (SQLException e) {
			Log.e(TAG, "Failed to insert Names. ", e);
		}
	}
	
	/*
	 * Метод, для обновления значения имени списка при переименовывании
	 */
	public static void updateList(Context _context, String _newValue, long l) {

		try {
			DBCreationLevel mainDB = new DBCreationLevel(_context);
			SQLiteDatabase sqliteDB = mainDB.getWritableDatabase();
			String _newQuery = null;
			int countRows = -1;
			Cursor cursor = sqliteDB.query(ListFields.LIST_TABLE_NAME, new String[] { "count(*)" }, null, null, null,
					null, null);
			if (cursor.moveToFirst()) {
				countRows = cursor.getInt(0);
				if (LOGV) {
					Log.v(TAG, "Count in Names table" + String.valueOf(countRows));
				}
			}
			cursor.close();
			_newQuery = String.format("UPDATE " + ListFields.LIST_TABLE_NAME + " SET " + ListFields.ListNamesColumns.LIST_TITLE
					+ " = '" + _newValue + "' WHERE " + BaseColumns._ID + " = " + l);
			Log.d("", "" + _newQuery);
			sqliteDB.execSQL(_newQuery);
			sqliteDB.close();
			mainDB.close();
		} catch (SQLiteException e) {
			Log.e(TAG, "Failed open database. ", e);
		} catch (SQLException e) {
			Log.e(TAG, "Failed to update Names. ", e);
		}
	}
	
	/*
	 * Метод, создающий запрос, который записывает данные в таблицу ShoppingItem 
	 */
	public static void writeItem(Context context, String _itemName, int _itemCount, 
			String _itemTypeCount, long _itemListID, int _itemState) {

		try {

			DBCreationLevel mainDB = new DBCreationLevel(context);
			SQLiteDatabase sqliteDB = mainDB.getWritableDatabase();
			String _newQuery = null;
			int countRows = -1;

			Cursor cursor = sqliteDB.query(ListFields.LIST_TABLE_NAME, new String[] { "count(*)" }, null, null, null,
					null, null);
			if (cursor.moveToFirst()) {
				countRows = cursor.getInt(0);
				if (LOGV) {
					Log.v(TAG, "Count in Names table" + String.valueOf(countRows));
				}
			}
			cursor.close();
			if ((maxRowsInNames == -1) || (maxRowsInNames >= countRows)) {
				
				_newQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (%s, %s, %s, %s, %s);",

						ItemFields.ITEM_TABLE_NAME,

						ItemFields.ItemNamesColumns.ITEM_NAME,
						ItemFields.ItemNamesColumns.ITEM_COUNT,
						ItemFields.ItemNamesColumns.ITEM_TYPE_COUNT,
						ItemFields.ItemNamesColumns.ITEM_LIST_ID,
						ItemFields.ItemNamesColumns.ITEM_STATE,

						_itemName,_itemCount,_itemTypeCount,_itemListID,_itemState);
			}

			sqliteDB.execSQL(_newQuery);
			sqliteDB.close();
			mainDB.close();
		} catch (SQLiteException e) {
			Log.e(TAG, "Failed open rimes database. ", e);
		} catch (SQLException e) {
			Log.e(TAG, "Failed to insert Names. ", e);
		}
	}
	
	
	/*
	 * Метод для обновления сведений о покупке товара
	 */
	public static void updateItem(Context _context, int _newValue, long l) {

		try {
			DBCreationLevel mainDB = new DBCreationLevel(_context);
			SQLiteDatabase sqliteDB = mainDB.getWritableDatabase();
				String strFilter = "_id=" + l ;
				ContentValues args = new ContentValues();
				args.put(ItemFields.ItemNamesColumns.ITEM_STATE, _newValue);
				sqliteDB.update(ItemFields.ITEM_TABLE_NAME, args, strFilter, null);
			sqliteDB.close();
			mainDB.close();
		} catch (SQLiteException e) {
			Log.e(TAG, "Failed open database. ", e);
		} catch (SQLException e) {
			Log.e(TAG, "Failed to update Names. ", e);
		}
	}

}