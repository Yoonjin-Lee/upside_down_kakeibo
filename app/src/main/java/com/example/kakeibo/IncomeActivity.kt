package com.example.kakeibo
import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kakeibo.R

class IncomeActivity : AppCompatActivity() {

    private val tableName: String = "noteData"

    private lateinit var dbHelper: IncomeDatabaseHelper
    private lateinit var database: SQLiteDatabase

    var sort: String = "desc"

    lateinit var noteRecycler: RecyclerView

    var layoutStyle: Boolean = true

    var searchText: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseCreate() // 데이터베이스 생성 함수
        createTable() // 테이블 생성 함수
        /*

        val toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        // 툴바 설정
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // 버전이 롤리팝 이상일 경우
            toolbar.setTitle(R.string.app_name)
            setSupportActionBar(findViewById(R.id.toolbar))
        } else {
            // 버전이 롤리팝 미만일 경우
            setSupportActionBar(findViewById(R.id.toolbar))
        }
        */


        noteRecycler = findViewById(R.id.recyclerView_main)
        // 리사이클러 뷰 설정

        layoutStyle()
        // 리사이클러 뷰 레이아웃 스타일 설정

        val newNotepad: android.widget.Button = findViewById(R.id.newNoteButton_main)
        // 이미지버튼 설정
        newNotepad.setOnClickListener {
            // 이미지버튼 클릭시
            startActivity(Intent(this, IncomeNotepadActivity::class.java))
            finish()
            // NewNotepadActivity 클래스를 실행
        }

        if (sort == "desc")
            refreshListAsc()
        /*
        else
           refreshListAsc()
         */

        // 이 구간은 작성 및 수정에서 빠져나왔을 때
        // 정렬 값을 기억하고 설정하기 위함임
    }

    private fun layoutStyle() {
        if (layoutStyle) {
            noteRecycler.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            //StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            // 스타일이 true일 경우 StaggeredGrid로 설정
            // -> 일반 그리드 레이아웃으로 설정하고자 하면 아래 주석을 해제

            // GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        }
        /*
        else {
            noteRecycler.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            // 스타일이 false일 경우 리니어레이아웃으로 설정
        }
         */
    }

    fun refreshListAsc() {
        val list = ArrayList<IncomeNoteList>()
        // 리스트 설정

        val cursor: Cursor = database.rawQuery(
            "select * from noteData order by time ASC",
            null
        )
        // cursor 설정 (db베이스에서 내림차순으로 불러옴)
        // ASC 생략 가능

        for (i in 0 until cursor.count) {
            // count가 100이면 0 부터 99까지

            cursor.moveToNext() // 커서 이동
            val title = cursor.getString(1)
            val content = cursor.getString(2)
            val color = cursor.getString(4)
            list.add(IncomeNoteList(title, content, color))
            // 리스트에 DB에 담긴 내용 추가
        }
        cursor.close()
        // 커서를 닫음

        noteRecycler.adapter = IncomeNoteAdapter(list) //리사이클러뷰 어댑터 할당
    }


    private fun createTable() {
        database.execSQL(
            "create table if not exists ${tableName}(" +
                    "_id integer PRIMARY KEY autoincrement," +
                    "title text," +
                    "content text," +
                    "time text," +
                    "color text)"
        )
    }

    private fun databaseCreate() {
        // 데이터베이스 생성 | 쓰기 가능한 상태로 설정
        dbHelper = IncomeDatabaseHelper(this)
        database = dbHelper.writableDatabase
    }

    @SuppressLint("Recycle")
    fun searchQuery(query: String?) {
        val sql = "select * from noteData " +
                "where content Like " + "'%" + query + "%'" + "or title Like " + "'%" + query + "%'" + "order by time DESC"

        val cursor = database.rawQuery(
            "select * from noteData " +
                    "where content Like " + "'%" + query + "%'" + "or title Like " + "'%" + query + "%'" + "order by time DESC",
            null
        )

        val intent = Intent(this@IncomeActivity, IncomeNoteSearchAdapter::class.java)
        intent.putExtra("sql", sql)
        // 인텐트에 sql문구를 담음

        val recordCount = cursor.count
        // 갯수
        val adapter = IncomeNoteSearchAdapter(intent)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_main)

        layoutStyle()

        for (i in 0 until recordCount) {
            cursor.moveToNext()
            val title = cursor.getString(1)
            val content = cursor.getString(2)
            val color = cursor.getString(4)
            adapter.addItem(IncomeNoteList(title, content, color))
            // 어댑터에 아이템 추가
        }

        recyclerView.adapter = adapter
        // 리사이클뷰 어댑터 설정

        cursor.close()

    }

    // 옵션메뉴 선택
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sort -> {
                if (sort == "desc") {
                    //  Toast.makeText(this, "정렬 (과거순)", Toast.LENGTH_SHORT).show()
                    sort = "asc"
                    refreshListAsc()
                } /*else {
                    Toast.makeText(this, "정렬 (최신순)", Toast.LENGTH_SHORT).show()
                    sort = "desc"
                    refreshListDesc()
                }*/
            }

            R.id.menu_layout -> {
                if (layoutStyle) {
                    layoutStyle = true
                    layoutStyle()
                    // Toast.makeText(this, "세로 정렬", Toast.LENGTH_SHORT).show()
                } /*else {
                    layoutStyle = true
                    layoutStyle()
                    Toast.makeText(this, "바둑판 정렬", Toast.LENGTH_SHORT).show()
                }*/
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

