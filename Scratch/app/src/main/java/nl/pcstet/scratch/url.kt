package nl.pcstet.scratch

import android.webkit.URLUtil

fun main() {
    val urlString = "https://bar.pcstet.nl"
    val urlIsValid = URLUtil.isValidUrl(urlString)
    print(urlIsValid)
}