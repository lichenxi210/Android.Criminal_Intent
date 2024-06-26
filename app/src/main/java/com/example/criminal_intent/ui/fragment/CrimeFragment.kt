package com.example.criminal_intent.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.criminal_intent.R
import com.example.criminal_intent.dao.CrimeRepository
import com.example.criminal_intent.database.DatabaseManager
import com.example.criminal_intent.model.Crime
import com.example.criminal_intent.ui.viewmodel.CrimeListViewModel
import com.example.criminal_intent.ui.viewmodel.CrimeListViewModelFactory
import java.util.*

class CrimeFragment:Fragment() {
    private lateinit var crime: Crime
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox

    private val crimeListViewModel: CrimeListViewModel by lazy {
        val database = DatabaseManager.getDatabase(requireNotNull(this.activity).applicationContext)
        val repository = CrimeRepository(database.crimeDao())
        val factory = CrimeListViewModelFactory(repository)
        ViewModelProvider(this, factory).get(CrimeListViewModel::class.java)
    }

    companion object {
        private const val ARG_CRIME_ID = "crime_id"

        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeId)

            val fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View? {
        val view = inflater.inflate(R.layout.fragment_crime, container, false)//存疑

        // Find views
         titleField = view.findViewById(R.id.crime_title) as EditText
         dateButton = view.findViewById(R.id.crime_date) as Button
         solvedCheckBox = view.findViewById(R.id.crime_solved) as CheckBox

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val crimeId = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        crimeListViewModel.getCrime(crimeId)

        crimeListViewModel.crimeDetail.observe(viewLifecycleOwner, { loadedCrime ->
            loadedCrime?.let {
                this.crime = it
                updateUI(it)
            }
        })
    }

    private fun updateUI(crime: Crime) {
        // 用 crime 对象更新界面
        titleField.setText(crime.title)
        dateButton.text = crime.date.toString()
        dateButton.apply {
            text = crime.date.toString()
            isEnabled = false
        }
        solvedCheckBox.isChecked = crime.isSolved
    }

}

