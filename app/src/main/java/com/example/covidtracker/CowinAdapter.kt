package com.example.covidtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CowinAdapter():RecyclerView.Adapter<CowinHolder>() {
    private val items: ArrayList<Cowin> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CowinHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        val viewHolder = CowinHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(holder: CowinHolder, position: Int) {
        val currentItem = items[position]
        holder.state.text = currentItem.state
        holder.cases.text = currentItem.cases
        holder.recovered.text = currentItem.recovered
        holder.deaths.text = currentItem.deaths
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateCovid(updatedNews : ArrayList<Cowin>)   // update news is used to update the news on a timely basis or else the news would be the same everythime
    {
        items.clear()                // first all the items would be needed to be clear
        items.addAll(updatedNews)    //then the updated news will be added from updatedNews Array to items

        notifyDataSetChanged()     // this will notify adapter to change the news
    }

}

class CowinHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val state: TextView = itemView.findViewById(R.id.place)
    val cases: TextView = itemView.findViewById(R.id.newcases)
    val recovered: TextView = itemView.findViewById(R.id.recovered)
    val deaths: TextView = itemView.findViewById(R.id.deaths)

}