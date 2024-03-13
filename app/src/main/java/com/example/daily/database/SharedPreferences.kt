import android.content.Context
import android.content.SharedPreferences
import com.example.daily.ui.Home.ContentModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Preferences(private var sharedPreferences: SharedPreferences? = null) {
    private val gson = Gson()

    fun setString(key: String, string: String) {
        sharedPreferences?.edit()?.putString(key, string)?.apply()
    }

    fun setBoolean(key: String, boolean: Boolean) {
        sharedPreferences?.edit()?.putBoolean(key, boolean)?.apply()
    }

    fun setInt(key: String, int: Int) {
        sharedPreferences?.edit()?.putInt(key, int)?.apply()
    }

    fun setFloat(key: String, float: Float) {
        sharedPreferences?.edit()?.putFloat(key, float)?.apply()
    }

    fun setLong(key: String, long: Long) {
        sharedPreferences?.edit()?.putLong(key, long)?.apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences?.getString(key, null)
    }

    fun getBoolean(key: String): Boolean? {
        return sharedPreferences?.getBoolean(key, false)
    }

    fun getInt(key: String): Int? {
        return sharedPreferences?.getInt(key, 0)
    }

    fun getFloat(key: String): Float? {
        return sharedPreferences?.getFloat(key, 0f)
    }

    fun getLong(key: String): Long? {
        return sharedPreferences?.getLong(key, 0L)
    }

    fun saveList(key: String, list: List<String>?) {
        val json = gson.toJson(list)
        sharedPreferences?.edit()?.putString(key, json)?.apply()
    }

    fun getList(key: String): List<String>? {
        val json = sharedPreferences?.getString(key, null)
        return gson.fromJson(json, object : TypeToken<List<String>>() {}.type)
    }

    companion object {
        private const val PREFS_NAME = "share_prefs"
        private var INSTANCE: Preferences? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(Preferences::class.java) {
            INSTANCE ?: Preferences(
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            )
                .also { INSTANCE = it }
        }
    }
}
