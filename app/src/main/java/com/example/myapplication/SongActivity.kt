package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySongBinding
import androidx.activity.result.ActivityResult
import android.util.Log

class SongActivity : AppCompatActivity() {
    //
    companion object {
        const val STRING_INTENT_KEY = "my_string_key"
    }
//
    lateinit var binding : ActivitySongBinding
    lateinit var song: Song
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

        //val song= Song(binding.albumTitleTv.text.toString(), binding.albumSingerTv.text.toString())


        //
        binding.btnDownIv.setOnClickListener {
            val intent = Intent()
            intent.putExtra(MainActivity.STRING_INTENT_KEY, binding.albumTitleTv.text.toString()) // 여기에 앨범 제목 데이터를 추가
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.nuguBtnPlayIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.nuguBtnPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.nuguBtnRandomInactiveIv.setOnClickListener {
            setRandomStatus(false)
        }
        binding.nuguBtnRandomInactiveClickIv.setOnClickListener {
            setRandomStatus(true)
        }

        binding.nuguBtnRepeatInactiveIv.setOnClickListener {
            setRepeatStatus(false)
        }
        binding.nuguBtnRepeatInactiveClickIv.setOnClickListener {
            setRepeatStatus(true)
        }


        if (intent.hasExtra("title")&&intent.hasExtra("singer")) {
            binding.albumTitleTv.text=intent.getStringExtra("title")
            binding.albumSingerTv.text=intent.getStringExtra("singer")
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    private fun initSong(){
        if (intent.hasExtra("title")&&intent.hasExtra("singer")) {
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false)
            )
        }
        startTimer()
    }

    private fun setPlayer(song:Song){
        binding.albumTitleTv.text=intent.getStringExtra("title")!!
        binding.albumSingerTv.text=intent.getStringExtra("singer")!!
        binding.songPlayingStartTv.text=String.format("%02d:%02d", song.second/60, song.second %60)
        binding.songPlayingEndTv.text=String.format("%02d:%02d", song.playTime/60, song.playTime %60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
    }

    fun setRandomStatus(isRandom: Boolean) {
        if (isRandom) {
            binding.nuguBtnRandomInactiveIv.visibility = View.VISIBLE
            binding.nuguBtnRandomInactiveClickIv.visibility = View.GONE
        }
        else {
            binding.nuguBtnRandomInactiveIv.visibility = View.GONE
            binding.nuguBtnRandomInactiveClickIv.visibility = View.VISIBLE
        }

    }

    fun setRepeatStatus(isRepeat: Boolean) {
        if (isRepeat) {
            binding.nuguBtnRepeatInactiveIv.visibility = View.VISIBLE
            binding.nuguBtnRepeatInactiveClickIv.visibility = View.GONE
        }
        else {
            binding.nuguBtnRepeatInactiveIv.visibility = View.GONE
            binding.nuguBtnRepeatInactiveClickIv.visibility = View.VISIBLE
        }
    }



    fun setPlayerStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying) {
            binding.nuguBtnPlayIv.visibility= View.VISIBLE
            binding.nuguBtnPauseIv.visibility= View.GONE
        }
        else {
            binding.nuguBtnPlayIv.visibility= View.GONE
            binding.nuguBtnPauseIv.visibility= View.VISIBLE
        }
    }

    private fun startTimer(){
        timer = Timer(song.playTime,song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int,var isPlaying: Boolean = true):Thread(){

        private var second : Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try {
                while (true){

                    if (second >= playTime){
                        break
                    }

                    if (isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
                        }

                        if (mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songPlayingStartTv.text = String.format("%02d:%02d",second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }

            }catch (e: InterruptedException){
                Log.d("Song","쓰레드가 죽었습니다. ${e.message}")
            }


        }
    }

}