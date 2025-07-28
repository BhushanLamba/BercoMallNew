package it.softbrain.barcomall.presentation.utils

import androidx.viewpager2.widget.ViewPager2
import it.softbrain.barcomall.presentation.adapter.ImageSliderAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

object ImageSliderUtils {


    fun setUpBanner(bannerList: ArrayList<String>, viewPager: ViewPager2,onClick:(String)->Unit={}) {
        val sliderJob = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Main + sliderJob)

        val imageSliderAdapter = ImageSliderAdapter(bannerList,onClick)
        viewPager.adapter = imageSliderAdapter

        scope.launch {
            while (isActive) {
                delay(3000L) // Wait for 3 seconds
                val nextItem = (viewPager.currentItem + 1) % imageSliderAdapter.itemCount
                viewPager.setCurrentItem(nextItem, true)
            }
        }



    }
}