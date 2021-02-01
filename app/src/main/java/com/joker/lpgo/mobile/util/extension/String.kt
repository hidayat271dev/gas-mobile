package com.joker.lpgo.mobile.util.extension

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

internal fun String.converTimestampServerToDateTime(pattern: String) : String {
    var temp = ""
    if (this.length ==16 ) temp = this.plus(":00")
    if (this.length > 19 ) temp = this.substring(0,19)

    val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val patternFormatter = DateTimeFormatter.ofPattern(pattern)
    return patternFormatter.format(LocalDateTime.parse(temp, firstApiFormat))
}

internal fun String.convertStringToLocalDate() : LocalDate {
    var temp = ""
    if (this.length ==16 ) temp = this.plus(":00")
    if (this.length > 19 ) temp = this.substring(0,19)

    val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    return LocalDate.parse(temp , firstApiFormat)
}

internal fun String.getAddressInfo(context: Context, lat: Double, long: Double): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses: List<Address> = geocoder.getFromLocation(lat,long, 1)
    val cityName: String = addresses[0].getAddressLine(0)

    return cityName
}