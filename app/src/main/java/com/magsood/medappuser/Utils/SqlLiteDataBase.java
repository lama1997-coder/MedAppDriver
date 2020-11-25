package com.magsood.medappuser.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.magsood.medappuser.Model.ModelCart;

import java.util.ArrayList;

public class SqlLiteDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "easylife.db";
    private static final int DATABASE_VERSION = 1;

    //table name
    private static final String TABLE_CART = "table_cart";
    private static final String TABLE_CARD = "table_card";
    private static final String TABLE_REC = "table_rec";
    private static final String TABLE_CART_ORDER = "cart_order";


    public SqlLiteDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        table card
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CART + "" +
                "(id VARCHAR(255) " +
                ",name VARCHAR(255)" +
                ",price VARCHAR(255)" +
                ",pharmacyName VARCHAR(255)" +
                ",pharmacyAddress VARCHAR(255)" +
                ",pharmacyLat VARCHAR(255)" +
                ",pharmacyLong VARCHAR(255)" +
                ",pharmacyID VARCHAR(255)" +
                ",medicineID VARCHAR(255)" +
                ",amount INTEGER " +
                ",created_at timestamp ,updated_at timestamp)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REC);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_ORDER);
        onCreate(db);
    }


    //add card
    public boolean AddToCart(ModelCart modelCart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", modelCart.getId());
        contentValues.put("name", modelCart.getName());
        contentValues.put("price", modelCart.getPrice());
        contentValues.put("pharmacyName", modelCart.getPharmacyName());
        contentValues.put("pharmacyAddress", modelCart.getPharmacyAddress());
        contentValues.put("pharmacyLat", modelCart.getPharmacyLat());
        contentValues.put("pharmacyLong", modelCart.getPharmacyLong());
        contentValues.put("pharmacyID", modelCart.getPharmacyID());
        contentValues.put("medicineID", modelCart.getMedicineID());

//        modelCart.setAmount(1);


        String query = "SELECT * FROM "+TABLE_CART+" where medicineID=='"+modelCart.getMedicineID()+"'";
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        c.moveToFirst();
        if (!c.isAfterLast()) {
            ContentValues values = new ContentValues();
            values.put("amount", Integer.parseInt(c.getString(c.getColumnIndex("amount")))+1);

            database.update(TABLE_CART, values, "medicineID = ?", new String[] {String.valueOf(modelCart.getMedicineID())} );

            database.close();
            return true;
        }else{
            contentValues.put("amount", 1);
            long result1 = db.insert(TABLE_CART, null, contentValues);
            if (result1 == -1) {
                return false;
            } else {
                return true;
            }
        }


    }
    //add receiver

    public ArrayList<ModelCart> GetAllCarts() {
        String query = "SELECT * FROM "+TABLE_CART+" ";

        ArrayList<ModelCart> cardModels = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                ModelCart cardModel = new ModelCart();
                cardModel.setId(c.getString(c.getColumnIndex("id")));
                cardModel.setName(c.getString(c.getColumnIndex("name")));
                cardModel.setPrice(c.getString(c.getColumnIndex("price")));
                cardModel.setPharmacyName(c.getString(c.getColumnIndex("pharmacyName")));
                cardModel.setPharmacyAddress(c.getString(c.getColumnIndex("pharmacyAddress")));
                cardModel.setPharmacyLat(c.getString(c.getColumnIndex("pharmacyLat")));
                cardModel.setPharmacyLong(c.getString(c.getColumnIndex("pharmacyLat")));
                cardModel.setAmount(Integer.parseInt(c.getString(c.getColumnIndex("amount"))));
                cardModel.setMedicineID(c.getString(c.getColumnIndex("medicineID")));

                cardModels.add(cardModel);
            }
            c.close();
        }

        return cardModels;
    }




    //Delete row from table_card
    public boolean deleteRowFromCart(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CART, "id='" + id + "'", null) > 0;
    }

    //Delete all table data
    public boolean deleteAllTableData(String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table_name, null, null) > 0;
    }


    public boolean UpdateCard(String id, String card_name, String card_no, String month,String year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("card_name", card_name);
        contentValues.put("card_no", card_no);
        contentValues.put("month", month);
        contentValues.put("year", year);
        long result1 = db.update(TABLE_CARD, contentValues, "id='" + id + "'", null);
        if (result1 == -1) {
            return false;
        } else {
            return true;
        }
    }




}
