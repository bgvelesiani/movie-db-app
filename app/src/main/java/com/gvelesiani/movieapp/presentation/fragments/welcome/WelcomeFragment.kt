package com.gvelesiani.movieapp.presentation.fragments.welcome

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.gvelesiani.movieapp.R
import com.gvelesiani.movieapp.base.BaseFragment
import com.gvelesiani.movieapp.databinding.FragmentWelcomeBinding
import com.gvelesiani.movieapp.other.extensions.getColorCompat
import org.koin.androidx.viewmodel.ext.android.viewModel


class WelcomeFragment : BaseFragment<WelcomeViewModel, FragmentWelcomeBinding>() {
    private val viewModel: WelcomeViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWelcomeBinding
        get() = FragmentWelcomeBinding::inflate

    override fun provideViewModel(): WelcomeViewModel = viewModel

    override fun setupView(savedInstanceState: Bundle?) {
        setUpWelcomeScreenAppNameText()

        binding.btGetStarted.setOnClickListener {
            viewModel.updateWelcomeScreenButtonStateUseCase(true)
            findNavController().navigate(R.id.action_welcomeFragment_to_moviesFragment)
        }
    }

    private fun setUpWelcomeScreenAppNameText() {
        val builder = SpannableStringBuilder()

        val white = getString(R.string.app_name)
        val whiteSpannable = SpannableString(white)
        whiteSpannable.setSpan(ForegroundColorSpan(Color.WHITE), 0, white.length, 0)
        builder.append(whiteSpannable)

        val red = "."
        val redSpannable = SpannableString(red)
        redSpannable.setSpan(
            ForegroundColorSpan(requireContext().getColorCompat(R.color.welcome_screen_dot_color)),
            0,
            red.length,
            0
        )
        builder.append(redSpannable)

        binding.tvAppName.setText(builder, TextView.BufferType.SPANNABLE)
    }
}