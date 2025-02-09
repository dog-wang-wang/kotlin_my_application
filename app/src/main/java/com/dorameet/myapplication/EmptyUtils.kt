package com.dorameet.myapplication

import java.io.File

/**
 * @author 11230
 */
object EmptyUtils {
    fun isEmpty(str: String?): Boolean {
        return str.isNullOrEmpty()
    }

    fun isEmpty(list: Collection<*>?): Boolean {
        return list == null || list.isEmpty()
    }

    fun fileIsExist(imgLocalAddress: String?): Boolean {
        val avatarFile = imgLocalAddress?.let { File(it) }
        return avatarFile?.exists() ?: false
    }
}