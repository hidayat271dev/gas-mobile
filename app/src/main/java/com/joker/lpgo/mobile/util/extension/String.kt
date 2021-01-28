package com.joker.lpgo.mobile.util.extension

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

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