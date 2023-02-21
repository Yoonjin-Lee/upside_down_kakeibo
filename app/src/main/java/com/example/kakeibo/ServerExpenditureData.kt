package com.example.kakeibo

data class ServerExpenditureData (
    val expenditureId : Int,
    val historyId : Int,
    val icon : Long,
    val content : String,
    val cost : Long,
    val memo : String
)