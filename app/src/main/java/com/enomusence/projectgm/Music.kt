package com.enomusence.projectgm

import android.media.MediaPlayer
import android.widget.SeekBar

object Music {
    fun setSeekBar(seekBar: SeekBar, music: MediaPlayer, volume: Boolean? = null, seekTo: Boolean? = null){
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               if(fromUser){
                   when{
                       volume == null -> music.seekTo(progress)
                       seekTo == null -> {
                           val volumeNumber = progress / 100.0f
                           music.setVolume(volumeNumber, volumeNumber)
                       }
                   }
               }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }

    fun createTimeLable(time: Int): String{
        var timelable = ""
        val min = time / 1000 / 60
        val sec = time / 1000 % 60

        timelable = "$min : "
        if(sec < 10) timelable += "0"
        timelable += sec

        return timelable
    }

}