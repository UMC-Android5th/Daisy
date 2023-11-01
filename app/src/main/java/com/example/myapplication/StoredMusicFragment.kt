package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentStoredmusicBinding


class StoredMusicFragment: Fragment() {

    lateinit var binding: FragmentStoredmusicBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentStoredmusicBinding.inflate(inflater, container, false)
        return binding.root
    }
}