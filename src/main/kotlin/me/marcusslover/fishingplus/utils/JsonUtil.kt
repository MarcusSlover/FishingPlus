package me.marcusslover.fishingplus.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.marcusslover.fishingplus.fisher.Fisher
import me.marcusslover.fishingplus.fisher.FisherSerializer

class JsonUtil {
    companion object Util {
        @JvmStatic
        val gson: Gson = GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(Fisher::class.java, FisherSerializer())
            .create()
    }
}