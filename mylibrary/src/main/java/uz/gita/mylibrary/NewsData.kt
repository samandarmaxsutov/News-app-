package uz.gita.mylibrary

import java.io.Serializable
import java.util.*

data class NewsData(
    val id: String = UUID.randomUUID().toString(),
    val title: String="",
    val description: String="",
    val image:String="",
    var like: String="0",
    var notlike:String="0",
    var seen:String="0",
    var date:String=Calendar.getInstance().get(Calendar.DAY_OF_YEAR).toString(),
    var link:String="https://ictschool.uz/category/yangiliklar/"
):Serializable