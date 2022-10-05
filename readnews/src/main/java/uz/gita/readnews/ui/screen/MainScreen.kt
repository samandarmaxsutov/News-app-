package uz.gita.readnews.ui.screen


import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.mylibrary.utils.checkForInternet
import uz.gita.mylibrary.utils.massage
import uz.gita.readnews.R
import uz.gita.readnews.databinding.FragmentMainScreenBinding
import uz.gita.readnews.prsenter.MainScreenViewModel
import uz.gita.readnews.prsenter.impl.MainScreenViewModelImpl
import uz.gita.readnews.ui.adapter.MainAdapter
import java.io.InvalidObjectException


class MainScreen : Fragment(R.layout.fragment_main_screen) {

    private val adapter:MainAdapter by lazy {   MainAdapter()}
    private val viewModel:MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val binding: FragmentMainScreenBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)

        observers()
        lifecycleScope.launch(Dispatchers.IO){
            if(!checkForInternet(requireContext()))
                massage(requireActivity(),"Internetga ulanmagan")
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val progress= ProgressDialog(requireContext())

        init()
        viewModel.progressBarLiveData.observe(viewLifecycleOwner){

            if (it){
                progress.setMessage("Ma'lumotlar yuklanmoqda...");
                progress.show();
            }else{
               lifecycleScope.launch{
                   delay(500)
                progress.dismiss()
            }}
        }

    }
    private fun init() {
        binding.list.adapter = adapter
        binding.swipeLayout.setOnRefreshListener {
            if(checkForInternet(requireContext())){
                lifecycleScope.launch {
                    delay(2000)
                    binding.swipeLayout.isRefreshing = false
                }

            } else{
                massage(requireActivity(),"Internetga ulanmagan")
                binding.swipeLayout.isRefreshing = false
            }
        }


        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            adapter.submitItems(it)
            if (it.isNotEmpty()){
                binding.noFound.visibility=View.GONE
                binding.list.visibility=View.VISIBLE

            }else{


                binding.noFound.visibility=View.VISIBLE
                binding.list.visibility=View.GONE
            }

        }
        adapter.onClickItem {
            viewModel.openInfoLiveData(it)
        }
        binding.searchView.setOnSearchClickListener {
            binding.textViewTitle.visibility = View.GONE


        }

        binding.searchView.setOnCloseListener{
            binding.textViewTitle.visibility = View.VISIBLE
            false
        }


//        binding.shimmerLayout.startShimmerAnimation()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
//                var job: Job?=null
//                job?.cancel()
//                binding.progressBar.visibility=View.VISIBLE
//
//                job= lifecycleScope.launch(){
//                    delay(300)
//                    binding.progressBar.visibility=View.GONE
//
//                }



//                binding.searchView.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.MATCH_PARENT)
                viewModel.searchView(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//               binding.progressBar.visibility=View.VISIBLE
//                var job:Job?=null
//                job?.cancel()
//                job= lifecycleScope.launch(){
//                    delay(1000)
//
//                    binding.progressBar.visibility=View.GONE
//                }
                viewModel.searchView(newText!!)
                return true
            }

        })




    }
    private fun observers(){
        viewModel.openInfoScreenLiveData.observe(this){
            findNavController().navigate(MainScreenDirections.actionMainScreenToInfoScreen(it))
        }
        viewModel.massageLiveData.observe(this){
            massage(requireContext() as Activity,it)
        }

    }
}