package cn.agrinav.utils

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import cn.agrinav.R
import com.dorameet.myapplication.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import lombok.Getter

/**
 * @author 11230
 */
class DialogUtils(private val context: Context) {
    private val dialogTextTitle: TextView
    private val dialogTextContent: TextView
    private val dialogBtnOk: Button
    private val dialogBtnCancel: Button
    private val view: View =
        View.inflate(context, R.layout.layout_dialog, null)
    private var title = "温馨提示"
    private var content = "该页面需要开启定位功能获取周围地块，请打开定位功能再使用"
    private var btnOk = "去设置"
    private var btnCancel = "取消"

    init {
        dialogTextTitle = view.findViewById<TextView>(R.id.dialog_text_title)
        dialogTextContent = view.findViewById<TextView>(R.id.dialog_text_content)
        dialogBtnOk = view.findViewById<Button>(R.id.dialog_button_yes)
        dialogBtnCancel = view.findViewById<Button>(R.id.dialog_button_no)
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
            val alertDialog = MaterialAlertDialogBuilder(
                context
            )
                .setCancelable(false)
                .show()
            val width: Int = DisplayUtil.dip2px(context, 340)
            val height: Int = DisplayUtil.dip2px(context, 390)
            // 获取弹窗的Window对象
            val window = alertDialog.window
            window!!.setLayout(width, height)
            // 设置弹窗的宽度和高度
            val params = window.attributes
            // 设置弹窗宽度为屏幕宽度
            params.width = width
            // 设置弹窗高度为自适应内容
            params.height = height
            window.attributes = params
            return alertDialog
        }
}