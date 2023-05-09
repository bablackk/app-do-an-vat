package edu.huflit.app_do_an_vat.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBName = "Userdata.db";

    public DBHelper(@Nullable Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        CreateDatabase(MyDB);
    }

    public void CreateDatabase(SQLiteDatabase MyDB) {
        String createTableCustomer = ("CREATE TABLE customer" +
                "(customer_username TEXT primary key," +
                " customer_password TEXT, " +
                "customer_name TEXT, "+
                "customer_phone_number TEXT)");
        String createTableFood = ("CREATE TABLE food" +
                "(food_id INTEGER primary key autoincrement," +
                "food_url TEXT," +
                "food_name TEXT," +
                "food_type TEXT," +
                "food_rate TEXT," +
                "food_price INTEGER," +
                "food_describe TEXT) ");
        String createTableTopping = ("CREATE TABLE topping" +
                "(topping_id INTEGER primary key autoincrement," +
                "topping_url TEXT," +
                "topping_name TEXT," +
                "topping_price INTEGER," +
                "topping_amount INTEGER) ");
        MyDB.execSQL(createTableCustomer);
        MyDB.execSQL(createTableFood);
        MyDB.execSQL(createTableTopping);
    }
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists customer");
        MyDB.execSQL("drop Table if exists food");
        MyDB.execSQL("drop Table if exists topping");

    }

    public boolean insertToppingData(Integer topping_id, String topping_url, String topping_name,int topping_price,int topping_amount) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("topping_id",topping_id);
        contentValues.put("topping_url",topping_url);
        contentValues.put("topping_name",topping_name);
        contentValues.put("topping_price",topping_price);
        contentValues.put("topping_amount",topping_amount);
        long result = MyDB.insert("topping" , null,contentValues);
        if(result == -1) return false;
        else
            return true;
    }

    public boolean insertFoodData(int food_id,String food_url, String product_name, int product_price,String product_rate,String product_type,String product_describe) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("food_id",food_id);
        contentValues.put("food_name",product_name);
        contentValues.put("food_url",food_url);
        contentValues.put("food_rate",product_rate);
        contentValues.put("food_type", product_type);
        contentValues.put("food_price",product_price);
        contentValues.put("food_describe",product_describe);
        long result = MyDB.insert("food" , null,contentValues);
        if(result == -1) return false;
        else
            return true;


    }
    public boolean updateFoodRate(int foodId, String newRate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("food_rate", newRate);
        String whereClause = "food_id=?";
        String[] whereArgs = new String[] { String.valueOf(foodId) };
        int rowsUpdated = db.update("food", values, whereClause, whereArgs);
        db.close();
        return rowsUpdated >0;
    }
    public boolean updateToppingAmount(int topping_id,int topping_amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("topping_amount", topping_amount);
        String whereClause = "topping_id=?";
        String[] whereArgs = new String[] { String.valueOf(topping_id) };
        int rowsUpdated = db.update("topping", values, whereClause, whereArgs);
        db.close();
        return rowsUpdated >0;
    }
    public boolean updateFoodData(int food_id,String food_url, String product_name, int product_price,String product_rate,String product_type,String product_describe) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("food_id",food_id);
        contentValues.put("food_name",product_name);
        contentValues.put("food_url",food_url);
        contentValues.put("food_rate",product_rate);
        contentValues.put("food_type", product_type);
        contentValues.put("food_price",product_price);
        contentValues.put("food_describe",product_describe);
        Cursor cursor  = MyDB.rawQuery("Select * from food where food_id = ?", new String[] {String.valueOf(food_id)});
        if(cursor.getCount() > 0 ) {
            long result = MyDB.update("food" , contentValues , "food_id=?", new String[] {String.valueOf(food_id)});
            if(result == -1) return false;
            else
                return true;
        }else return false;

    }
    public Boolean deleteFoodData(int food_id ) {
        SQLiteDatabase MyDB = this.getWritableDatabase();


        Cursor cursor  = MyDB.rawQuery("Select * from food where food_id = ?", new String[] {String.valueOf(food_id)});
        if(cursor.getCount() > 0 ) {
            long result = MyDB.delete("food"  , "food_id=?", new String[] {String.valueOf(food_id)});
            if(result == -1) return false;
            else
                return true;
        }else return false;

    }
    public Cursor getFoodData() {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor  = MyDB.rawQuery("Select * from food ",null    );
        return cursor;

    }
    public Cursor getTopping() {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor  = MyDB.rawQuery("Select * from topping ",null    );
        return cursor;

    }

}


