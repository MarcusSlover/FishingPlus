package me.marcusslover.fishingplus.utils

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import java.io.*


class FileUtil {
    companion object Util {
        @JvmStatic
        private val jsonParser: JsonParser = JsonParser()

        @JvmStatic
        fun readJson(file: File, default: JsonElement): JsonElement {
            if (file.exists()) {
                val reader = JsonReader(FileReader(file))
                return jsonParser.parse(reader)
            }
            return default
        }

        @JvmStatic
        fun writeJson(file: File, value: JsonElement) {
            if (!file.parentFile.exists()) {
                if (!file.parentFile.mkdirs()) {
                    throw IOException("Couldn't create parent directory!")
                }
            }
            val writer = BufferedWriter(FileWriter(file))
            writer.write(JsonUtil.gson.toJson(value))
            writer.close()
        }
    }
}