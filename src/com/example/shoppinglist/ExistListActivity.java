package com.example.shoppinglist;

import database.DBContactLevel.ListFields;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import database.DBCreationLevel;
import database.DataAccessLevel;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
/*
 * Класс Activity, которое отвечает за отобращение существующих списков покупок
 */
public class ExistListActivity extends Activity {

	final Context context = this;
	int rowId = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exist_list);
		
		DBCreationLevel dbhelper = new DBCreationLevel(context);
		SQLiteDatabase sqliteDB = dbhelper.getReadableDatabase();
		final Cursor c = sqliteDB.query(ListFields.LIST_TABLE_NAME, null, null, null, null, null, null);
		startManagingCursor(c);
		final String[] from = { ListFields.ListNamesColumns.LIST_TITLE };
		int[] to = new int[] { R.id.tvListTitle };
		
		final SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.exist_list_item,
				c, from, to);
		final ListView lvData = (ListView) findViewById(R.id.lvData);	
		lvData.setAdapter(adapter);
		
		// Обработчик единичного нажатия на элемент списка
		// При одиночном нажатии мы переходим на Activity, демонстрирующую текущее содержание выбраного списка
		lvData.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {

				Intent intent = new Intent(ExistListActivity.this, CreateListActivity.class);				
				intent.putExtra("_id", id);
				startActivity(intent);
				finish();
			}
		});
		
		// Обработчик долгого нажатия на элемент списка
		// При олгом нажатии перед пользователем выскакивает диалог, в котором ему предлагается выбрать
		// действие над списком - это либо "переименовать", либо "удалить"
		lvData.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {

				final CharSequence[] _items = { "Удалить список", "Переименовать" };
				AlertDialog.Builder _dlgMenu = new AlertDialog.Builder(ExistListActivity.this);
				_dlgMenu.setTitle("Выберите действие").setItems(_items,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int _item) {

								switch (_item) {
									case 0: {
										// Обработка нажатия "удалить"
										DBCreationLevel mainDB = new DBCreationLevel(context);
										SQLiteDatabase sqliteDB = mainDB.getReadableDatabase();
										DataAccessLevel.delete(getBaseContext(), adapter.getItemId(pos),ListFields.LIST_TABLE_NAME);
										
										final Cursor c = sqliteDB.query(ListFields.LIST_TABLE_NAME, null, null, null, null, null, null);
										adapter.changeCursor(c);
										
										mainDB.close();
										sqliteDB.close();
									}
										break;
									case 1: {
										// Обработка нажатия "переименовать"
										LayoutInflater _dlgLayout = LayoutInflater.from(context);
										View _vwEditMenu = _dlgLayout.inflate(R.layout.dlg_rename, null);
										AlertDialog.Builder _dlgBuilder = new AlertDialog.Builder(
												context);
										// 
										_dlgBuilder.setView(_vwEditMenu);
										final EditText _userInput = (EditText) _vwEditMenu.findViewById(R.id.edtTextDlgUserInput);
										
										_dlgBuilder.setTitle("Введине новое имя для списка");
										// 
										_dlgBuilder.setCancelable(false).setPositiveButton("OK",
												new DialogInterface.OnClickListener() {

													@Override
													public void onClick(DialogInterface dialog, int id) {

														DBCreationLevel mainDB = new DBCreationLevel(context);
														SQLiteDatabase sqliteDB = mainDB.getReadableDatabase();
														DataAccessLevel.updateList(getBaseContext(), _userInput
																.getText().toString(), adapter.getItemId(pos));		
														final Cursor c = sqliteDB.query(ListFields.LIST_TABLE_NAME, null, null, null, null, null, null);
														adapter.changeCursor(c);
														mainDB.close();
														sqliteDB.close();
													}
												}).setNegativeButton("Отмена",
												new DialogInterface.OnClickListener() {

													@Override
													public void onClick(DialogInterface dialog, int id) {

														dialog.cancel();
													}
												});
										// 
										AlertDialog alertDialog = _dlgBuilder.create();
										// 
										alertDialog.show();
									}
										break;
								}
							}
						});
				_dlgMenu.show();
				return true;
			}
		});
	}

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(Menu.NONE, 1, 0, "Вернуться в начало");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case 1: {
				Intent _moveActivity = new Intent(this, MainActivity.class);
				startActivity(_moveActivity);
				finish();
			}
				break;
		}
		return true;
	}

}
