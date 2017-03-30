package com.jossing.runboapple.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Jossing , Create on 2017/3/16
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private final static String CREATE_TABLE_USER =
            "CREATE TABLE User ( " +
            "    id               TEXT PRIMARY KEY " +
            "                          NOT NULL " +
            "                          UNIQUE, " +
            "    name             TEXT NOT NULL " +
            "                          CHECK (name LIKE ('____%') ), " +
            "    tel              TEXT UNIQUE, " +
            "    address          TEXT, " +
            "    secondaryAddress TEXT " +
            ");";

    private final static String CREATE_TABLE_APPLE =
            "CREATE TABLE Apple (" +
            "    id          TEXT    PRIMARY KEY " +
            "                        UNIQUE " +
            "                        NOT NULL, " +
            "    name        TEXT    CHECK (name LIKE ('__%') ) " +
            "                        NOT NULL, " +
            "    quality     TEXT    NOT NULL " +
            "                        CHECK (quality = 'A' OR " +
            "                               quality = 'B' OR " +
            "                               quality = 'C' OR " +
            "                               quality = 'D'), " +
            "    address     TEXT, " +
            "    description TEXT, " +
            "    count       INTEGER DEFAULT (0) " +
            "                        CHECK (count > 0) " +
            ");";

    private final static String CREATE_TABLE_ORDER =
            "CREATE TABLE [Order] ( " +
            "    id      TEXT    PRIMARY KEY " +
            "                    UNIQUE " +
            "                    NOT NULL, " +
            "    userId  TEXT    REFERENCES User (id) " +
            "                    NOT NULL, " +
            "    goodsId TEXT    REFERENCES Apple (id) " +
            "                    NOT NULL, " +
            "    count   INTEGER DEFAULT (0) " +
            "                    CHECK (count > 0)  " +
            ");";

    private final static String CREATE_TABLE_TRADE =
            "CREATE TABLE Trade ( " +
            "    id      TEXT PRIMARY KEY " +
            "                 UNIQUE " +
            "                 NOT NULL, " +
            "    orderId TEXT REFERENCES [Order] (id) " +
            "                 NOT NULL, " +
            "    info    TEXT CHECK (info = 'A' OR " +
            "                        info = 'B' OR " +
            "                        info = 'C' OR " +
            "                        info = 'D') " +
            "                 NOT NULL " +
            ");";

    public MyDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户信息表
        db.execSQL(CREATE_TABLE_USER);
        // 创建商品信息表
        db.execSQL(CREATE_TABLE_APPLE);
        // 创建用户采购信息表
        db.execSQL(CREATE_TABLE_ORDER);
        // 创建交易信息表
        db.execSQL(CREATE_TABLE_TRADE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // 外键支持必须在运行时打开
        if(!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

}
