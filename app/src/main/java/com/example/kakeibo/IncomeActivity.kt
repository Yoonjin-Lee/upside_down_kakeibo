package com.example.kakeibo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kakeibo.databinding.ActivityIncomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Call
import java.util.*


class IncomeActivity : FragmentActivity() {

//    private val tableName: String = "noteData"
//
//    private lateinit var dbHelper: IncomeDatabaseHelper
//    private lateinit var database: SQLiteDatabase

//    var sort: String = "desc"

    lateinit var noteRecycler: RecyclerView

//    var layoutStyle: Boolean = true

//    var searchText: String = ""

    // yoonjin - fragment 변경 부분
    var dataList: ArrayList<IncomeNoteList> = arrayListOf()
    private lateinit var viewBinding: ActivityIncomeBinding
//    var monthMoney = ""
    var goalDate = ""

    var money = ""
    var period = ""
    var memo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityIncomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        AndroidThreeTen.init(this)

//        databaseCreate() // 데이터베이스 생성 함수
//        createTable() // 테이블 생성 함수

        // 리사이클러 뷰 설정
        noteRecycler = findViewById(R.id.recyclerView_main)

        val adapter = IncomeNewAdapter(dataList)
        noteRecycler.adapter = adapter
        noteRecycler.layoutManager = LinearLayoutManager(this)

//        adapter.apply {
//            dataList.add(IncomeNoteList(R.drawable.ic_halfselected_learn, "교육", "10000"))
//        }

//        layoutStyle()
//        // 리사이클러 뷰 레이아웃 스타일 설정

        val newNotepad: android.widget.Button = findViewById(R.id.newNoteButton_main)
        // 이미지버튼 설정
        // 추가 버튼 부분
        val listener = object : AddSpendingIncomeFragment.OnActionCompleteListener{
            override fun onActionComplete(item: IncomeNoteList) {
                if (item != null){
                    dataList.add(item)
                }

                noteRecycler.adapter?.notifyDataSetChanged()
            }
        }

        val bottomSheetDialogFragment = AddSpendingIncomeFragment()
        bottomSheetDialogFragment.setOnActionCompleteListener(listener)
        //모서리 둥글게
        bottomSheetDialogFragment.setStyle(
            BottomSheetDialogFragment.STYLE_NORMAL,
            R.style.RoundCornerBottomSheetDialogTheme
        )

        newNotepad.setOnClickListener {
            // 이미지버튼 클릭시
            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
        }


//        if (sort == "desc")
//            refreshListAsc()
        /*
        else
           refreshListAsc()
         */

        // 이 구간은 작성 및 수정에서 빠져나왔을 때
        // 정렬 값을 기억하고 설정하기 위함임

        //시작 버튼
        val startBtn = findViewById<Button>(R.id.startBtn)

        // 날짜 계산
        val currentDateTime = org.threeten.bp.LocalDate.now()
        val goalDateTime: org.threeten.bp.LocalDate
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDate = currentDateTime.format(formatter)
        Log.d("currentDate", currentDate)

        Log.d("intent", intent.getStringExtra("period").toString())

        if (intent.hasExtra("money")) {
            money = intent.getStringExtra("money").toString()
            Log.d("money", money)
        }
        if (intent.hasExtra("period")) {
            period = intent.getStringExtra("period").toString()
            goalDateTime = org.threeten.bp.LocalDate.now().plusMonths(period!!.toLong())
            goalDate = goalDateTime.format(formatter)
            Log.d("goalDate", goalDate)
        }
        if (intent.hasExtra("memo")) {
            memo = intent.getStringExtra("memo").toString()
            Log.d("memo", memo)
        }


