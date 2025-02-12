package com.dorameet.myapplication.home.data

class ArticleResponse {
    // Getters and Setters
    var total: Int = 0
    var list: List<ArticleData>? = null
    var pageNum: Int = 0
    var pageSize: Int = 0
    var size: Int = 0
    var startRow: Int = 0
    var endRow: Int = 0
    var pages: Int = 0
    var prePage: Int = 0
    var nextPage: Int = 0
    var isFirstPage: Boolean = false
    var isLastPage: Boolean = false
    var isHasPreviousPage: Boolean = false
    var isHasNextPage: Boolean = false
    var navigatePages: Int = 0
    var navigatepageNums: List<Int>? = null
    var navigateFirstPage: Int = 0
    var navigateLastPage: Int = 0
    private var lastPage = 0
    private var firstPage = 0

    fun getLastPage(): Int {
        return lastPage
    }

    fun setLastPage(lastPage: Int) {
        this.lastPage = lastPage
    }

    fun getFirstPage(): Int {
        return firstPage
    }

    fun setFirstPage(firstPage: Int) {
        this.firstPage = firstPage
    }
}