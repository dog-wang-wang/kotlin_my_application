package com.dorameet.myapplication

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationBarView

class SecondActivity : AppCompatActivity() {
    private var navigationBar: NavigationBarView? = null
    private var currentSelectedItem: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()
    }

    private fun initViews() {
       navigationBar = findViewById<NavigationBarView>(R.id.navigation_rail)
        // 获取其中的 NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragments) as NavHostFragment?

        // 从 NavHostFragment 获取 NavController
        val navController = navHostFragment!!.navController
        navigationBar?.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener setOnItemSelectedListener@{ item: MenuItem ->
    //            设置一些导航动画和启动模式。
            val builder: NavOptions.Builder = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(androidx.navigation.ui.R.animator.nav_default_enter_anim)
                .setExitAnim(androidx.navigation.ui.R.animator.nav_default_exit_anim)
                .setPopEnterAnim(androidx.navigation.ui.R.animator.nav_default_pop_enter_anim)
                .setPopExitAnim(androidx.navigation.ui.R.animator.nav_default_pop_enter_anim)
    //          果选中的菜单项是主要的(Primary)菜单项(取决于item的order属性),
    //          则将popUpTo设置为导航图的起始目的地,这样在导航回退时就会直接回到起始页面。
            if ((item.order and Menu.CATEGORY_SECONDARY) == 0) {
                builder.setPopUpTo(navController.graph.getStartDestination(), false)
            }
    //          构建NavOptions对象
            val options: NavOptions = builder.build()
            if (currentSelectedItem == null || currentSelectedItem!!.getItemId() != item.itemId) {
                try {
                    // 尝试使用navController进行导航,传入选中的菜单项ID、null作为参数Bundle以及构建好的NavOptions
                    navController.navigate(item.itemId, null, options)
                    // 更新当前选中的菜单项
                    currentSelectedItem = item
                    // 如果导航成功,返回true
                    return@setOnItemSelectedListener true
                } catch (e: IllegalArgumentException) {
                    // 如果导航失败(可能是因为没有与该ID匹配的目的地),会抛出IllegalArgumentException
                    // 此时捕获异常并返回false,表示没有成功处理该菜单项的选中事件
                    return@setOnItemSelectedListener false
                }
            } else {
                // 如果当前选中的菜单项与新选中的菜单项相同,则不进行导航
                return@setOnItemSelectedListener false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}