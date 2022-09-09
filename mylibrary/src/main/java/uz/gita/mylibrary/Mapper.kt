package uz.gita.mylibrary

import com.google.firebase.firestore.DocumentSnapshot

object Mapper {
    fun DocumentSnapshot.toNewsData() = NewsData(
        id = this["id"].toString(),
        title = this["title"].toString(),
        description = this["description"].toString(),
        image = this["image"].toString(),
        like = this["like"].toString(),
        notlike = this["notlike"].toString(),
        seen = this["seen"].toString(),
        date = this["date"].toString()
    )
}