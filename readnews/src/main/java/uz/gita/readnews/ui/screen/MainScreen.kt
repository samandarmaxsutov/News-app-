package uz.gita.readnews.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.mylibrary.NewsData
import uz.gita.mylibrary.Repository
import uz.gita.readnews.R
import uz.gita.readnews.databinding.FragmentMainScreenBinding
import uz.gita.readnews.prsenter.MainScreenViewModel
import uz.gita.readnews.prsenter.impl.MainScreenViewModelImpl
import uz.gita.readnews.ui.adapter.MainAdapter


class MainScreen : Fragment(R.layout.fragment_main_screen) {
    private val repository = Repository.orderRepository
    private val adapter:MainAdapter by lazy {   MainAdapter()}
    private val viewModel:MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val binding:FragmentMainScreenBinding by viewBinding()

    val data=ArrayList<NewsData>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.list.adapter=adapter
        repository.getAll2().observe(viewLifecycleOwner){

            if(it.isSuccess){
                data.clear()
                data.addAll(it.getOrNull()!!)
                adapter.submitItems(data)
            }else{
                Toast.makeText(requireContext(), it.exceptionOrNull()!!.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }

}