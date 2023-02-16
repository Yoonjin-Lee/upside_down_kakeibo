package com.example.kakeibo

//History Fragment에서 사용하는 RV의 데이터 타입
data class WeekData (
    //주차
    val week: String,

    //날짜
    val mon: String,
    //성공 여부
    val mon_check : Int,

    val tue: String,
    val tue_check : Int,

    val wed: String,
    val wed_check : Int,

    val thu: String,
    val thu_check : Int,

    val fri: String,
    val fri_check : Int,

    val sat: String,
    val sat_check : Int,

    val sun: String,
    val sun_check : Int,

    //저축 가능한 금액
    val money: String
)