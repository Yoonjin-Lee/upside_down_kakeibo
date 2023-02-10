package com.example.kakeibo

import android.media.Image

data class Data_item(
//    val itemIcn: Image, //아이콘 이미지
    val itemIcn: Int, //아이콘 이미지
    val itemContent: String, //내용
    val itemMoney: String, //금액 입력
    val itemMemo: String //한 줄 메모
)