package com.example.shoppinglist;

import database.DBContactLevel.ItemFields;
import database.DBCreationLevel;
import database.DataAccessLevel;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemLongClickListener;


public class CreateListActivity extends Activity {

	final Context context = this;
	int rowId = 0;
	// массив для заполнения спинера
	String[] data = {"кг", "шт", "гр", "м", "см", "упк", "пак", "бут"};
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_list);
		// получаем ID нашего списка и производим выборку из БД всех товаров которы в нем содержаться
		final long _listID = getIntent().getLongExtra("_id",-6);
		
		//подключаем спинер для выбора типа товара
		ArrayAdapter<String> adprSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
		adprSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		final Spinner spnr = (Spinner) findViewById(R.id.spItemType);
		spnr.setAdapter(adprSpinner);
		spnr.setPrompt("Выберите тип");
		spnr.setSelection(0);
		
		Button btnCreateItem = (Button) findViewById(R.id.btnCreateItem);
		final EditText edtItemName = (EditText) findViewById(R.id.edtItemName);
		final EditText edtItemCount = (EditText) findViewById(R.id.edtItemCount);


		DBCreationLevel mainDB = new DBCreationLevel(context);
		final SQLiteDatabase sqliteDB = mainDB.getReadableDatabase();
		
		final Cursor c = sqliteDB.query(ItemFields.ITEM_TABLE_NAME, null, 
				ItemFields.ItemNamesColumns.ITEM_LIST_ID + "=" + _listID, null, null, null, null);
		startManagingCursor(c);
		final String[] from = {
				BaseColumns._ID,
				ItemFields.ItemNamesColumns.ITEM_STATE,
				ItemFields.ItemNamesColumns.ITEM_NAME,
				ItemFields.ItemNamesColumns.ITEM_COUNT,
				ItemFields.ItemNamesColumns.ITEM_TYPE_COUNT,

				};
		int[] to = new int[] {0,R.id.cbState,R.id.tvItemTitle,R.id.tvItemCount,R.id.tvItemTypeCount};
		
		//Создание объекта совего адаптера
		final MyItemAdapter adapter = new MyItemAdapter(getApplicationContext(), R.layout.create_list_item,
				c, from, to);
		final ListView lvItem = (ListView) findViewById(R.id.lvItem);	
		
		
		lvItem.setAdapter(adapter);
		
		lvItem.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {

				final CharSequence[] _items = { "Удалить товар из списка"};
				AlertDialog.Builder _dlgMenu = new AlertDialog.Builder(CreateListActivity.this);
				_dlgMenu.setTitle("Выберите действие").setItems(_items,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int _item) {

								switch (_item) {
									case 0: {
										// Обработка нажатия "удалить"
										DBCreationLevel mainDB = new DBCreationLevel(context);
										SQLiteDatabase sqliteDB = mainDB.getReadableDatabase();
										DataAccessLevel.delete(getBaseContext(), adapter.getItemId(pos),ItemFields.ITEM_TABLE_NAME);
										
										final Cursor c = sqliteDB.query(ItemFields.ITEM_TABLE_NAME, null, ItemFields.ItemNamesColumns.ITEM_LIST_ID + "=" + _listID, null, null, null, null);
										adapter.changeCursor(c);
										
										mainDB.close();
										sqliteDB.close();
									}
										break;
									
								}
							}
						});
				_dlgMenu.show();
				return true;
			}
		});
		
		
		// создание обработчика на нажатие кнопки добавления нового товара в список

		OnClickListener oclBtn = new OnClickListener() {
			@Override
			public void onClick(View v) {

						if((!edtItemName.getText().toString().equals(""))&(!edtItemCount.getText().toString().equals("")) ){
							
							DataAccessLevel.writeItem(getBaseContext(), 
									'"'+edtItemName.getText().toString()+'"',
									Integer.parseInt(edtItemCount.getText().toString()),
									'"'+spnr.getSelectedItem().toString()+'"',
									_listID,
									1);
							DBCreationLevel mainDB = new DBCreationLevel(context);
							SQLiteDatabase sqliteDB = mainDB.getReadableDatabase();
							
							final Cursor updContent = sqliteDB.query(ItemFields.ITEM_TABLE_NAME, null, 
							ItemFields.ItemNamesColumns.ITEM_LIST_ID + "=" + _listID, null, null, null, null);
							adapter.changeCursor(updContent);

							mainDB.close();
							sqliteDB.close();
				}
			}
		};
		
		btnCreateItem.setOnClickListener(oclBtn);
	
	}

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	
		menu.add(Menu.NONE, 1, 0, "Вернуться в начало");
		menu.add(Menu.NONE, 2, 0, "Вернуться к выбору списков");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// создание пунктов меню
		Intent _moveActivity;
		switch (item.getItemId()) {
			case 1: {
				_moveActivity = new Intent(this, MainActivity.class);
				startActivity(_moveActivity);
				finish();
			}
				break;
			case 2: {
				_moveActivity = new Intent(this, ExistListActivity.class);
				startActivity(_moveActivity);
				finish();
			}
				break;
		}
		return true;
	}

}
