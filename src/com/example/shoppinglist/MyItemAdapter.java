package com.example.shoppinglist;

import database.DBCreationLevel;
import database.DataAccessLevel;
import database.DBContactLevel.ItemFields;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/*
 * ����� ������� ������� ��������� ����������� ����������� SimpleCursorAdapter
 * �� ��� ���������� � ������� ������� ��� ���������� ������ � CheckBox
 */
public class MyItemAdapter extends SimpleCursorAdapter {
	
	private int layout;
	private Context ctx;

	public MyItemAdapter(Context _context, int _layout, Cursor _cursor, String[] _from,
			int[] _to) {
		  super(_context, _layout, _cursor, _from, _to);
		  layout = _layout;
		  ctx = _context;
	}
	
	// ����� ��������� ������ � view �� ������� ��������� ������
	 @Override
	 public void bindView(View view, Context _context, Cursor _cursor) {
	
		 final long base_id = Long.parseLong(_cursor.getString(_cursor.getColumnIndex(BaseColumns._ID)));
		 String title = _cursor.getString(_cursor.getColumnIndex(ItemFields.ItemNamesColumns.ITEM_NAME));
		 int count = Integer.parseInt(_cursor.getString(_cursor.getColumnIndex(ItemFields.ItemNamesColumns.ITEM_COUNT)));
		 String type_count = _cursor.getString(_cursor.getColumnIndex(ItemFields.ItemNamesColumns.ITEM_TYPE_COUNT));
		 long list_id = Long.parseLong(_cursor.getString(_cursor.getColumnIndex(ItemFields.ItemNamesColumns.ITEM_LIST_ID)));
		 int state = Integer.parseInt(_cursor.getString(_cursor.getColumnIndex(ItemFields.ItemNamesColumns.ITEM_STATE)));
		  
		 TextView tvItemTitle = (TextView) view.findViewById(R.id.tvItemTitle);
		 TextView tvItemCount = (TextView) view.findViewById(R.id.tvItemCount);
		 CheckBox cbState = (CheckBox) view.findViewById(R.id.cbState);
		  
		 tvItemTitle.setText(title);
		 tvItemCount.setText("���-��: " + count + " " + type_count);
		  
		  // ����������� �������� ����������
		 cbState.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			 public void onCheckedChanged(CompoundButton buttonView,
			 boolean isChecked) {
			 // ������ ������ ������ (� ������� ��� ���)
					DBCreationLevel mainDB = new DBCreationLevel(ctx);
					final SQLiteDatabase sqliteDB = mainDB.getReadableDatabase();
					if (buttonView.isChecked()){	
						DataAccessLevel.updateItem(ctx, 1, base_id);
					}
					else
					{
						DataAccessLevel.updateItem(ctx, 0, base_id);
					}
					mainDB.close();
					sqliteDB.close();
				 }
			});
		  cbState.setChecked(false);
		  if(state == 1){
			  cbState.setChecked(true);
		  }
	 }
	 
	 @Override
	 public View newView(Context _context, Cursor _cursor, ViewGroup parent) {
		 
		  LayoutInflater inflater = (LayoutInflater) _context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);
		  View view = inflater.inflate(layout, parent, false);
	  return view;
	 }

}
