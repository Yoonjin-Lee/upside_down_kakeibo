package com.example.kakeibo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class IncomeDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "notepad_kot.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "create table if not exists noteData(" +
                "_id integer PRIMARY KEY autoincrement," +
                "title text," +
                "content text," +
                "time text," +
                "color text)"

        // !!는 null이 아님을 명시
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS noteData")
        }
    }
}