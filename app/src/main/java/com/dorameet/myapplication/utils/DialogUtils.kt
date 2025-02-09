package com.dorameet.myapplication.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import android.widget.ImageView

import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import cn.agrinav.utils.DisplayUtil
import com.dorameet.myapplication.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * @author 11230
 */
class DialogUtils(private val context: Context) {
    private val dialogTextTitle: TextView
    private val dialogTextContent: TextView
    private val dialogBtnOk: MaterialButton
    private val dialogBtnCancel: MaterialButton
    private val view: View = View.inflate(context, R.layout.layout_dialog, null)
    val dialogClose: ImageView
    private var title = "提示"
    private var content = "该页面需要开启定位功能获取周围地块，请打开定位功能再使用"
    private var btnOk = "去设置"
    private var btnCancel = "取消"

    init {
        dialogTextTitle = view.findViewById<TextView>(R.id.dialog_title)
        dialogTextContent = view.findViewById<TextView>(R.id.dialog_content)
        dialogBtnOk = view.findViewById<MaterialButton>(R.id.dialog_button_yes)
        dialogBtnCancel = view.findViewById<MaterialButton>(R.id.dialog_button_no)
        dialogClose = view.findViewById<ImageView>(R.id.iv_close)
        //这里设置初始值
        dialogTextTitle.text = title
        dialogTextContent.text = content
        dialogBtnOk.text = btnOk
        dialogBtnCancel.text = btnCancel
    }

    fun setTitle(title: String): DialogUtils {
        this.title = title
        dialogTextTitle.text = title
        return this
    }

    fun setContent(content: String): DialogUtils {
        this.content = content
        dialogTextContent.text = content
        return this
    }

    fun setPositiveListener(listener: View.OnClickListener?): DialogUtils {
        dialogBtnOk.setOnClickListener(listener)
        return this
    }

    fun setNegativeListener(listener: View.OnClickListener?): DialogUtils {
        dialogBtnCancel.setOnClickListener(listener)
        return this
    }
    fun setCloseListener(listener: View.OnClickListener?): DialogUtils {
        dialogClose.setOnClickListener(listener)
        return this
    }

    fun setPositiveText(text: String): DialogUtils {
        btnOk = text
        dialogBtnOk.text = text
        return this
    }

    fun setNegativeText(text: String): DialogUtils {
        btnCancel = text
        dialogBtnCancel.text = text
        return this
    }


    val dialog: AlertDialog
        get() {
            val alertDialog = MaterialAlertDialogBuilder(context)
                .setView(view)
                .setCancelable(true)
                .create()
            val width: Int = DisplayUtil.dip2px(context, 340)
            val height: Int = DisplayUtil.dip2px(context, 390)
            // 获取弹窗的Window对象
            val window = alertDialog.window
//            window!!.setLayout(width, height)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)); // 设置背景为透明
//            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT); // 设置Dialog大小
            // 设置弹窗的宽度和高度
//            val params = window.attributes
//            // 设置弹窗宽度为屏幕宽度
//            params.width = width
//            // 设置弹窗高度为自适应内容
//            params.height = height
//            window.attributes = params
            return alertDialog
        }
}