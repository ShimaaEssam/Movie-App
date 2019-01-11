package com.shimaa.movies

import android.app.Activity
import android.app.ProgressDialog
import android.app.Service
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Switch
import android.widget.Toast
import com.shimaa.movies.adapter.moviesAdapter
import com.shimaa.movies.api.Client
import com.shimaa.movies.model.Movie
import com.shimaa.movies.model.moviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.ArrayList


class MainActivity : AppCompatActivity() , SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var recyclerView: RecyclerView//extends ViewGroup implements ScrollingView, NestedScrollingChild2
    private lateinit var adapter: moviesAdapter
    private lateinit var movieList: List<Movie>
    private lateinit var pd: ProgressDialog//extends AlertDialog
    private lateinit var swipeContainer: SwipeRefreshLayout//whenever the user can refresh the contents of a view via a vertical swipe gesture.
    val LOG_TAG = moviesAdapter::class.java.name//method will return the full package plus class name of that class as a string,

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        swipeContainer = findViewById(R.id.main_content) as SwipeRefreshLayout
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark)
        swipeContainer.setOnRefreshListener {
            initViews()
            Toast.makeText(this@MainActivity, "Movies Refreshed", Toast.LENGTH_LONG).show()
        }
    }

    fun getActivity(): Activity? {
        var context: Context = this
        while (context is ContextWrapper) {//wrapper 7aga fl gradle
            if (context is Activity) {
                return context
            }
            context = context.baseContext//base context 7alt el context eli hwa el syak bta3y
        }
        return null
    }

    private fun initViews() {
        pd = ProgressDialog(this)
        pd.setMessage("Fetching movies ...")
        pd.setCancelable(false)
        pd.show()
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        movieList=ArrayList<Movie>()
        adapter = moviesAdapter(this@MainActivity, movieList)
        if (getActivity()!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {//law law el mopile m3dol(bnst5dmha 34an a3rf el orientation bta3y
            recyclerView.setLayoutManager(GridLayoutManager(this, 2))//change number of columns using gridlayout manager

        } else {
            recyclerView.setLayoutManager(GridLayoutManager(this, 4))//law mayl

        }
        recyclerView.setItemAnimator(DefaultItemAnimator())//This class defines the animations that take place on items as changes are made to the adapter.
        recyclerView.adapter=adapter
        adapter.notifyDataSetChanged()
        checkSortOrder()


    }

    private fun loadJSON() {
        try{
            if(BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(this@MainActivity,"please obtain API Key firstly from themoviedb.org",Toast.LENGTH_LONG).show()
                pd.dismiss()
                return
            }
            var client = Client()
             var apiService = client.getClient()!!.create(com.shimaa.movies.api.Service::class.java)
            var call = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN)
            call.enqueue(object : Callback<moviesResponse> {
                override fun onFailure(call: Call<moviesResponse>?, t: Throwable?) {
                    Log.d("Error", t!!.message)
                    Toast.makeText(this@MainActivity,"Error Fetching Data!",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<moviesResponse>?, response: Response<moviesResponse>?) {
                    var movies: List<Movie> = response!!.body().getResults()
                    recyclerView.adapter = moviesAdapter(this@MainActivity, movies)
                    recyclerView.smoothScrollToPosition(0)
                    if (swipeContainer.isRefreshing) {
                        swipeContainer.isRefreshing = false
                    }
                    pd.dismiss()
                }

            })
            }
        catch (ex:Exception) {
            Log.d("Error", ex.message)
            Toast.makeText(this@MainActivity,ex.toString(),Toast.LENGTH_LONG).show()

        }
    }
    private fun loadJSON1() {
        try{
            if(BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(this@MainActivity,"please obtain API Key firstly from themoviedb.org",Toast.LENGTH_LONG).show()
                pd.dismiss()
                return
            }
            var client = Client()
            var apiService = client.getClient()!!.create(com.shimaa.movies.api.Service::class.java)
            var call = apiService.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN)
            call.enqueue(object : Callback<moviesResponse> {
                override fun onFailure(call: Call<moviesResponse>?, t: Throwable?) {
                    Log.d("Error", t!!.message)
                    Toast.makeText(this@MainActivity,"Error Fetching Data!",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<moviesResponse>?, response: Response<moviesResponse>?) {
                    var movies: List<Movie> = response!!.body().getResults()
                    recyclerView.adapter = moviesAdapter(this@MainActivity, movies)
                    recyclerView.smoothScrollToPosition(0)
                    if (swipeContainer.isRefreshing) {
                        swipeContainer.isRefreshing = false
                    }
                    pd.dismiss()
                }

            })
        }
        catch (ex:Exception) {
            Log.d("Error", ex.message)
            Toast.makeText(this@MainActivity,ex.toString(),Toast.LENGTH_LONG).show()

        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_settings -> {
                var intent:Intent=Intent(this@MainActivity,SettingsActivity::class.java)
                startActivity(intent)
                return true}
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        Log.d(LOG_TAG,"Preferences updated")
        checkSortOrder()
    }

    private fun checkSortOrder() {
        var preferences:SharedPreferences=PreferenceManager.getDefaultSharedPreferences(this)
        var sortOrder:String=preferences.getString(
                this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.pref_most_popular))
        if(sortOrder.equals(this.getString(R.string.pref_most_popular))){
            Log.d(LOG_TAG,"Sorting by most popular")
            loadJSON()
        }else{
            Log.d(LOG_TAG,"Sorting by vote average")
            loadJSON1()
        }
    }

    override fun onResume() {
        super.onResume()
        if(movieList.isEmpty()){
            checkSortOrder()
        }else{

        }
    }
}

