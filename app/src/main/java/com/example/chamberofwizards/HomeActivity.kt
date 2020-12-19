package com.example.chamberofwizards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.chamberofwizards.fragments.ForumFragment
import com.example.chamberofwizards.fragments.MessagesFragment
import com.example.chamberofwizards.fragments.NotifFragment
import com.example.chamberofwizards.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        replaceFragment(R.id.flHome, ForumFragment())

        initViews()
    }

    private fun initViews() {
        val listener = BottomNavigationView.OnNavigationItemSelectedListener {
            var fragment: Fragment? = null
            when (it.itemId) {
                R.id.menu_forum -> {
                    fragment = ForumFragment()
                    replaceFragment(R.id.flHome, fragment)
                    true
                }
                R.id.menu_search -> {
                    fragment = SearchFragment()
                    replaceFragment(R.id.flHome, fragment)
                    true
                }
                R.id.menu_notif -> {
                    fragment = NotifFragment()
                    replaceFragment(R.id.flHome, fragment)
                    true
                }
                R.id.menu_messages -> {
                    fragment = MessagesFragment()
                    replaceFragment(R.id.flHome, fragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
        bnvHome.setOnNavigationItemSelectedListener(listener)
    }
}