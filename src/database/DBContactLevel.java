package database;

import android.provider.BaseColumns;

/*	
 * ����� ������������ ��� �������������� 
 * 	�������� � ���� ��� ����������� ������, � ������� ������� ���� ������ ��� ������ � ��,	
 * 	������ get � set, ������� ����� ����������� ������, ����� �� �� ����� �����������
 * 
 * */

public class DBContactLevel {

	public static class ListFields {

		// public static final String DEFAULT_SORT = NamesColumns.FNAME + " DESC";
		
		// ��� ������� � ��, ���������� ��������� ������ �������
		public static final String LIST_TABLE_NAME = "ShoppingList";
		
		//�������� ������ �������
		private String listTitle;
		
		// ID ������ �������
		private long listID;
		
		//
		// ���� ������ Get � Set ��� ������� ������ �� ��
		//
		
		//get
		public String getListTitle() {

			return listTitle;
		}

		public long getListID() {

			return listID;
		}
		
		//set
		public void setListTitle(String _name) {

			this.listTitle = _name;
		}

		public void setListID(long _id) {

			this.listID = _id;
		}

		// ����� � ������� ����� ������� ShoppingList � ��
		public class ListNamesColumns implements BaseColumns {

			public static final String LIST_TITLE = "listTitle";

		}
	}
	
	public static class ItemFields {


		// ��� ������� � ��, ���������� ��������� �������� ������� �������
		public static final String ITEM_TABLE_NAME = "ShoppingItem";
		
		// ID ������
		private long itemID;
		
		// �������� ������
		private String itemName;
		
		// ���������� ������
		private int itemCount;
		
		// ���, � ������� �� �������� ���������� ������( �����, ����������, ������, �������� � �.�.)
		private String itemTypeCount;
		
		// ID ������, �������� ����������� �����
		private long itemListID;
		
		// ����, ���������� �� ��������� ������(������/�� ������) 
		// true - ����� ����������
		// false - ����� ������� ����� ������
		private int itemState;
		
		//���� ������ Get � Set ��� ������� ������ �� ��
		
		//get
		public String getItemName() {

			return itemName;
		}

		public long getItemID() {

			return itemID;
		}

		public int getItemCount(){
			
			return itemCount;
		}
		
		public String getItemTypeCount(){
			
			return itemTypeCount;
		}
		
		public long getItemListID() {

			return itemListID;
		}
		
		public int getItemState(){
			
			return itemState;
		}
		
		//set
		public void setItemName(String _itemName) {

			this.itemName = _itemName;
		}

		public void setItemID(long _itemID) {

			this.itemID = _itemID;
		}
		
		public void setItemCount(int _itemCount){
			
			this.itemCount = _itemCount;
		}
		
		public void setItemTypeCount(String _itemTypeCount){
			
			this.itemTypeCount = _itemTypeCount;
		}

		public void setItemListID(long _itemListID){
			
			this.itemListID = _itemListID;
		}
		
		public void setItemState(int _itemState){
			
			this.itemState = _itemState;
		}
		
		//����� � ������� ����� ������� ShoppingItem � ��
		public class ItemNamesColumns implements BaseColumns {

			public static final String ITEM_NAME = "itemName";
			public static final String ITEM_ID = "itemID";
			public static final String ITEM_COUNT = "itemCount";
			public static final String ITEM_TYPE_COUNT = "itemTypeCount";
			public static final String ITEM_LIST_ID = "itemListID";
			public static final String ITEM_STATE = "itemState";

		}
	}
}
