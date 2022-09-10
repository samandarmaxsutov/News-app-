package uz.gita.readnews.ui.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.readnews.models.IntroData
import uz.gita.readnews.ui.pages.IntroPage


class IntroAdapter(fragment: Fragment,private var list: List<IntroData>):FragmentStateAdapter(fragment) {
    override fun getItemCount()=list.size

    override fun createFragment(position: Int): Fragment {
        val page = IntroPage()
        page.arguments= bundleOf(Pair("data",list[position]))

        return page
    }
}