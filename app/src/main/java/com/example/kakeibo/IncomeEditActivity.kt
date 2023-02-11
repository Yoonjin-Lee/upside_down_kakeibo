package com.example.kakeibo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kakeibo.IncomeDatabaseHelper
import com.example.kakeibo.R
import java.text.SimpleDateFormat
import java.util.*

class IncomeEditActivity : AppCompatActivity() {
    private lateinit var etvTitle: TextView
    private lateinit var etvContent: TextView
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private lateinit var dbHelper: IncomeDatabaseHelper
    private lateinit var database: SQLiteDatabase
    private lateinit var tableName: String

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income_edit)
        etvTitle = findViewById(R.id.tv_edit_history_content_box)
        etvContent = findViewById(R.id.tv_edit_history_money_box)
        btnSave = findViewById(R.id.btn_edit_history_done)
        btnCancel = findViewById(R.id.btn_edit_history_cancel)

        // 인텐트 받음
        val intent = intent
        val title = intent.extras?.getString("title")
        val content = intent.extras?.getString("content")
        val position = intent.extras?.getInt("position")

        etvTitle.text = title
        etvContent.text = content
        // 내용 작성

        btnSave.setOnClickListener {
            dbHelper = IncomeDatabaseHelper(this)
            database = dbHelper.writableDatabase
            tableName = "noteData"

            val sdfNow = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().time)
            val time = sdfNow.toString()
            val saveData = ContentValues()
            saveData.put("title", etvTitle.text.toString())
            saveData.put("content", etvContent.text.toString())
            saveData.put("time", time)

            database.update("noteData", saveData, "_id = ?", arrayOf(position.toString()))
            // database.update("noteData", contentValues, "_id=?", new String[] {String.valueOf(position)});
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnCancel.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

