package com.example.kakeibo

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class IncomeNoteSearchAdapter(intent: Intent) : RecyclerView.Adapter<IncomeNoteSearchAdapter.ViewHolder>() {

    //    NoteSearchAdapter는 NoteAdapter와 기본 구조는 같음.
    var items = ArrayList<IncomeNoteList>()
//    var mIntent = intent
    val sql = intent.extras!!.getString("sql").toString()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView: View = inflater.inflate(R.layout.activity_income_recyclerview, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: IncomeNoteList?) {
        items.add(item!!)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView
        fun setItem(item: IncomeNoteList) {
            val title: TextView = view.findViewById(R.id.textView_title_recyclerView)
            val contents: TextView = view.findViewById(R.id.textView_contents_recyclerView)

            title.text = item.title
            contents.text = item.contents

            when(item.color) {
                "0" -> view.setBackgroundResource(R.drawable.rounding_content)
                else -> view.setBackgroundResource(R.drawable.rounding_content)
            }

            view.setOnClickListener { v ->
                val dbHelper = IncomeDatabaseHelper(v.context)
                val database = dbHelper.writableDatabase

                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    Log.d("로그", position.toString() + "클릭")
                    Log.d("로그1", sql)
                    val cursor = database.rawQuery(sql, null)
                    //                        전달받은 쿼리문을 저장한 문자열 변수 sql을 사용
                    cursor.move(position + 1)
                    val intent = Intent(v.context, IncomeEditActivity::class.java)
                    intent.putExtra("title", cursor.getString(1))
                    intent.putExtra("content", cursor.getString(2))
                    intent.putExtra("position", cursor.getInt(0))
                    val context = v.context
                    context.startActivity(intent)
                    (context as Activity).finish()
                }
            }


            view.setOnLongClickListener { v ->
                val database: SQLiteDatabase
                val context = v.context
                val dbHelper = IncomeDatabaseHelper(v.context)
                database = dbHelper.writableDatabase

                val alertDialog = AlertDialog.Builder(context)
                var cursor: Cursor
                alertDialog.setMessage("삭제하시겠습니까?")
                alertDialog.setPositiveButton("예") { _, _ ->
                    val position = adapterPosition
                    cursor = database.rawQuery(sql, null)
                    database.rawQuery(sql, null)
                    cursor.move(position + 1)
                    Toast.makeText(v.context, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    database.delete("noteData", "_id=?",
                        arrayOf(cursor.getInt(0).toString())
                    )
                    cursor.close()

                    (v.context as IncomeActivity).searchQuery((v.context as IncomeActivity).searchText)
                }

                alertDialog.setNegativeButton("취소") { _, _ -> }//취소 버튼
                alertDialog.create().show()
                false
            }
        }
    }

}