package com.gvelesiani.movieapp.presentation.welcome

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.widget.TextView
import com.gvelesiani.movieapp.R
import com.gvelesiani.movieapp.base.BaseActivity
import com.gvelesiani.movieapp.common.extensions.getColorCompat
import com.gvelesiani.movieapp.databinding.ActivityWelcomeBinding
import com.gvelesiani.movieapp.presentation.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class WelcomeActivity :
    BaseActivity<WelcomeViewModel, ActivityWelcomeBinding>(WelcomeViewModel::class) {

    override val bindingInflater: (LayoutInflater) -> ActivityWelcomeBinding
        get() = ActivityWelcomeBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        setupWelcomeScreenAppNameText()
        navigateToMainScreen()
    }

    private fun navigateToMainScreen() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun setupWelcomeScreenAppNameText() {
        val builder = SpannableStringBuilder()

        val white = getString(R.string.app_name)
        val whiteSpannable = SpannableString(white)
        whiteSpannable.setSpan(ForegroundColorSpan(Color.WHITE), 0, white.length, 0)
        builder.append(whiteSpannable)

        val red = "."
        val redSpannable = SpannableString(red)
        redSpannable.setSpan(
            ForegroundColorSpan(this.getColorCompat(R.color.colorSecondary)),
            0,
            red.length,
            0
        )
        builder.append(redSpannable)

        binding.tvAppName.setText(builder, TextView.BufferType.SPANNABLE)
    }
}