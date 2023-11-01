package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.FragmentPannelBinding
import me.relex.circleindicator.CircleIndicator3

class PannelFragment(val imgRes: Int) : Fragment() {
    lateinit var binding : FragmentPannelBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPannelBinding.inflate(inflater, container, false)

        binding.pannelImageIv.setImageResource(imgRes)

        //val circleIndicator = binding.Indicator
        val viewPager = view?.findViewById<ViewPager2>(R.id.home_pannel_vp)
        val indicator = view?.findViewById<CircleIndicator3>(R.id.Indicator)


        // CircleIndicator와 ViewPager2 연결
        indicator?.setViewPager(viewPager)

        return binding.root
    }
}