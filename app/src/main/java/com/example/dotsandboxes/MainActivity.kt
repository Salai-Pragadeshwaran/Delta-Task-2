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
import android.widget.Toast

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

           var size =  MyDrawView.linesLists.size
            if(size>0) {
                if((MyDrawView.singlePlayerMode)&&(MyDrawView.linesLists[size-1].direction!=0)){
                    MyDrawView.availableOptions.add(MyDrawView.linesLists[size - 1])
                    MyDrawView.linesLists.removeAt(size - 1)
                }
                else if(MyDrawView.singlePlayerMode) {
                    do {
                        MyDrawView.availableOptions.add(MyDrawView.linesLists[size - 1])
                        MyDrawView.linesLists.removeAt(size - 1)
                        size = MyDrawView.linesLists.size
                    }while(MyDrawView.linesLists[size-1].direction!=0)
                    MyDrawView.availableOptions.add(MyDrawView.linesLists[size - 1])
                    MyDrawView.linesLists.removeAt(size - 1)
                }
                else {
                    MyDrawView.linesLists.removeAt(size - 1)
                }
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

                MyDrawView.linesLists.clear()
                MyDrawView.availableOptions.clear()
                MyDrawView.chainsFormed = false
                MyDrawView.continueStreak = false
                MyDrawView.gameOn = true
                return true
            }

            R.id.gridPlus -> {
                if(MyDrawView.n < 12){
                    MyDrawView.n++
                    MyDrawView.linesLists.clear()
                    MyDrawView.availableOptions.clear()
                    MyDrawView.gameOn = true
                    MyDrawView.continueStreak = false
                    MyDrawView.chainsFormed = false
                }
                return true
            }
            R.id.gridMinus -> {
                if(MyDrawView.n > 3){
                    MyDrawView.n--
                    MyDrawView.linesLists.clear()
                    MyDrawView.availableOptions.clear()
                    MyDrawView.gameOn = true
                    MyDrawView.continueStreak = false
                    MyDrawView.chainsFormed = false
                }
                return true
            }
            R.id.player2 -> {
                score3.visibility = View.GONE
                score4.visibility = View.GONE
                MyDrawView.linesLists.clear()
                MyDrawView.playerNumber = 2
                MyDrawView.gameOn = true
                MyDrawView.singlePlayerMode = false
                return true
            }
            R.id.player3 -> {
                score3.visibility = View.VISIBLE
                score4.visibility = View.GONE
                MyDrawView.linesLists.clear()
                MyDrawView.playerNumber = 3
                MyDrawView.gameOn = true
                MyDrawView.singlePlayerMode = false
                return true
            }
            R.id.player4 -> {
                score3.visibility = View.VISIBLE
                score4.visibility = View.VISIBLE
                MyDrawView.linesLists.clear()
                MyDrawView.playerNumber = 4
                MyDrawView.gameOn = true
                MyDrawView.singlePlayerMode = false
                return true
            }
            R.id.level_1 -> {
                score3.visibility = View.GONE
                score4.visibility = View.GONE
                MyDrawView.linesLists.clear()
                MyDrawView.availableOptions.clear()
                MyDrawView.playerNumber = 2
                MyDrawView.gameOn = true
                MyDrawView.singlePlayerMode = true
                Toast.makeText(this, "Switched to Single Player Mode - difficulty level 1", Toast.LENGTH_SHORT).show()
                MyDrawView.difficultyLevel = 1
                MyDrawView.continueStreak = false
                MyDrawView.chainsFormed = false
                return true
            }
            R.id.level_2 -> {
                score3.visibility = View.GONE
                score4.visibility = View.GONE
                MyDrawView.linesLists.clear()
                MyDrawView.availableOptions.clear()
                MyDrawView.playerNumber = 2
                MyDrawView.gameOn = true
                MyDrawView.singlePlayerMode = true
                Toast.makeText(this, "Switched to Single Player Mode - difficulty level 2", Toast.LENGTH_SHORT).show()
                MyDrawView.difficultyLevel = 2
                MyDrawView.continueStreak = false
                MyDrawView.chainsFormed = false
                return true
            }

            R.id.level_3 -> {
                score3.visibility = View.GONE
                score4.visibility = View.GONE
                MyDrawView.linesLists.clear()
                MyDrawView.availableOptions.clear()
                MyDrawView.playerNumber = 2
                MyDrawView.gameOn = true
                MyDrawView.singlePlayerMode = true
                Toast.makeText(this, "Switched to Single Player Mode - difficulty level 3", Toast.LENGTH_SHORT).show()
                MyDrawView.difficultyLevel = 3
                MyDrawView.continueStreak = false
                MyDrawView.chainsFormed = false
                return true
            }

            R.id.level_4 -> {
                score3.visibility = View.GONE
                score4.visibility = View.GONE
                MyDrawView.linesLists.clear()
                MyDrawView.availableOptions.clear()
                MyDrawView.playerNumber = 2
                MyDrawView.gameOn = true
                MyDrawView.singlePlayerMode = true
                Toast.makeText(this, "Switched to Single Player Mode - difficulty level 4", Toast.LENGTH_SHORT).show()
                MyDrawView.difficultyLevel = 4
                MyDrawView.continueStreak = false
                MyDrawView.chainsFormed = false
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    

}
