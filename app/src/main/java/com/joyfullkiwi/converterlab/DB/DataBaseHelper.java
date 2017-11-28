package com.joyfullkiwi.converterlab.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "db_financeua";

    private static final int DB_VERSION = 1;
    public static final String PRIMARY_KEY = "_id";

    //organizations table
    public static final String ORGANIZATIONS_TABLE_NAME = "organizations";

    public static final String ORGANIZATION_ID = "org_id";
    public static final String ORGANIZATION_TITLE = "title";
    public static final String ORGANIZATION_REGION_ID = "region_id";
    public static final String ORGANIZATION_CITY_ID = "city_id";
    public static final String ORGANIZATION_PHONE = "phone";
    public static final String ORGANIZATION_ADDRESS = "address";
    public static final String ORGANIZATION_LINK = "link";

    private static final String ORGANIZATIONS_TABLE_CREATE =
            "CREATE TABLE " + ORGANIZATIONS_TABLE_NAME + " (" +
                    PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ORGANIZATION_ID         + " TEXT, " +
                    ORGANIZATION_TITLE      + " TEXT, " +
                    ORGANIZATION_REGION_ID  + " TEXT, " +
                    ORGANIZATION_CITY_ID    + " TEXT, " +
                    ORGANIZATION_PHONE      + " TEXT, " +
                    ORGANIZATION_ADDRESS    + " TEXT, " +
                    ORGANIZATION_LINK       + " TEXT);";

    //regions table
    public static final String REGIONS_TABLE_NAME = "regions";
    public static final String REGION_ID = "region_id";
    public static final String REGION_TITLE = "title";

    private static final String REGIONS_TABLE_CREATE =
            "CREATE TABLE " + REGIONS_TABLE_NAME + " (" +
                    PRIMARY_KEY         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    REGION_ID           + " TEXT, " +
                    REGION_TITLE        + " TEXT);";


    //cities table
    public static final String CITIES_TABLE_NAME = "cities";

    public static final String CITY_ID = "city_id";
    public static final String CITY_TITLE = "title";

    private static final String CITIES_TABLE_CREATE =
            "CREATE TABLE " + CITIES_TABLE_NAME + " (" +
                    PRIMARY_KEY     + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CITY_ID         + " TEXT, " +
                    CITY_TITLE      + " TEXT);";


    //currencies table
    public static final String CURRENCIES_TABLE_NAME  = "currencies";
    public static final String CURRENCY_ABBR  = "abbreviation";//"USD":
    //"USD":"доллары США", abbreviation_name
    public static final String CURRENCY_NAME = "name";//"доллары США",
    private static final String CURRENCIES_TABLE_CREATE =
            "CREATE TABLE " + CURRENCIES_TABLE_NAME + " (" +
                    PRIMARY_KEY          + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CURRENCY_ABBR        + " TEXT, " +
                    CURRENCY_NAME        + " TEXT);";


    //CURRENCIES for ORGANIZATIONS
    public static final String CURR_ORG_TABLE_NAME  = "currencies_org";
    public static final String CURRORG_ID = "org_id";
    public static final String CURRORG_ABBR = "abb";
    public static final String CURRORG_ASK = "ask";
    public static final String CURRORG_BID = "bid";

    private static final String CURR_ORG_TABLE_CREATE =
            "CREATE TABLE " + CURR_ORG_TABLE_NAME + " (" +
                    PRIMARY_KEY              + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CURRORG_ID               + " TEXT, " +
                    CURRORG_ABBR             + " TEXT, " +
                    CURRORG_ASK              + " TEXT, " +
                    CURRORG_BID              + " TEXT); " ;


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ORGANIZATIONS_TABLE_CREATE);
        db.execSQL(REGIONS_TABLE_CREATE);
        db.execSQL(CITIES_TABLE_CREATE);
        db.execSQL(CURRENCIES_TABLE_CREATE);
        db.execSQL(CURR_ORG_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


}
