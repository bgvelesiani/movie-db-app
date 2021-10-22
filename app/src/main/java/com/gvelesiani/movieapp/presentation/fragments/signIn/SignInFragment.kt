package com.gvelesiani.movieapp.presentation.fragments.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.gvelesiani.movieapp.R
import com.gvelesiani.movieapp.databinding.FragmentSignInBinding
import com.gvelesiani.mvvm.fragment.BaseFragment

class SignInFragment : BaseFragment<SignInViewModel, FragmentSignInBinding>() {
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val viewModel: SignInViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignInBinding
        get() = FragmentSignInBinding::inflate

    override fun provideViewModel(): SignInViewModel = viewModel

    override fun setupView(binding: FragmentSignInBinding, savedInstanceState: Bundle?) {
        with(binding) {
            btSignIn.setOnClickListener {
                signIn(etEmail.text.toString(), etPassword.text.toString())
            }

            tvContinueAsAGuest.setOnClickListener {
                findNavController().navigate(R.id.moviesFragment)
            }
        }
    }


    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.moviesFragment)
                } else {
                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}