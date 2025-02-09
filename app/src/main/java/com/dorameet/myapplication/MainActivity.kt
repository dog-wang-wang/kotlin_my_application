package com.dorameet.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.dorameet.myapplication.utils.DialogUtils
import com.dorameet.myapplication.utils.ImageUtils
import com.dorameet.myapplication.utils.PermissionUtils
import com.dorameet.myapplication.utils.PermissionUtils.CheckPermissionListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


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
    //注册跳转相册和相机的请求码
    private val REQUEST_CODE_CAMERA = 100
    private val REQUEST_CODE_GALLERY = 101
    private val cameraLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            // 使用之前创建的uri
            val data = result.data
            if (data != null) {
                val extras = data.extras
                val bitmap = extras!!["data"] as Bitmap?
                //把这张照片存入外存
                saveAvatar(bitmap)
                //然后重载头像
                if(avatarView!=null) {
                    Glide.with(this)
                        .load(bitmap)
                        .apply(RequestOptions.circleCropTransform())
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .into(avatarView!!)
                }
            }
        }
    }
    private val albumLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            // 获取图片
            val uri = result.data!!.data
            try {
                // 通过URL获得一个BitMap
                val bitmap =MediaStore.Images.Media.getBitmap(contentResolver, uri)
                //把这张照片存入外存
                saveAvatar(bitmap)
                if(avatarView!=null) {
                    Glide.with(this)
                        .load(bitmap)
                        .apply(RequestOptions.circleCropTransform())
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .into(avatarView!!)
                }

            } catch (e: IOException) {
                Log.e("MainActivity", "获取相册图片失败")
            }
        }
    }

    private fun saveAvatar(bitmap: Bitmap?) {
        val imageAddress = getExternalFilesDir(null)!!.absolutePath + "/"+getString(R.string.user_avatar)
        //然后把bitmap存入这个地方
        val out = FileOutputStream(imageAddress)
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.flush()
        out.close()
    }

    private fun loadAvatar() {
        val imgLocalAddress = getExternalFilesDir(null)!!.absolutePath + "/"+getString(R.string.user_avatar)
        //首先检验本地目录是否存有这张头像
        if (EmptyUtils.fileIsExist(imgLocalAddress)) {
            if(avatarView!=null) {
                Glide.with(this)
                    .load(imgLocalAddress)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(avatarView!!)
            }
        } else {
            //获取到网络图片还要在本地存储一份
            if(avatarView!=null) {
                Glide.with(this)
                    .load(R.drawable.avatar)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(avatarView!!)
            }
        }
    }

    private var snackBarGallery:Snackbar? = null
    private var snackBarCamera:Snackbar? = null
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
        var bitmap = BitmapFactory.decodeFile(getExternalFilesDir(null)!!.absolutePath + "/"+getString(R.string.user_avatar))
        bitmap = ImageUtils().getCircleBitmap(bitmap)
        //然后设置图片信息
        avatarView?.setImageBitmap(bitmap)
//        loadAvatar()
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
        //TODO：在这里要跳转到手势密码绑定页面
        //如果不可以使用指纹就直接强制绑定手势密码，跳转到手势密码绑定页面
        val dialogUtilPhoto: DialogUtils = DialogUtils(this)
        val dialogPhoto: AlertDialog = dialogUtilPhoto.dialog
        dialogUtilPhoto.setTitle("提示")
            .setContent("适趣我爱说申请访问相机权限用于修改头像")
            .setPositiveText("确定")
            .setNegativeText("取消")
            .setPositiveListener {
                dialogPhoto.dismiss()
                if (bottomDialog != null) {
                    bottomDialog!!.dismiss()
                }
                //这里去申请相机权限
                snackBarCamera =  PermissionUtils.checkPermission(findViewById(R.id.snack_bar_container_work), this,
                    arrayListOf(Manifest.permission.CAMERA),
                    object : CheckPermissionListener {
                        override fun onHavePermission() {
                            cameraLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE))
                        }

                        override fun noHavePermission(snackbar: Snackbar?) {
                            //没有权限就会自动去申请权限
                        }
                    },
                    REQUEST_CODE_CAMERA,
                    "适趣我爱说申请访问相机权限用于修改头像"
                    )

            }
            .setNegativeListener {
                //不给权限不让用这个功能，结束这个弹窗
                dialogPhoto.dismiss()
            }.setCloseListener {
                dialogPhoto.dismiss()
            }
        val dialogUtilGallery: DialogUtils = DialogUtils(this)
        val dialogGallery: AlertDialog = dialogUtilGallery.dialog
        dialogUtilGallery.setTitle("提示")
            .setContent("适趣我爱说申请访问存储权限用于修改头像")
            .setPositiveText("确定")
            .setNegativeText("取消")
            .setPositiveListener {
                dialogGallery.dismiss()
                if (bottomDialog != null) {
                    bottomDialog!!.dismiss()
                }
                //这里去申请相册权限
                //检测sdk版本是否大于33
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    //这里去申请相机权限
                    snackBarGallery = PermissionUtils.checkPermission(findViewById(R.id.snack_bar_container_work), this,
                        arrayListOf(Manifest.permission.READ_MEDIA_IMAGES),
                        object : CheckPermissionListener {
                            override fun onHavePermission() {
                                albumLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
                            }

                            override fun noHavePermission(snackbar: Snackbar?) {

                            }

                        },
                        REQUEST_CODE_GALLERY,
                        "适趣我爱说申请访问存储权限用于修改头像"
                    )
                }else{
                    snackBarGallery = PermissionUtils.checkPermission(findViewById(R.id.snack_bar_container_work), this,
                        arrayListOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        object : CheckPermissionListener {
                            override fun onHavePermission() {
                                albumLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
                            }

                            override fun noHavePermission(snackbar: Snackbar?) {
                                TODO("Not yet implemented")
                            }
                        },
                        REQUEST_CODE_GALLERY,
                        "适趣我爱说申请访问存储权限用于修改头像"
                    )
                }
            }
            .setNegativeListener {
                //不给权限不让用这个功能，结束这个弹窗
                dialogGallery.dismiss()
            }.setCloseListener {
                dialogGallery.dismiss()
            }
        avatarView?.setOnClickListener {
            //弹出弹窗
            bottomDialog!!.show()
        }
        tvPhoto?.setOnClickListener {
            //拍照，这里要询问用户是否允许拍照
            dialogPhoto.show()
        }
        tvGallery?.setOnClickListener {
            //相册,这里要询问用户是否允许打开相册
            dialogGallery.show()
        }
        tvCancel?.setOnClickListener {
            //取消
            bottomDialog!!.dismiss()
        }
    }
    //这里是回调方法
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAMERA) {
            //首先关闭snackBar
            snackBarCamera?.dismiss()
            //如果用户允许了相机权限，就打开相机
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //打开相机
                cameraLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE))
            }
        }else if (requestCode == REQUEST_CODE_GALLERY) {
            //首先关闭snackBar
            snackBarGallery?.dismiss()
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //打开相册
                albumLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
            }
        }
    }
}