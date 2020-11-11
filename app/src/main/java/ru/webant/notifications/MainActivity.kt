package ru.webant.notifications

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import ru.webant.notifications.fragments.notifications_list.NotificationsFragment

class MainActivity : MvpAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, NotificationsFragment())
            .commit()
    }
}