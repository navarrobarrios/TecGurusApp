package net.tecgurus.app.tecgurusapp.db.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.tecgurus.app.tecgurusapp.R;

public class DataSQLite extends SQLiteOpenHelper{

    //region Variables
    //region Static Variables
    private static final String DATA_BASE_NAME = "TEC_GURUS_DATABASE";
    private static final int DATA_BASE_VERSION = 1;
    //endregion
    //region Global Variables
    @SuppressLint("StaticFieldLeak")
    private static DataSQLite sDataSQlite;
    private Context mContext;
    //endregion
    //endregion

    //region getInstance
    public static DataSQLite getInstance(Context context){
        if (sDataSQlite == null)
            sDataSQlite = new DataSQLite(context);
        return sDataSQlite;
    }
    //endregion

    //region Constructor
    private DataSQLite(Context context){
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        this.mContext = context;
    }
    //endregion

    //region Variables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(mContext.getString(R.string.data_base_table_user));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
    //endregion
}
