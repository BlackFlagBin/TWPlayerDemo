package com.thoughtworks.twplayer

class TWDataSource {

    companion object {
        val URL_KEY_DEFAULT = "URL_KEY_DEFAULT"
    }

    var mUrlsMap = LinkedHashMap<String, String>()
    var mCurrentUrlIndex = 0
    var mLooping = false

    fun getCurrentUrl(): String? {
        var index = 0
        mUrlsMap.map {
            if (index == mCurrentUrlIndex) {
                return it.value
            }
            index++
        }

        return null
    }
}