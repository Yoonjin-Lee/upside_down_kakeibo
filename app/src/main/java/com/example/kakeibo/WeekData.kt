package com.example.kakeibo

//History Fragment에서 사용하는 RV의 데이터 타입
data class WeekData (
    val week: String,
    val mon: String,
    val tue: String,
    val wed: String,
    val thu: String,
    val fri: String,
    val sat: String,
    val sun: String,
    //성공, 실패 여부 나타내는 데이터 필요.
    val mon_check : Int,
    val tue_check : Int,
    val wed_check : Int,
    val thu_check : Int,
    val fri_check : Int,
    val sat_check : Int,
    val sun_check : Int,
    //저축 가능한 금액
    val money: String
)