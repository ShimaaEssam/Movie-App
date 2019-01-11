package com.shimaa.movies.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shimaa.movies.DetailActivity
import com.shimaa.movies.R
import com.shimaa.movies.model.Movie

class moviesAdapter(private val context: Context, private val movieList: List<Movie>) : Adapter<moviesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): moviesAdapter.MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.movie_card, viewGroup, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        myViewHolder.title.text = movieList[i].getOriginalTitle()
        val vote = java.lang.Double.toString(movieList[i].getvoteAverage())
        myViewHolder.userRating.text = vote
        //da 34an placeholder() kan feha problem
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.load)
        Glide.with(context)
                .load(movieList[i].getPosterPath())
                .apply(requestOptions)
                .into(myViewHolder.thumbnail)
    }


    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView
        var userRating: TextView
        var thumbnail: ImageView

        init {
            title = itemView.findViewById<View>(R.id.title) as TextView
            userRating = itemView.findViewById<View>(R.id.userrating) as TextView
            thumbnail = itemView.findViewById<View>(R.id.thumbnail) as ImageView

            itemView.setOnClickListener { view ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    val clickedDataItem = movieList[pos]
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("original_title", movieList[pos].getOriginalTitle())
                    intent.putExtra("poster_path", movieList[pos].getPosterPath())
                    intent.putExtra("overview", movieList[pos].getOverview())
                    intent.putExtra("vote_average", movieList[pos].getvoteAverage())
                    intent.putExtra("release_date", movieList[pos].getReleaseDate())
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                    Toast.makeText(view.context, "You clicked" + clickedDataItem.getOriginalTitle(), Toast.LENGTH_LONG).show()


                }
            }


        }
    }
}
