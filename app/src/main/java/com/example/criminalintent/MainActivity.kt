package com.example.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentFragment= supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(currentFragment==null){
            val crimeFragment= CrimeFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, crimeFragment)
                .commit()
        }
    }
}
