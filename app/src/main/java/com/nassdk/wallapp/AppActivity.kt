package com.nassdk.wallapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(
                R.id.app_container,
                NewsFeedFragment(),
                "NewsFeedFragment"
            )
            transaction.commit()
        }
    }
}