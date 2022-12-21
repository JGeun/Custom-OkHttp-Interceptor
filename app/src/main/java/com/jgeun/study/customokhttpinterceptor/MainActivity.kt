package com.jgeun.study.customokhttpinterceptor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.jgeun.study.customokhttpinterceptor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        binding.apply {
//            vm = viewModel
//        }

        viewModel.item.observe(this) {
            binding.itemDataContent.text = "${it.itemName} ${it.pay}"
        }

        viewModel.person.observe(this) {
            binding.personDataContent.text = "${it.name} ${it.age}"
        }

    }
}