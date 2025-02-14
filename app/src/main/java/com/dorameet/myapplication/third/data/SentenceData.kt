package com.dorameet.myapplication.third.data

class SentenceData {
    var word: String? = null
    var wb: Int? = null
    var we: Int? = null
    override fun toString(): String {
        return "{\"word\":\"$word\",\"wb\":$wb,\"we\":$we}"
    }
}