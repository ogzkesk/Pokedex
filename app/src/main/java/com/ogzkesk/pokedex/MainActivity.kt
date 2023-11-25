package com.ogzkesk.pokedex

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ogzkesk.core.connection.NetworkObserver
import com.ogzkesk.core.ext.collectFlowWithLifeCycle
import com.ogzkesk.core.ui.NetworkDialog
import com.ogzkesk.pokedex.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        enableEdgeToEdge(
            SystemBarStyle.dark(Color.TRANSPARENT),
            SystemBarStyle.dark(Color.TRANSPARENT)
        )

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        observeConnection()
    }

    private fun observeConnection() {
        collectFlowWithLifeCycle(NetworkObserver(this).observe()) {
            if (it.not()) {
                NetworkDialog(this@MainActivity).show {
                    openWifiSettings()
                }
            }
        }
    }

    private fun openWifiSettings(){
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        startActivity(intent)
    }
}