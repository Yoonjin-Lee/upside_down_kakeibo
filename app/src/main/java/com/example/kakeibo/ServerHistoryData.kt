package com.example.kakeibo

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class ServerHistoryData(
    @SerializedName("date") var date: String,
    @SerializedName("success") var success : Int,
    @SerializedName("history_id") var historyId : Int,
//    @SerializedName("money") var money : Int
)
