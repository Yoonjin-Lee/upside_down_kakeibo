package com.example.kakeibo

import com.google.gson.annotations.SerializedName

data class Data_DailyAvailable(
    @SerializedName("todayAvail") val todayAvail : Int //특정 유저 오늘 사용 가능한 금액
)
