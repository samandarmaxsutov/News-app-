package uz.gita.readnews.prsenter.impl

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import uz.gita.readnews.models.UserData
import uz.gita.readnews.prsenter.SignUpViewModel


class SignUpViewModelImpl : SignUpViewModel, ViewModel(){
    private lateinit var auth: FirebaseAuth
    private val TAG = "NewsReader:  "
    override val progressLiveData=MutableLiveData<Boolean>()
    override val openMainScreenLivedata=MutableLiveData<Unit>()
    override val error=MutableLiveData<String>()
    override val message=MutableLiveData<String>()

    override fun create(context: Activity,userData: UserData){

        auth = Firebase.auth
        if(userData.name.isEmpty() && userData.lastName.isEmpty() && userData.email.isEmpty() &&
            userData.phone.isEmpty() && userData.password.isEmpty() ){
            error.value = "Kiritmagan"

        }
        if (userData.name.isEmpty()){
            error.value="Ismingizni kiriting!"
            return
        }
        if (userData.lastName.isEmpty()){
            error.value="Familiyangizni kiriting!"
            return
        }
        if (userData.email.isEmpty()){
            error.value="Emailni kiriting!"
            return
        }
        if (userData.phone.isEmpty()){
            error.value="Telefon raqamingizni kiriting!"
            return
        }

        if (userData.password.isEmpty()){
            error.value="Parolni kiriting!"
            return
        }
        if (userData.phone.length < 8 && userData.phone.isNotEmpty()){
            error.value="To'g'ri telefon raqamni kiriting"
            return
        }
        progressLiveData.value=true
        auth.createUserWithEmailAndPassword(userData.email, userData.password)
            .addOnCompleteListener(context) { task ->

                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    progressLiveData.value=false
                    openMainScreenLivedata.value=Unit
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Log.e(":  " , task.exception.toString())
                    task.addOnFailureListener {
                        progressLiveData.value=false
                        Log.d("tag:  " , it.toString())
                        message.value = it.toString()
                    }
                }
            }

     }
}