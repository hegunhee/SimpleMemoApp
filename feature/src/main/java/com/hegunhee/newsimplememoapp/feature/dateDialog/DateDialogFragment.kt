package com.hegunhee.newsimplememoapp.feature.dateDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hegunhee.newsimplememoapp.feature.R
import com.hegunhee.newsimplememoapp.feature.databinding.DialogDatePickerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DateDialogFragment : DialogFragment(){

    private val viewModel : DateDialogViewModel by viewModels()

    private lateinit var binding : DialogDatePickerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.dialog_date_picker,container,false)
        binding = DialogDatePickerBinding.bind(root).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@DateDialogFragment.viewModel
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.dateDialogNavigation.collect{
                        when(it) {
                            is DateDialogNavigation.Dismiss -> {
                                dismissAllowingStateLoss()
                            }
                            is DateDialogNavigation.DisMissWithDate -> {

                            }
                        }
                    }
                }
            }
        }
    }

    companion object {

        const val TAG = "date_dialog_tag"

        fun getInstance() : DateDialogFragment {
            return DateDialogFragment()
        }
    }
}