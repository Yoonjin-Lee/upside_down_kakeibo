package com.example.kakeibo

import java.util.Date

data class ServerGoalData(
    val icon : Int,
    val target_amount : Int,
    val period_start : String,
    val period_end : String,
    val memo : String,
    val monthly_income : Int,
    val essential_amount : Int,
    val rest_amount : Int,
    val daily_avail_amount : Int
)
