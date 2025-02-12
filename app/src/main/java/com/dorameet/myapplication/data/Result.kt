package com.dorameet.myapplication.data

/**
 * @author 11230
 */
class Result<T> {
    var code: Int = 0
    var msg: String? = null
    private var data: T? = null

    constructor()
    constructor(code: Int, msg: String?, data: T) {
        this.code = code
        this.msg = msg
        this.data = data
    }

    fun getData(): T? {
        return data
    }

    fun setData(data: T) {
        this.data = data
    }
}