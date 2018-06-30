package net.tecgurus.app.tecgurusapp.db.helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import net.tecgurus.app.tecgurusapp.db.beans.UserBean;
import net.tecgurus.app.tecgurusapp.db.core.DataSQLite;

public class UserHelper {
    //region Variables
    //region DATABASE FIELDS
    public static final String TABLE_NAME = "user";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_LASTNAME = "last_name";
    public static final String FIELD_ADDRESS = "address";
    //endregion
    //region Globale Variables
    private DataSQLite mHelper;
    //endregion
    //endregion

    //region newInstance
    public static UserHelper getInstance(Context context) {
        return new UserHelper(context);
    }
    //endregion

    //region Consctructor
    private UserHelper(Context context){
        mHelper = DataSQLite.getInstance(context);
    }
    //endregion

    //region Operations
    public void save(UserBean userBean){

        SQLiteDatabase writableDatabase = mHelper.getWritableDatabase();
        try{
            writableDatabase.insertOrThrow(TABLE_NAME, null, userBean.toContentValues());
        }catch (SQLiteConstraintException e){
            Log.e(UserHelper.class.getSimpleName(), String.format("El usuario %s ya existe", userBean.getUsername()));
        }catch (IllegalStateException e){
            e.printStackTrace();
        }

    }

    public UserBean getUserByUsername(String username){
        UserBean userBean = null;
        SQLiteDatabase readableDatabase = mHelper.getReadableDatabase();
        @SuppressLint("Recycle") final Cursor cursor = readableDatabase.query(TABLE_NAME, null,
                FIELD_USERNAME + "=?",  new String[]{username}, null, null, null);
        try{
            if (cursor != null && cursor.moveToNext()){
                userBean = new UserBean(cursor);
            }
        }catch (SQLiteConstraintException | IllegalStateException e){
            e.printStackTrace();
        }finally {
            if (cursor != null)
                cursor.close();
        }
        return userBean;
    }

    public void update(UserBean userBean){
        SQLiteDatabase writableDatabase = mHelper.getWritableDatabase();
        ContentValues userValues = userBean.toContentValues();
        userValues.remove(FIELD_USERNAME);
        try{
            writableDatabase.update(TABLE_NAME, userValues,FIELD_USERNAME + "=?",
                    new String[]{userBean.getUsername()});
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    public void deleteUserByUsername(String username){
        SQLiteDatabase writableDatabase = mHelper.getWritableDatabase();
        try {
            writableDatabase.delete(TABLE_NAME, FIELD_USERNAME + "=?", new String[]{username});
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }
    //endregion

}
