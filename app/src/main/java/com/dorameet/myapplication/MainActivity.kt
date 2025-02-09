package com.dorameet.myapplication

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dorameet.myapplication.utils.ImageUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {
    var avatarView: ImageView? = null
    var edt_name: EditText? = null
    var chipBoy: Chip? = null
    var chipGirl: Chip? = null
    var bottomDialog: BottomSheetDialog? = null
    var bottomDialogView: View? = null
    var tvPhoto  :TextView? = null
    var tvGallery :TextView? = null
    var tvCancel  :TextView? = null
    private var sex = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.first_message_constraintLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()
        initEvents()
        initAvatar()
        initChips()
    }

    private fun initChips() {
        //设置一个全局变量用来表示选择的性别
        showChip()
        chipBoy?.setOnClickListener {
            sex = true
            showChip()
        }
        chipGirl?.setOnClickListener {
            sex = false
            showChip()
        }
    }

    private fun showChip() {
        if (sex) {
            chipBoy?.setChipStrokeColorResource(R.color.blue)
            chipBoy?.setChipStrokeWidthResource(R.dimen.border_small)
            chipGirl?.setChipStrokeColorResource(R.color.color_no)
            chipGirl?.setChipStrokeWidthResource(R.dimen.border_null)

        } else {
            chipGirl?.setChipStrokeColorResource(R.color.blue)
            chipGirl?.setChipStrokeWidthResource(R.dimen.border_small)
            chipBoy?.setChipStrokeColorResource(R.color.color_no)
            chipBoy?.setChipStrokeWidthResource(R.dimen.border_null)
        }
    }
    //初始化头像形状
    private fun initAvatar() {
        //首先获取到图片
        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar)
        bitmap = ImageUtils().getCircleBitmap(bitmap)
        //然后设置图片信息
        avatarView?.setImageBitmap(bitmap)
    }
    //初始化控件
    private fun initViews() {
        avatarView = findViewById<ImageView>(R.id.first_avatar);
        edt_name = findViewById<EditText>(R.id.edt_name)
        chipBoy = findViewById<Chip>(R.id.first_sex_boy)
        chipGirl = findViewById<Chip>(R.id.first_sex_girl)
        //弹出更换头像的底部弹窗
        bottomDialogView = LayoutInflater.from(this).inflate(R.layout.layout_change_bottom_sheet, null)
        bottomDialog =BottomSheetDialog(this, R.style.BottomSheetDialog)
        bottomDialog!!.setContentView(bottomDialogView!!)
        //设置底部弹窗中的内容
        tvPhoto = bottomDialogView!!.findViewById<TextView>(R.id.bottom_take_photo)
        tvGallery = bottomDialogView!!.findViewById<TextView>(R.id.bottom_gallery)
        tvCancel = bottomDialogView!!.findViewById<TextView>(R.id.bottom_cancel)

    }
    private fun initEvents(){
        avatarView?.setOnClickListener {
            //弹出弹窗
            bottomDialog!!.show()
        }
        tvPhoto?.setOnClickListener {
            //拍照，这里要询问用户是否允许拍照

        }
        tvGallery?.setOnClickListener {
            //相册,这里要询问用户是否允许打开相册
        }
        tvCancel?.setOnClickListener {
            //取消
            bottomDialog!!.dismiss()
        }
    }

}