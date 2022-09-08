package uz.gita.mylibrary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

internal class OrderRepositoryImpl : NewsRepository {
    private val db = Firebase.firestore

    override fun placingOrder(orderData: NewsData): LiveData<Result<Unit>> {
        val liveData = MutableLiveData<Result<Unit>>()
        Log.d("TTT", "placingOrder1")
        db.collection("news").document(orderData.id).set(orderData)
            .addOnCompleteListener {
                Log.d("TTT", "addOnCompleteListener")
            }
            .addOnSuccessListener {
                Log.d("TTT", "addOnSuccessListener")
                liveData.value = Result.success(Unit)
            }
            .addOnFailureListener {
                Log.d("TTT", "addOnFailureListener")
                liveData.value = Result.failure(it)
            }
        Log.d("TTT", "placingOrder2")
        return liveData
    }

    override fun getAll(): LiveData<Result<List<NewsData>>> {
        val liveData = MutableLiveData<Result<List<NewsData>>>()

        db.collection("news").get()
            .addOnSuccessListener {
                val ls = it.documents.map { item -> Mapper.run { item.toNewsData() } }
                liveData.value = Result.success(ls)
            }
            .addOnFailureListener { liveData.value = Result.failure(it) }

        return liveData
    }

    override fun getAll2(): LiveData<Result<List<NewsData>>> {
        val liveData = MediatorLiveData<Result<List<NewsData>>>()
        liveData.addDisposable(getAll()) { liveData.value = it }

        db.collection("news").addSnapshotListener { snapshot, e ->
            liveData.addDisposable(getAll()) { liveData.value = it }
        }

        return liveData
    }
}

private fun <T, K> MediatorLiveData<T>.addDisposable(source: LiveData<K>, block: Observer<K>) {
    addSource(source) {
        block.onChanged(it)
        removeSource(source)
    }
}

