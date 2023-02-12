package com.layoutexample.Shopping_list_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import java.util.ArrayList;


    public class SqliteRepo implements Repository {
        private static final String TABLE_NAME = "Items";
        private SQLiteOpenHelper sqlite;

        private static SqliteRepo instance = null;
        public static SqliteRepo getInstance(Context context){
            if(instance == null)
                instance = new SqliteRepo(context);

            return instance;
        }
        private SqliteRepo(Context context){
            sqlite = DataBaseHelper.getInstance(context);
        }

        @Override
        public ItemModel findItemById(int id) {
            SQLiteDatabase db = sqlite.getReadableDatabase();
            String query = "SELECT * FROM items WHERE id = " + id;

            Cursor cursor = db.rawQuery(query, null);

            ItemModel listItem = cursor.moveToFirst()
                    ? new ItemModel()
                    .setId(cursor.getInt(0))
                    .setItem(cursor.getString(1))
                    .setIsChecked(cursor.getInt(2))
                    : null;

            cursor.close();
            return listItem;
        }


        @Override
        public ArrayList<ItemModel> findAllItems() {
            SQLiteDatabase db = sqlite.getReadableDatabase();
            ArrayList<ItemModel> listItems = new ArrayList<>();

            String query = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);

            while (cursor.moveToNext()) {
                ItemModel listItem = new ItemModel()
                        .setId(cursor.getInt(0))
                        .setItem(cursor.getString(1))
                        .setIsChecked(cursor.getInt(2));

                listItems.add(listItem);
            }
            cursor.close();
            return listItems;
        }

        @Override
        public void deleteItem(ItemModel itemModel) {
            SQLiteDatabase db = sqlite.getWritableDatabase();
            String[] args = {String.valueOf(itemModel.getId())};
            db.delete(TABLE_NAME, "id = ?", args);
            db.close();
        }

        @Override
        public void save(ItemModel listItem) {
            if(listItem.getId() == 0)
                insertItem(listItem);
            else
                updateItem(listItem);
        }

        @Override
        public void deleteAll() {
            SQLiteDatabase db = this.sqlite.getReadableDatabase();
            db.delete(TABLE_NAME,null,null);
            db.execSQL("delete FROM "+ TABLE_NAME);
            db.close();
        }


        private void insertItem(ItemModel listItem){
            SQLiteDatabase db = sqlite.getWritableDatabase();
            ContentValues c = getContentValues(listItem);
            db.insert(TABLE_NAME, null, c);
            db.close();
        }

        private void updateItem(ItemModel listItem){
            SQLiteDatabase db = sqlite.getWritableDatabase();

            ContentValues c = getContentValues(listItem);
            String[] whereArgs = getWhereArgs(listItem.getId());

            db.update(TABLE_NAME, c, "id = ?", whereArgs);
            db.close();
        }

        @NonNull
        private ContentValues getContentValues(ItemModel listItem) {

            ContentValues c = new ContentValues();
            c.put("Item", listItem.getItem());
            c.put("IsChecked", listItem.getIsChecked());
            return c;
        }

        @NonNull
        private String[] getWhereArgs(int id) {
            String[] whereArgs = {String.valueOf(id)};
            return whereArgs;
        }
    }

