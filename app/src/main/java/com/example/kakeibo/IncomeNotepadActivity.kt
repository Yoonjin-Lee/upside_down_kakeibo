package com.example.kakeibo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class IncomeNotepadActivity : AppCompatActivity() {
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private lateinit var etvTitle: EditText
    private lateinit var etvContent: EditText

    private lateinit var dbHelper: IncomeDatabaseHelper
    private lateinit var database: SQLiteDatabase
    private lateinit var tableName: String

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add_history_main)

        dbHelper = IncomeDatabaseHelper(this)
        database = dbHelper.writableDatabase
        tableName = "noteData"

        btnSave = findViewById(R.id.btn_add_history_main_big_done)
        btnSave = findViewById(R.id.btn_add_history_main_done)
        btnCancel = findViewById(R.id.btn_add_history_main_cancel)

        etvTitle = findViewById(R.id.tv_add_history_main_content_box)
        etvContent = findViewById(R.id.tv_add_history_main_money_box)
        btnSave.setOnClickListener {
            if (etvTitle.text.isEmpty() && etvContent.text.isEmpty()) {
                Toast.makeText(this, "작성한 내용이 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                val title = etvTitle.text.toString()
                val content = etvContent.text.toString()
                val sdfNow = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().time)
                val time = sdfNow.toString()

                val saveData = ContentValues()

                saveData.put("title", title)
                saveData.put("content", content)
                saveData.put("time", time)
                saveData.put("color", Random.nextInt(5).toString())

                database.insert(tableName, null, saveData)

                //Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, IncomeActivity::class.java))
                finish()
            }
        }

        btnCancel.setOnClickListener {
            startActivity(Intent(this, IncomeActivity::class.java))
        }
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(this, IncomeActivity::class.java))
    }
}