package com.example.kakeibo

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kakeibo.databinding.FragmentHistoryBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class HistoryFragment : Fragment() {
    private lateinit var viewBinding: FragmentHistoryBinding
    var serverData : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHistoryBinding.inflate(layoutInflater)

        //데이터를 넘겨줄 리스트
        val weekDataList : ArrayList<WeekData> = arrayListOf()
        val adapter = WeekRAdapter(weekDataList)

        //어뎁터, 수직 설정
        viewBinding.list.adapter = adapter
        viewBinding.list.layoutManager = LinearLayoutManager(context)

        // api 설정
        val authService = getRetrofit().create(ApiService::class.java)

        authService.getHistoryData().enqueue(object : Callback<List<ServerHistoryData>> {
            override fun onResponse(call: Call<List<ServerHistoryData>>, response: retrofit2.Response<List<ServerHistoryData>>) {
                if (response.isSuccessful) {
                    val data = response.body()

                    if (data != null) {
                        Log.d("test_retrofit", "열차 정보 :" + data)

                        val date_num = data.size //총 몇일인지
                        val week_num = date_num/7 //몇 주인지
                        Log.d("date_num", date_num.toString())
                        Log.d("week_num", week_num.toString())

                        //1. 주 동안
                        //2. 7번
                        //3. date_num이 되면 멈추도록

                        for (i in 0 until week_num){
                            val tempList = arrayListOf<String>()
                            //주차 넣기
                            tempList.add((i+1).toString())

                            for (j in 1..7){
                                val index = 10*i + j
                                if ((10*i)+j < week_num){ //date_num 전까지는 안의 데이터를 입력
                                    if (j != 7){
                                        tempList.add(data[index].date)
                                        tempList.add(data[index].success.toString())
                                    } else {
                                        tempList.add(data[index].date)
                                        tempList.add(data[index].success.toString())
                                        //일요일의 저축 가능 금액
                                        tempList.add(data[index].money.toString())
                                    }
                                } else if ((10*i) + j == week_num) {
                                    //마지막 날의 저축 가능 금액을 그 주의 저축 가능 금액으로 설정
                                    tempList.add(data[index].date)
                                    tempList.add(data[index].success.toString())
                                    //마지막 날의 저축 가능 금액
                                    tempList[15] = data[index].money.toString()
                                } else {
                                    // 그 주의 다른 날은 null로 입력.
                                    tempList.add("") //날짜
                                    tempList.add("3") //버튼 색
                                }
                            }

                            weekDataList.add(
                                WeekData(tempList[0], tempList[1], tempList[2].toInt(), tempList[3], tempList[4].toInt(),
                            tempList[5], tempList[6].toInt(), tempList[7], tempList[8].toInt(), tempList[9], tempList[10].toInt(),
                            tempList[11], tempList[12].toInt(), tempList[13], tempList[14].toInt(), tempList[15])
                            )
                        }
                    }
                } else {
                    Log.w("retrofit", "실패 ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<ServerHistoryData>>, t: Throwable) {
                Log.w("retrofit", "열차 정보 접근 실패", t)
                Log.w("retrofit", "열차 정보 접근 실패 response",)
            }
        })

        //테스트 데이터
//        adapter.apply {
//            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,0,1,1, 1, 1,1, "50"))
//            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,1,0,1, 1, 1,1, "50"))
//            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,1,1,1, 1, 1,1, "50"))
//            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,1,1,1, 1, 1,1, "50"))
//            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,0,1,1, 1, 1,0, "50"))
//            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,1,1,1, 1, 1,1, "50"))
//            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,1,1,1, 1, 1,1, "50"))
//            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,1,1,1, 1, 1,1, "50"))
//        }

        //리스트 아이템 사이 간격
        val verticalItemDecorator = VerticalItemDecorator(16)
        viewBinding.list.addItemDecoration(verticalItemDecorator)

        //shareBtn clickListener 구현
        adapter.setItemClickListener(object : WeekRAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                val intent = Intent(context, ResultActivity::class.java)
                startActivity(intent)
            }
        })

        //날짜 버튼 ClickListener 구현
        adapter.setDateClickListener(object : WeekRAdapter.OnDateClickListener{
            override fun onClick(v: View, position: Int) {
                val intent = Intent(context, DateresultActivity::class.java)
                startActivity(intent)
            }
        })

        return viewBinding.root
    }

    //아이템 간격을 위한 클래스
    inner class VerticalItemDecorator(var divHeight: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.top = divHeight
            outRect.bottom = divHeight
        }
    }
}