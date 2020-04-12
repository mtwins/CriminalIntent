package com.example.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "CRIME LIST FRAGMENT"

class CrimeListFragment : Fragment() {
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this).get(CrimeListViewModel::class.java)
    }
    private var adapter: CrimeAdapter? = null
    private lateinit var crimeRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total Crimes : ${crimeListViewModel.crimes.size}")
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
    private fun updateUI(){
        val crimes= crimeListViewModel.crimes
        adapter=CrimeAdapter(crimes)
        crimeRecyclerView.adapter=adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        updateUI()
        return view
    }
    private inner class CrimeHolder(view:View): RecyclerView.ViewHolder(view), View.OnClickListener{

        val titleTextView: TextView= itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView= itemView.findViewById(R.id.crime_date)
        private lateinit var crime: Crime
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(crime:Crime){
            this.crime=crime
            titleTextView.text=crime.title
            dateTextView.text=crime.date.toString()
        }

        override fun onClick(v: View?) {
            Toast.makeText(context,"${crime.title} pressed", Toast.LENGTH_SHORT).show()
        }
    }
    private inner class CrimeAdapter(var crimes:List<Crime>): RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
           val view= layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        override fun getItemCount(): Int =crimes.size

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
           var crime= crimes[position]
            holder.bind(crime)
        }

    }
}