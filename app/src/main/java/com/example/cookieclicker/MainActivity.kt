package com.example.cookieclicker

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookieclicker.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: ItemAdapter
    private lateinit var items: List<Item>
    lateinit var clickerButton: Button
    lateinit var totalPoints: TextView
    lateinit var pointsPerSecond: TextView
    lateinit var pointsPerClick: TextView

    companion object {
        var points = 0
        var clickBonus = 1
        var autoClick = 0
        var check = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickerButton = findViewById(R.id.button_main_buttonClicker)
        totalPoints = findViewById(R.id.textView_main_totalPoints)
        pointsPerSecond = findViewById(R.id.textView_main_pointsPerSecond)
        pointsPerClick = findViewById(R.id.textView_main_pointsPerClick)

        val inputStream = resources.openRawResource(R.raw.items)
        val jsonString = inputStream.bufferedReader().use {
            it.readText()
        }

//        Log.d(TAG, "onCreate: $jsonString")

        val gson = Gson()
        val type = object : TypeToken<List<Item>>() {}.type
        items = gson.fromJson<List<Item>>(jsonString, type)
        items = items.sortedBy { it.price }

        // sortedBy can do your custom sorting needs but it's good to have
        // a default sorted method

//        Log.d(TAG, "loadHeroes: $heroes")

        // link the adapter to the recyclerview and give it the list
        adapter = ItemAdapter(items, this)
        binding.recyclerViewMainItems.adapter = adapter
        binding.recyclerViewMainItems.layoutManager = LinearLayoutManager(this)

        val counterThread = Thread {
            try {
                while (true) {
                    points = points + autoClick
                    totalPoints.text = "Points: $points"

                    // tried to put achievement check in here but it kept breaking
                    // the autoclicks so I moved it to the button onClickListener

                    Thread.sleep(1000)
                }
            } catch (e: Exception) {
            }
        }
        counterThread.start()

        clickerButton.setOnClickListener {
            points = points + clickBonus
            totalPoints.text = "Points: $points"

            if (points >= 100 && check == 0) {
                Toast.makeText(this, "100 points!", Toast.LENGTH_SHORT).show()
                check++
            }
            if (points >= 1000 && check == 1) {
                Toast.makeText(this, "1000 points!", Toast.LENGTH_SHORT).show()
                check++
            }
            if (points >= 10000 && check == 2) {
                Toast.makeText(this, "10000 points!", Toast.LENGTH_SHORT).show()
                check++
            }
            if (points >= 100000 && check == 3) {
                Toast.makeText(this, "100000 points!", Toast.LENGTH_SHORT).show()
                check++
            }
            if (points >= 1000000 && check == 4) {
                Toast.makeText(this, "1000000 points!", Toast.LENGTH_SHORT).show()
                check++
            }
            if (points >= 10000000 && check == 5) {
                Toast.makeText(this, "10000000 points!", Toast.LENGTH_SHORT).show()
                check++
            }
        }
    }

    fun updateDisplay() {
        totalPoints.text = "Points: $points"
        pointsPerSecond.text = "PPS: $autoClick"
        pointsPerClick.text = "PPC: $clickBonus"
    }

    fun addBonuses(click : String, auto : String) {
        if (click.substring(0, 1) == "+")
            clickBonus += click.substring(1).toInt()
        if (click.substring(0, 1) == "*")
            clickBonus *= click.substring(1).toInt()
        if (auto.substring(0, 1) == "+")
            autoClick += auto.substring(1).toInt()
        if (auto.substring(0, 1) == "*")
            autoClick *= auto.substring(1).toInt()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater: MenuInflater = menuInflater
//        inflater.inflate(R.menu.sort_menu, menu)
//        return true
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.sort_by_name -> {
//                adapter.heroesList = adapter.heroesList.sortedBy { it.name }
//                adapter.notifyDataSetChanged()
//                return true
//            }
//            R.id.sort_by_ranking -> {
//                adapter.heroesList = adapter.heroesList.sorted()
//                adapter.notifyDataSetChanged()
//                return true
//            }
//            R.id.sort_by_descLength -> {
//                adapter.heroesList = adapter.heroesList.sortedBy { it.description.length }
//                adapter.notifyDataSetChanged()
//                return true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}