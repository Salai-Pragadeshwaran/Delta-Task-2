package com.example.dotsandboxes

import android.os.Bundle
import android.os.Handler
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        score3.visibility = View.GONE
        score4.visibility = View.GONE

        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
           var size =  MyDrawView.linesLists.size
            if(size>0) {
                MyDrawView.linesLists.removeAt(size - 1)
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings ->{

                var animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
                scoreViewA.startAnimation(animation)

                return true
            }

            R.id.gridPlus -> {
                if(MyDrawView.n < 12){
                    MyDrawView.n++
                    MyDrawView.linesLists.clear()
                }
                return true
            }
            R.id.gridMinus -> {
                if(MyDrawView.n > 3){
                    MyDrawView.n--
                    MyDrawView.linesLists.clear()
                }
                return true
            }
            R.id.player2 -> {
                score3.visibility = View.GONE
                score4.visibility = View.GONE
                MyDrawView.linesLists.clear()
                MyDrawView.playerNumber = 2
                return true
            }
            R.id.player3 -> {
                score3.visibility = View.VISIBLE
                score4.visibility = View.GONE
                MyDrawView.linesLists.clear()
                MyDrawView.playerNumber = 3
                return true
            }
            R.id.player4 -> {
                score3.visibility = View.VISIBLE
                score4.visibility = View.VISIBLE
                MyDrawView.linesLists.clear()
                MyDrawView.playerNumber = 4
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    

}
