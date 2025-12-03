package nl.pcstet.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import nl.pcstet.core.model.AppTheme
import nl.pcstet.core.model.UserPreferences
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<UserPreferences> {
    override val defaultValue = UserPreferences()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        return try {
            Json.decodeFromString(
                deserializer = UserPreferences.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(
        t: UserPreferences,
        output: OutputStream
    ) {
        output.write(
            Json.encodeToString(
                serializer = UserPreferences.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

}