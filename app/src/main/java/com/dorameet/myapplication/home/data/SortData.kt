package com.dorameet.myapplication.home.data

/**
 * @author 11230
 */
class SortData {
    var id: Int = 0
    var type: String = ""

    constructor()
    constructor(id: Int, type: String) {
        this.id = id
        this.type = type
    }
}