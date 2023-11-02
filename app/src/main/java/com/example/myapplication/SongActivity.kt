package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySongBinding
import androidx.activity.result.ActivityResult
import android.util.Log
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {
    //
    companion object {
        const val STRING_INTENT_KEY = "my_string_key"
    }
//
    lateinit var binding : ActivitySongBinding
    lateinit var song: Song
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson=Gson()

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
            mediaPlayer?.start() // MediaPlayer를 시작
        }
        binding.nuguBtnPauseIv.setOnClickListener {
            setPlayerStatus(true)
            mediaPlayer?.pause() // MediaPlayer를 일시 중지
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

    private fun initSong(){
        if (intent.hasExtra("title")&&intent.hasExtra("singer")) {
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }
        startTimer()
    }

    private fun setPlayer(song:Song){
        //binding.albumTitleTv.text=intent.getStringExtra("title")!!
        //binding.albumSingerTv.text=intent.getStringExtra("singer")!!
        binding.albumTitleTv.text=song.title
        binding.albumSingerTv.text=song.singer
        binding.songPlayingStartTv.text=String.format("%02d:%02d", song.second/60, song.second %60)
        binding.songPlayingEndTv.text=String.format("%02d:%02d", song.playTime/60, song.playTime %60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer=MediaPlayer.create(this, music)
        setPlayerStatus(song.isPlaying)
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
            if (mediaPlayer?.isPlaying==true) {
                mediaPlayer?.pause()
            }
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

    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        song.second=((binding.songProgressSb.progress * song.playTime)/100)/1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor= sharedPreferences.edit()
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)

        editor.apply()


    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }


}