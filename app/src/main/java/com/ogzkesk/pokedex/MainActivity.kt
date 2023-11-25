package com.ogzkesk.pokedex

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ogzkesk.core.connection.NetworkDialog
import com.ogzkesk.core.connection.NetworkObserver
import com.ogzkesk.core.ext.collectFlowWithLifeCycle
import com.ogzkesk.pokedex.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge(
            SystemBarStyle.dark(Color.TRANSPARENT),
            SystemBarStyle.dark(Color.TRANSPARENT)
        )

        observeConnection()
    }

    private fun observeConnection() {
        collectFlowWithLifeCycle(NetworkObserver(this).observe()) {
            if (it.not()) {
                NetworkDialog(this@MainActivity).show {}
            }
        }
    }
}