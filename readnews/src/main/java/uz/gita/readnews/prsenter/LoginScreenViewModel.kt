package uz.gita.readnews.prsenter

import android.app.Activity
import androidx.lifecycle.LiveData
import uz.gita.readnews.models.UserData

interface LoginScreenViewModel {
    //TODO Keyinroq qilaman baribir keraksizku!!!
    val openMainScreenLivedata:LiveData<Unit>
    val error:LiveData<String>
    val message:LiveData<String>
    fun create(context: Activity, userData: UserData)
}