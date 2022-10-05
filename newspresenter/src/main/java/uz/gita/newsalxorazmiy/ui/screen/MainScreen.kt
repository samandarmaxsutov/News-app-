package uz.gita.newsalxorazmiy.ui.screen

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.mylibrary.utils.massage
import uz.gita.newsalxorazmiy.R
import uz.gita.newsalxorazmiy.databinding.FragmentMainScreenBinding
import uz.gita.newsalxorazmiy.presenter.MainScreenViewModel
import uz.gita.newsalxorazmiy.presenter.impl.MainScreenViewModelImpl
import uz.gita.newsalxorazmiy.ui.adpter.MainAdapter

class MainScreen: Fragment(R.layout.fragment_main_screen) {

    private val adapter: MainAdapter by lazy {   MainAdapter()}
    private val viewModel: MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val binding: FragmentMainScreenBinding by viewBinding()

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
            adapter.submitItems(it)
            if (it.isNotEmpty()){
                binding.noFound.visibility= View.GONE
                binding.list.visibility= View.VISIBLE

            }else{
                binding.noFound.visibility= View.VISIBLE
                binding.list.visibility= View.GONE
            }

        }
        adapter.onClickItem {

        }
        binding.searchView.setOnSearchClickListener {
            binding.textViewTitle.visibility = View.GONE


        }

        binding.searchView.setOnCloseListener{
            binding.textViewTitle.visibility = View.VISIBLE
            false
        }

        binding.addBtn.setOnClickListener {
            viewModel.openAddScreen()
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                viewModel.searchView(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                viewModel.searchView(newText!!)
                return true
            }

        })

    }
    private fun observers(){

        viewModel.massageLiveData.observe(this){
            massage(requireContext() as Activity,it)
        }
        viewModel.openAddScreenLiveData.observe(this){
            findNavController().navigate(MainScreenDirections.actionMainScreen2ToAddScreen())
        }

    }
}