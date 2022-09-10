package uz.gita.readnews.models.sharedperfarnce

import android.content.Context
import android.content.SharedPreferences

class SharedPref private constructor(){
    companion object{
        private var sharedPref:SharedPref?=null
        private var sharedPreferences:SharedPreferences? =null
        fun init(context: Context){
            if (sharedPref==null){
                sharedPref=SharedPref()
                sharedPreferences=context.getSharedPreferences("shared",Context.MODE_PRIVATE)
            }
        }
        fun getInstance()= sharedPref!!
    }
    var isFirst:Boolean get() {
       return sharedPreferences!!.getBoolean("isFirst", true)
    } set(value) {
        sharedPreferences!!.edit().putBoolean("isFirst",value).apply()
    }



}