package com.example.criminal_intent.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.criminal_intent.ui.viewmodel.CrimeListViewModel
import com.example.criminal_intent.R
import com.example.criminal_intent.dao.CrimeRepository
import com.example.criminal_intent.database.DatabaseManager
import com.example.criminal_intent.model.Crime
import com.example.criminal_intent.ui.adapter.CrimeAdapter
import com.example.criminal_intent.ui.viewmodel.CrimeListViewModelFactory

class CrimeListFragment: Fragment() {

    private lateinit var crimeRecyclerView: RecyclerView
    private lateinit var searchCrime: EditText
    private var adapter: CrimeAdapter? = null

    private val crimeListViewModel: CrimeListViewModel by lazy {
        val database = DatabaseManager.getDatabase(requireNotNull(this.activity).applicationContext)
        val repository = CrimeRepository(database.crimeDao())
        val factory = CrimeListViewModelFactory(repository)
        ViewModelProvider(this, factory).get(CrimeListViewModel::class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView  = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter


        searchCrime = view.findViewById(R.id.search_crime)
        searchCrime.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter?.filter?.filter(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    updateUI(crimes)
                }
            })
    }

    private fun updateUI(crimes: List<Crime>) {
        adapter = CrimeAdapter(crimes) { crime ->
            // 这里是点击事件的处理逻辑
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, CrimeFragment.newInstance(crime.id))
            transaction.addToBackStack(null)
            transaction.commit()
        }
        crimeRecyclerView.adapter = adapter
    }
}