        startBtn.setOnClickListener {
            val income = viewBinding.edMuch.text.toString()
            var essential: Int = 0
            for (i in dataList) {
                essential += i.itemMoney.toInt()
            }
            Log.d("필수액", essential.toString())
            Log.d("요소", "income:  ${income}, money: ${money}, period: ${period}")
            val daily =
                (income.toInt() - essential - money.toInt()) * period.toInt() / (period.toInt() * 30)

            // 서버 -> 데이터 넘기기
            // 한 달 = 30일
            val goal = ServerGoalData(
                1,
                money.toInt(),
                currentDate,
                goalDate,
                memo,
                income.toInt(),
                essential,
                money.toInt(),
                daily
            )

            val authService = getRetrofit().create(ApiService::class.java)
            authService.postGoalData(goal).enqueue(object :
                retrofit2.Callback<Void> {
                override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        val intent = Intent(this@IncomeActivity, MainActivity::class.java)
                        startActivity(intent)

                        if (data != null) {
                            Log.d("test_retrofit", data.toString())
                        } else {
                            Log.w("retrofit", "실패 ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.w("retrofit", "목표 정보 접근 실패", t)
                    Log.w("retrofit", "목표 정보 접근 실패 response")
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("LifeCycle", "start")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LifeCycle", "resume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LifeCycle", "pause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LifeCycle", "stop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("LifeCycle", "restart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LifeCycle", "destroy")
    }

//    private fun layoutStyle() {
//        if (layoutStyle) {
//            noteRecycler.layoutManager =
//                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//            //StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//            // 스타일이 true일 경우 StaggeredGrid로 설정
//            // -> 일반 그리드 레이아웃으로 설정하고자 하면 아래 주석을 해제
//
//            // GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
//        }
//        /*
//        else {
//            noteRecycler.layoutManager =
//                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//            // 스타일이 false일 경우 리니어레이아웃으로 설정
//        }
//         */
//    }

//    fun refreshListAsc() {
//        val list = ArrayList<IncomeNoteList>()
//        // 리스트 설정
//
//        val cursor: Cursor = database.rawQuery(
//            "select * from noteData order by time ASC",
//            null
//        )
//        // cursor 설정 (db베이스에서 내림차순으로 불러옴)
//        // ASC 생략 가능
//
//        for (i in 0 until cursor.count) {
//            // count가 100이면 0 부터 99까지
//
//            cursor.moveToNext() // 커서 이동
//            val title = cursor.getString(1)
//            val content = cursor.getString(2)
//            val color = cursor.getString(4)
////            list.add(IncomeNoteList(title, content, color))
//            // 리스트에 DB에 담긴 내용 추가
//        }
//        cursor.close()
//        // 커서를 닫음
//
//        noteRecycler.adapter = IncomeNoteAdapter(list) //리사이클러뷰 어댑터 할당
//    }


//    private fun createTable() {
//        database.execSQL(
//            "create table if not exists ${tableName}(" +
//                    "_id integer PRIMARY KEY autoincrement," +
//                    "title text," +
//                    "content text," +
//                    "time text," +
//                    "color text)"
//        )
//    }
//
//    private fun databaseCreate() {
//        // 데이터베이스 생성 | 쓰기 가능한 상태로 설정
//        dbHelper = IncomeDatabaseHelper(this)
//        database = dbHelper.writableDatabase
//    }


//    @SuppressLint("Recycle")
//    fun searchQuery(query: String?) {
//        val sql = "select * from noteData " +
//                "where content Like " + "'%" + query + "%'" + "or title Like " + "'%" + query + "%'" + "order by time DESC"
//        /*
//        "select * from noteData where content Like '%query%' or title Like '%query%' order by time DESC"
//        select 컬럼 from 테이블 | *는 모든 컬럼을 의미함
//        where 조건
//        content 컬럼내에서 Like(포함하는것) | title 컬럼도 동일함
//        입력값이 사과일때
//        %query면 썩은사과, 파인사과 등
//        query%면 사과가격, 사과하세요 등
//        %query%면 황금사과가격, 빨리사과하세요 등
//        order by 정렬 | time 컬럼을 기준으로 DESC 내림차순
//        order by를 사용하지 않거나 order by time ASC로 하면 오름차순
//         */
//
//        val cursor = database.rawQuery(
//            "select * from noteData " +
//                    "where content Like " + "'%" + query + "%'" + "or title Like " + "'%" + query + "%'" + "order by time DESC",
//            null
//        )
//
//        val intent = Intent(this@IncomeActivity, IncomeNoteSearchAdapter::class.java)
//        intent.putExtra("sql", sql)
//        // 인텐트에 sql문구를 담음
//
//        val recordCount = cursor.count
//        // 갯수
//        val adapter = IncomeNoteSearchAdapter(intent)
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_main)
//
//        layoutStyle()
//
//        for (i in 0 until recordCount) {
//            cursor.moveToNext()
//            val title = cursor.getString(1)
//            val content = cursor.getString(2)
//            val color = cursor.getString(4)
//            adapter.addItem(IncomeNoteList(title, content, color))
//            // 어댑터에 아이템 추가
//        }
//
//        recyclerView.adapter = adapter
//        // 리사이클뷰 어댑터 설정
//
//        cursor.close()
//
//    }
//
//    // 옵션메뉴 선택
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.menu_sort -> {
//                if (sort == "desc") {
//                    //  Toast.makeText(this, "정렬 (과거순)", Toast.LENGTH_SHORT).show()
//                    sort = "asc"
//                    refreshListAsc()
//                } /*else {
//                    Toast.makeText(this, "정렬 (최신순)", Toast.LENGTH_SHORT).show()
//                    sort = "desc"
//                    refreshListDesc()
//                }*/
//            }
//
//            R.id.menu_layout -> {
//                if (layoutStyle) {
//                    layoutStyle = true
//                    layoutStyle()
//                    // Toast.makeText(this, "세로 정렬", Toast.LENGTH_SHORT).show()
//                } /*else {
//                    layoutStyle = true
//                    layoutStyle()
//                    Toast.makeText(this, "바둑판 정렬", Toast.LENGTH_SHORT).show()
//                }*/
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
}