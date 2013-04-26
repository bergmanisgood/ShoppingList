package com.example.shoppinglist;

import database.DataAccessLevel;
import com.example.shoppinglist.MainActivity;
import com.example.shoppinglist.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/*
 * ����� �������� Activity
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final EditText edtNewListName = (EditText) findViewById(R.id.edtNewListName);
		Button btnCreateNewList = (Button) findViewById(R.id.btnCreateNewList);
		Button btnExistList = (Button) findViewById(R.id.btnExistList);
		
		// �������� �����������
		OnClickListener oclBtn = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �� id ����������� ������, ��������� ���� ����������
				switch (v.getId()) {
				case R.id.btnExistList:
					Intent _newListIntent = new Intent(MainActivity.this,ExistListActivity.class);
					startActivity(_newListIntent);
					finish();
				break;
				case R.id.btnCreateNewList:
						if(!edtNewListName.getText().toString().equals("") ){
							// ������� ����� ������ ������� � ��������� �� Activity, ��� ����������� ��� ��������� ������ �������
							DataAccessLevel.writeList(getBaseContext(), '"'+edtNewListName.getText().toString()+'"');
							Intent _viewListIntent = new Intent(MainActivity.this,ExistListActivity.class);
							startActivity(_viewListIntent);
							finish();
						}
				}
			}
		};
		
		btnExistList.setOnClickListener(oclBtn);
		btnCreateNewList.setOnClickListener(oclBtn);
	}

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	
		menu.add(Menu.NONE, 1, 0, "�����");
		return super.onCreateOptionsMenu(menu);
	}
    
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// �������� ������� ����
		Intent _moveActivity;
		switch (item.getItemId()) {
			case 1: {
				finish();
			}
				break;

		}
		return true;
	}

}
