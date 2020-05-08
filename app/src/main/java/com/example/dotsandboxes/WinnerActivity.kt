package com.example.dotsandboxes

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_winner.*

class WinnerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_winner)
            var msg = getIntent().getStringExtra("WIN_MSG")
        if(msg!="Match Tie") {
            var winSound = MediaPlayer.create(this, R.raw.mario)
            winSound.start()
        }
        else{
            var winSound = MediaPlayer.create(this, R.raw.game_over_sound)
            winSound.start();
        }
            winMsg.text = msg
            Handler().postDelayed({

                finish()
            }, 7500)

    }
}
