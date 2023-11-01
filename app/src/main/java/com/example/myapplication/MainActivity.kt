package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.databinding.ActivityMainBinding
import androidx.activity.result.ActivityResult
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //companion object { const val STRING_INTENT_KEY = "my_string_key"}

    //private val getResultText = registerForActivityResult(
    //    ActivityResultContracts.StartActivityForResult()
    //) { result ->
    //    if (result.resultCode == Activity.RESULT_OK) {
    //        val resultString = result.data?.getStringExtra(STRING_INTENT_KEY)
    //    }
    //}

    companion object {
        const val STRING_INTENT_KEY = "my_string_key"
    }

    //
    private val getResultText = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultString = result.data?.getStringExtra(SongActivity.STRING_INTENT_KEY)
            Toast.makeText(this, resultString, Toast.LENGTH_SHORT).show()
        }
    }

//

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val song= Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(), 0, 60, false)

        //binding.mainPlayerCl.setOnClickListener {
            //startActivity(Intent(this, SongActivity::class.java))
        //    val intent=Intent(this,SongActivity::class.java)
        //    intent.putExtra("title", song.title)
        //    intent.putExtra("singer", song.singer)
        //    startActivity(intent)
        //}

        //
        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            getResultText.launch(intent) // SongActivity 호출
        }


        //?
        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            getResultText.launch(intent) // SongActivity 호출
        }

        if (intent.hasExtra("title")&&intent.hasExtra("singer")) {
            binding.mainMiniplayerTitleTv.text=intent.getStringExtra("title")
            binding.mainMiniplayerSingerTv.text=intent.getStringExtra("singer")
        }

        initBottomNavigation()

        Log.d("Song", song.title + song.singer)

    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}

