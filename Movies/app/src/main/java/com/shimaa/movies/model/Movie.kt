package com.shimaa.movies.model

import com.google.gson.annotations.SerializedName

class Movie {
    @SerializedName("poster_path")
    private lateinit var posterPath:String
    @SerializedName("adult")
    private  var adult:Boolean = false
    @SerializedName("overview")
    private lateinit var overview:String
    @SerializedName("release_date")
    private lateinit var releaseDate:String
    @SerializedName("genre_ids")
    private var genreIds:List<Int> = ArrayList()
    @SerializedName("id")
    private  var id:Int=1
    @SerializedName("original_title")
    private lateinit var originalTitle:String
    @SerializedName("original_language")
    private lateinit var originalLanguage:String
    @SerializedName("title")
    private lateinit var title:String
    @SerializedName("backdrop_path")
    private lateinit var backdropPath:String
    @SerializedName("popularity")
    private var popularity:Double=0.0
    @SerializedName("vote_count")
    private  var voteCount:Int=1
    @SerializedName("video")
    private  var video:Boolean = false
    @SerializedName("vote_average")
    private var voteAverage:Double=0.0

    constructor(posterPath: String, adult: Boolean,overview:String,releaseDate:String,
                genreIds:List<Int>,id:Int,originalTitle:String, originalLanguage:String,
                title:String, backdropPath:String,popularity:Double,
                voteCount:Int,video:Boolean,voteAverage:Double) {
        this.posterPath=posterPath
        this.adult=adult
        this.overview=overview
        this.releaseDate=releaseDate
        this.genreIds=genreIds
        this.id=id
        this.originalTitle=originalTitle
        this.originalLanguage=originalLanguage
        this.title=title
        this.backdropPath=backdropPath
        this.popularity=popularity
        this.voteAverage=voteAverage
        this.voteCount=voteCount
        this.video=video
    }
    var baseImageUrl:String="https://image.tmdb.org/t/p/w500"
    fun getPosterPath():String{
        return baseImageUrl+posterPath
    }
    fun setPosterPath(posterPath: String){
        this.posterPath=posterPath
    }
    fun isAdult():Boolean{
        return adult
    }
    fun setAdult(adult: Boolean){
        this.adult=adult
    }
    fun getOverview():String{
        return overview
    }
    fun setOverview(overview:String){
        this.overview=overview
    }
    fun getReleaseDate():String{
        return releaseDate
    }
    fun setReleaseDate(releaseDate:String){
        this.releaseDate=releaseDate
    }
    fun getGenreIds():List<Int>{
        return genreIds
    }
    fun setGenreIds(genreIds:List<Int>){
        this.genreIds=genreIds
    }
    fun getOriginalTitle():String{
        return originalTitle
    }
    fun setOriginalTitle(originalTitle:String){
        this.originalTitle=originalTitle
    }
    fun getOriginalLanguage():String{
        return originalLanguage
    }
    fun setOriginalLanguage(originalLanguage:String){
        this.originalLanguage=originalLanguage
    }
    fun getTitle():String{
        return title
    }
    fun setTitle(title:String){
        this.title=title
    }
    fun getbackdropPath():String{
        return backdropPath
    }
    fun setbackdropPath(backdropPath:String){
        this.backdropPath=backdropPath
    }
    fun getpopularity():Double{
        return popularity
    }
    fun setpopularity(popularity:Double){
        this.popularity=popularity
    }
    fun getvoteCount():Int{
        return voteCount
    }
    fun setvoteCount(voteCount:Int){
        this.voteCount=voteCount
    }
    fun getvideo():Boolean{
        return video
    }
    fun setvideo(video:Boolean){
        this.video=video
    }
    fun getvoteAverage():Double{
        return voteAverage
    }
    fun setvoteAverage(voteAverage:Double){
        this.voteAverage=voteAverage
    }
}