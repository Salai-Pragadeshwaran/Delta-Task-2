package com.example.dotsandboxes

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

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
            R.id.action_settings -> true
            R.id.gridPlus -> {
                if(MyDrawView.n < 12){
                    MyDrawView.n++
                    MyDrawView.linesLists.clear();
                }
                return true
            }
            R.id.gridMinus -> {
                if(MyDrawView.n > 3){
                    MyDrawView.n--
                    MyDrawView.linesLists.clear();
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    

}
