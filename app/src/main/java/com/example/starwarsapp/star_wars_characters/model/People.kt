import com.google.gson.annotations.SerializedName

class People : Cloneable{

    @SerializedName("name")
    var name: String? = null

    @SerializedName("birth_year")
    var birthYear: String? = null

    var gender: String? = null

    @SerializedName("hair_color")
    var hairColor: String? = null

    var height: String? = null

    @SerializedName("homeworld")
    var homeWorldUrl: String? = null

    var mass: String? = null

    @SerializedName("skin_color")
    var skinColor: String? = null

    @SerializedName("created")
    var created: String? = null

    @SerializedName("edited")
    var edited: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("films")
    var filmsUrls: ArrayList<String>? = null

    @SerializedName("species")
    var speciesUrls: ArrayList<String>? = null

    @SerializedName("starships")
    var starshipsUrls: ArrayList<String>? = null

    @SerializedName("vehicles")
    var vehiclesUrls: ArrayList<String>? = null
}