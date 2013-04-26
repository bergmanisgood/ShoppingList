package database;

import android.provider.BaseColumns;

/*	
 * Класс используемый для взаимодействия 
 * 	Содержит в себе два статических класса, в которых описаны поля таблиц для записи в БД,	
 * 	методы get и set, которые будут захватывать данные, когда мы их будем запрашивать
 * 
 * */

public class DBContactLevel {

	public static class ListFields {

		// public static final String DEFAULT_SORT = NamesColumns.FNAME + " DESC";
		
		// Имя таблицы в БД, содержащей созданные списки покупок
		public static final String LIST_TABLE_NAME = "ShoppingList";
		
		//название списка покупок
		private String listTitle;
		
		// ID списка покупок
		private long listID;
		
		//
		// Ниже методы Get и Set для захвата данных из БД
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

		// Класс с именами полей таблицы ShoppingList в БД
		public class ListNamesColumns implements BaseColumns {

			public static final String LIST_TITLE = "listTitle";

		}
	}
	
	public static class ItemFields {


		// Имя таблицы в БД, содержащей созданные элементы списков покупок
		public static final String ITEM_TABLE_NAME = "ShoppingItem";
		
		// ID товара
		private long itemID;
		
		// название товара
		private String itemName;
		
		// количество товара
		private int itemCount;
		
		// тип, в котором мы измеряем количество товара( штуки, килограммы, граммы, упаковки и т.д.)
		private String itemTypeCount;
		
		// ID списка, которому принадлежит товар
		private long itemListID;
		
		// Флаг, отвечающий за состояние товара(куплен/не куплен) 
		// true - товар приобретен
		// false - товар ожидает своей участи
		private int itemState;
		
		//Ниже методы Get и Set для захвата данных из БД
		
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
		
		//Класс с именами полей таблицы ShoppingItem в БД
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
