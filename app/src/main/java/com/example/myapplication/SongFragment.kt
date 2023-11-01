package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentSongBinding

class SongFragment: Fragment() {
    lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)

        binding.btnToggleOffIv.setOnClickListener {
            setToggletatus(false)
        }
        binding.btnToggleOnIv.setOnClickListener {
            setToggletatus(true)
        }

        return binding.root
    }

    fun setToggletatus(isToggle: Boolean) {
        if (isToggle) {
            binding.btnToggleOffIv.visibility = View.VISIBLE
            binding.btnToggleOnIv.visibility = View.GONE
        }
        else {
            binding.btnToggleOffIv.visibility = View.GONE
            binding.btnToggleOnIv.visibility = View.VISIBLE
        }

    }

}