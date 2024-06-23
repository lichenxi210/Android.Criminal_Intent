package com.example.criminal_intent.ui.adapter

import android.util.Log
import android.widget.Filter
import android.widget.Filterable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.criminal_intent.R
import com.example.criminal_intent.model.Crime
import java.util.*

class CrimeAdapter(private var crimes: List<Crime>) : RecyclerView.Adapter<CrimeAdapter.CrimeHolder>(), Filterable {

    private var crimeListFull: List<Crime> = ArrayList(crimes)
    init {
        Log.d("com.example.criminal_intent.ui.adapter.CrimeAdapter", "com.example.criminal_intent.ui.adapter.CrimeAdapter initialized with ${crimes.size} items")
    }

    inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_crime, parent, false)
        return CrimeHolder(view)
    }

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crimes[position]
        holder.titleTextView.text = crime.title
        holder.dateTextView.text = crime.date.toString()
    }

    override fun getItemCount(): Int {
        return crimes.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val filteredList = ArrayList<Crime>()

                if (charSequence == null || charSequence.isEmpty()) {
                    filteredList.addAll(crimeListFull)
                } else {
                    val filterPattern = charSequence.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }

                    for (crime in crimeListFull) {
                        if (crime.title.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                            filteredList.add(crime)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList

                return results
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                crimes = filterResults?.values as List<Crime>
                notifyDataSetChanged()
            }
        }
    }
}
