package com.dorameet.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.BitmapCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.drawToBitmap
import com.dorameet.myapplication.utils.ImageUtils
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {
    var avatarView: ImageView? = null
    var edt_name: EditText? = null
    var chipBoy: Chip? = null
    var chipGirl: Chip? = null
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

    private fun initAvatar() {
        //首先获取到图片
        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar)
        bitmap = ImageUtils().getCircleBitmap(bitmap)
        avatarView?.setImageBitmap(bitmap)
    }
    private fun initViews() {
        avatarView = findViewById<ImageView>(R.id.first_avatar);
        edt_name = findViewById<EditText>(R.id.edt_name)
        chipBoy = findViewById<Chip>(R.id.first_sex_boy)
        chipGirl = findViewById<Chip>(R.id.first_sex_girl)
    }

}