package uz.gita.readnews.ui.screen

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.tapadoo.alerter.Alerter
import uz.gita.mylibrary.utils.hideSystemUI
import uz.gita.mylibrary.utils.massage
import uz.gita.readnews.R
import uz.gita.readnews.databinding.FragmentMainScreenBinding
import uz.gita.readnews.databinding.SignupScreenBinding
import uz.gita.readnews.models.UserData
import uz.gita.readnews.prsenter.MainScreenViewModel
import uz.gita.readnews.prsenter.SignUpViewModel
import uz.gita.readnews.prsenter.impl.MainScreenViewModelImpl
import uz.gita.readnews.prsenter.impl.SignUpViewModelImpl

class SignUpScreen: Fragment(R.layout.signup_screen) {
    private val viewModel: SignUpViewModel by viewModels<SignUpViewModelImpl>()
    private val binding: SignupScreenBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        if(FirebaseAuth.getInstance().currentUser!=null){
            findNavController().navigate(SignUpScreenDirections.actionSignUpScreenToMainScreen())
        }
        viewModel.openMainScreenLivedata.observe(this){
            findNavController().navigate(SignUpScreenDirections.actionSignUpScreenToMainScreen())
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val animDrawable = binding.rootScroll.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(4000)
        animDrawable.start()
        binding.telReg.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        viewModel.message.observe(viewLifecycleOwner){

            if(it.toString().contains("badly")){
               binding.emailReg.setError("Emailni to'g'ri kiriting!")
               return@observe
           }
            if(it.toString().toLowerCase().contains("password")){
                binding.parolReg.setError("Parol kamida 6 ta belgidan iborat bo'lishi kerak")
                return@observe
            }
            if(it.toString().toLowerCase().contains("network error")){
                massage(requireActivity(),"Internet aloqasi yo'q")
                return@observe
            }
            if (it.toString().toLowerCase().contains("already in use by another account")){
                binding.emailReg.setError("Bu emaildan allaqachon foydalanilgan")
                return@observe
            }
            massage(requireActivity(),it)
        }
        viewModel.error.observe(viewLifecycleOwner){
            when(it){
                "Kiritmagan"-> setErrors()
                "Familiyangizni kiriting"->binding.familiyaReg.error=it
                "Emailni kiriting"->binding.emailReg.error=it
                "Telefon raqamingizni kiriting"->binding.telReg.error=it
                "Parolni kiriting"->binding.parolReg.error=it
                "To'g'ri telefon raqamni kiriting"->binding.telReg.error=it
                "Ismingizni kiriting"->binding.ismReg.error=it

            }
        }
        binding.signUpReg.setOnClickListener {
            viewModel.create(requireActivity(), UserData(
                binding.ismReg.text.toString(),
                binding.familiyaReg.text.toString(),
                binding.emailReg.text.toString(),
                binding.telReg.text.toString(),
                binding.parolReg.text.toString()
            ))
        }
        val progress=ProgressDialog(requireContext())
        progress.create()
        viewModel.progressLiveData.observe(viewLifecycleOwner){

            progress.setCancelable(false)
             if (it){
                 progress.setMessage("Ro'yxatdan o'tkazilmoqda...");
                 progress.show();
             }else{
                 progress.cancel()
             }

        }
    }
    private fun setErrors(){
        binding.ismReg.error="Ismingizni kiriting!"
        binding.familiyaReg.error="Familiyangizni kiriting!"
        binding.emailReg.error="Emailni kiriting!"
        binding.telReg.error="Telefon raqamingizni kiriting!"
        binding.parolReg.error= "Parolni kiriting!"
    }
    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }
}