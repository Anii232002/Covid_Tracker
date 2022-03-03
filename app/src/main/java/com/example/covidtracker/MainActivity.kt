package com.example.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: CowinAdapter //the keyword m is added infront of a variable if you ant to make ti accessible in any of the functions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchData()
        mAdapter = CowinAdapter()
        recyclerView.adapter = mAdapter
    }

    private  fun fetchData(){

        // Instantiate the RequestQueue.
        val url = "https://disease.sh/v3/covid-19/gov/India"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val newsJsonArray = it.getJSONArray("states")  // in our api there is a array of jsongroups(group of news articles) in which there are many jsonitems(each news) further in which..
                //.. each item has its own 4 attributes called title ,author ,url of the website and the image as a string in urlToImage . Each news has all these attributes
                val covidArray = ArrayList<Cowin>()   // here a newsArray is created of type Array list of NEws. News is the data class  which has the capacity to hold 4 attributes..
                // of each news  which  are parsed below
                for(i in 0 until newsJsonArray.length())
                {
                    val newsJsonObject = newsJsonArray.getJSONObject(i) //  each news item is received by the . operator  as each item is in newsJsonArray (JSONArray ie "articles")..
                    // now newsJsonObject has the attributes of each news article
                    val covid = Cowin(
                        newsJsonObject.getString("state"),   // attribute of each news article is received by again . operator in newsJsonObject.getString("ATTRIBUTE NAME")
                        newsJsonObject.getString("todayCases"),
                        newsJsonObject.getString("todayRecovered"),
                        newsJsonObject.getString("todayDeaths")
                    )
                    covidArray.add(covid)
                }
                mAdapter.updateCovid(covidArray) // here we are updating the news when the updated news comes
            },
            {


            }

        )


// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
}