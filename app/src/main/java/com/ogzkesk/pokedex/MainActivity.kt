package com.ogzkesk.pokedex

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ogzkesk.core.R
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
//        setSystemBars()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeConnection()
    }

//    private fun setSystemBars(){
//        val systemBarStyle = SystemBarStyle.dark(getColor(R.color.md_theme_light_primary))
//        enableEdgeToEdge(systemBarStyle,systemBarStyle)
//    }

    private fun observeConnection() {
        collectFlowWithLifeCycle(NetworkObserver(this).observe()){
            if (it.not()) {
                withContext(Dispatchers.Main) {
                    NetworkDialog(this@MainActivity).show {}
                }
            }
        }
    }
}