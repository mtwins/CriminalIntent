package com.example.criminalintent

import android.nfc.Tag
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.util.*

private const val ARG_CRIME_ID="crime_id"
private const val DIALOG_DATE="DialogDate"
private const val REQUEST_DATE=0
class CrimeFragment : Fragment() , DatePickerFragment.Callbacks{
    private lateinit var crime: Crime
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckedBox: CheckBox
    private val crimeDetailViewModel: CrimeDetailViewModel by lazy{
        ViewModelProvider(this).get(CrimeDetailViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()
        val crimeId:UUID= arguments?.getSerializable(ARG_CRIME_ID) as UUID
        crimeDetailViewModel.loadCrime(crimeId)

        //evenutally load from db
    }

    override fun onStop() {
        super.onStop()
        crimeDetailViewModel.saveCrime(crime)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeDetailViewModel.crimeLiveDate.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                crime-> crime?.let {
                this.crime=crime
                updateUI()
            }
            })
    }

    private fun updateUI(){
        titleField.setText(crime.title)
        dateButton.text =crime.date.toString()
        solvedCheckedBox.apply {
            isChecked=crime.isSolved
            jumpDrawablesToCurrentState()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime, container, false)
        titleField = view.findViewById(R.id.crime_title) as EditText
        dateButton = view.findViewById(R.id.crime_date) as Button
//        dateButton.apply {
//            text = crime.date.toString()
//            isEnabled = false
//        }
        solvedCheckedBox = view.findViewById(R.id.crime_solved) as CheckBox

        return view
    }
    companion object{

        fun newInstance(crimeId: UUID): CrimeFragment{
            val args =Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }
            return CrimeFragment().apply {
                arguments=args
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //all listeners in one place
        //anonymous class to add a textwatcher instance but overrmid thheon TextChange funtion
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crime.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }


        }
        dateButton.setOnClickListener{
            DatePickerFragment.newInstance(crime.date).apply {
                setTargetFragment(this@CrimeFragment, REQUEST_DATE)
                show(this@CrimeFragment.parentFragmentManager, DIALOG_DATE)
            }
        }
        //all properties for solved check box in apply
        solvedCheckedBox.apply {
            setOnCheckedChangeListener { _, isChecked ->
                crime.isSolved = true
            }
        }
        titleField.addTextChangedListener(titleWatcher)
    }

    override fun onDateSelected(date: Date) {
        crime.date=date
        updateUI()
    }
}