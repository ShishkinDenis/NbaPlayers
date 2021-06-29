package com.shishkin.itransition.gui.activities

import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shishkin.itransition.R
import com.shishkin.itransition.db.NbaPlayerDao
import com.shishkin.itransition.network.entities.NbaPlayer
import com.shishkin.itransition.network.entities.NbaTeam
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var nbaPlayerDao: NbaPlayerDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(this, R.id.host_fragment)
//        TODO bottom navigation view overlaps recycler view's last item
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        Thread {
            val nbaTeam = NbaTeam(1, "a", "b", "c", "d", "e", "f", 2, 3)
            val nbaPlayer = NbaPlayer(1, "NameFromDb", 2, 3, "b", "c", nbaTeam, 4)
            val list = ArrayList<NbaPlayer>()
            list.add(nbaPlayer)
            nbaPlayerDao.insertAllPlayers(list)
           Log.d("Retrofit", nbaPlayerDao.getAllPlayers()[0].firstName)
        }.start()


    }
}

