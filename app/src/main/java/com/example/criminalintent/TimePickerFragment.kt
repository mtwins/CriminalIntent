//package com.example.criminalintent
//
//import android.app.DatePickerDialog
//import android.app.Dialog
//import android.os.Bundle
//import android.widget.DatePicker
//import androidx.fragment.app.DialogFragment
//import java.util.*
//
//private const val ARG_TIME = "time"
//
//class TimePickerFragment : DialogFragment() {
//
//    interface Callbacks {
//        fun onTimeSelected(date: Date)
//    }
//
//    companion object {
//        fun newInstance(date: Date): DatePickerFragment {
//            val args = Bundle().apply {
//                putSerializable(ARG_TIME, date)
//            }
//            return DatePickerFragment().apply {
//                arguments = args
//            }
//        }
//    }
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        //    return super.onCreateDialog(savedInstanceState)
//        val dateListener =
//            DatePickerDialog.OnDateSetListener { datePicker: DatePicker, year: Int, month: Int, day: Int ->
//                val resultDate: Date = GregorianCalendar(year, month, day).time
//                targetFragment?.let { fragment ->
//                    (fragment as Callbacks).onDateSelected(resultDate)
//                }
//            }
//        val date = arguments?.getSerializable(ARG_DATE) as Date
//        val calendar = Calendar.getInstance()
//        calendar.time = date
//        val initialYear = calendar.get(Calendar.YEAR)
//        val initialMonth = calendar.get(Calendar.MONTH)
//        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)
//        return TimePickerFragment(
//            requireContext(), dateListener, initialYear, initialMonth, initialDay
//        )
//    }
//
//
//}