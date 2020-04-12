package com.example.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*
private const val TAG="MAIN ACTIVITY"
class MainActivity : AppCompatActivity() , CrimeListFragment.Callbacks{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentFragment= supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(currentFragment==null){
            val crimeFragment= CrimeListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, crimeFragment)
                .commit()
        }
    }

    override fun onCrimeSelected(crimeId: UUID) {
        Log.d(TAG," on crime selected $crimeId")
        val fragment=CrimeFragment.newInstance(crimeId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
