package uz.gita.mylibrary.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ActivityChooserView
import com.tapadoo.alerter.Alerter


fun massage(activity: Activity, massage:String){
    Alerter.create(activity)
        .setTitle("News App")
        .setText(massage)
        .setDuration(10000)
        .show()
}