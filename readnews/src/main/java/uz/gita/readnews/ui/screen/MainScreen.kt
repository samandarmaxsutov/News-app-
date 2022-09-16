package uz.gita.readnews.ui.screen

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.mylibrary.utils.massage
import uz.gita.readnews.R
import uz.gita.readnews.databinding.FragmentMainScreenBinding
import uz.gita.readnews.prsenter.MainScreenViewModel
import uz.gita.readnews.prsenter.impl.MainScreenViewModelImpl
import uz.gita.readnews.ui.adapter.MainAdapter


class MainScreen : Fragment(R.layout.fragment_main_screen) {

    private val adapter:MainAdapter by lazy {   MainAdapter()}
    private val viewModel:MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val binding:FragmentMainScreenBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observers()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }
    private fun init() {
        binding.list.adapter = adapter
        viewModel.newsLiveData.observe(viewLifecycleOwner) {

            if (it.isNotEmpty()){
                binding.noFound.visibility=View.GONE
                binding.list.visibility=View.VISIBLE
                adapter.submitItems(it)
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



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                var job: Job?=null
                job?.cancel()
                binding.progressBar.visibility=View.VISIBLE

                job= lifecycleScope.launch(){
                    delay(300)
                    binding.progressBar.visibility=View.GONE
                    viewModel.searchView(query!!)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.progressBar.visibility=View.VISIBLE
                var job:Job?=null
                job?.cancel()
                job= lifecycleScope.launch(){
                    delay(1000)
                    viewModel.searchView(newText!!)
                    binding.progressBar.visibility=View.GONE
                }
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