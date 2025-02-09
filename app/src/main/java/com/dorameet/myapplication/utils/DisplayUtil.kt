package cn.agrinav.utils

import android.content.Context

/**
 * @author 11230
 */
object DisplayUtil {
    /**
     * px转化为dp或者dip
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        //得到换算比例
        val scale = context.resources.displayMetrics.density
        //加上0.5f是为了结果四舍五入
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * dip或者dp转换为px
     */
    fun dip2px(context: Context, dpValue: Int): Int {
        //得到换算比例
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * px转换为sp
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        //得到换算比例
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * sp转化为px
     */
    fun sp2px(context: Context, spValue: Float): Int {
        //得到换算比例
        val fontScale = context.resources.displayMetrics.density
        return (spValue * fontScale + 0.5f).toInt()
    }
}