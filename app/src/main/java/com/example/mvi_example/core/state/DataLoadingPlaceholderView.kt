package com.example.mvi_example.core.state

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.mvi_example.R
import com.example.mvi_example.core.network.AppError
import com.example.mvi_example.databinding.LayoutLoadingPlaceholderViewBinding

class DataLoadingPlaceholderView : ConstraintLayout {

    private lateinit var binding: LayoutLoadingPlaceholderViewBinding

    constructor(context: Context) : super(context) {
        sharedInit()
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        sharedInit()
    }

    var onRetry: (() -> Unit)? = null

    private fun sharedInit() {
        val view = inflate(context, R.layout.layout_loading_placeholder_view, this)
        binding = LayoutLoadingPlaceholderViewBinding.bind(view)
        binding.retryButton.setOnClickListener {
            onRetry?.invoke()
        }
    }

    fun bind(state: State<*>) {
        when (state) {
            is State.Loading -> {
                isVisible = true
                binding.progressBar.isVisible = true

                binding.errorImageView.isVisible = false
                binding.errorTitleTextView.isVisible = false
                binding.errorInfoTextView.isVisible = false
                binding.retryButton.isVisible = false
            }

            is State.LoadingFailed -> {
                isVisible = true
                binding.progressBar.isVisible = false

                binding.errorImageView.isVisible = true
                binding.errorTitleTextView.isVisible = true
                binding.errorInfoTextView.isVisible = true
                binding.retryButton.isVisible = true

                if (state.error is AppError) {
                    when (state.error.code) {
                        AppError.Code.Network -> {
//                            binding.errorImageView.setImageResource(R.drawable.img_no_internet_connection)
                            binding.errorTitleTextView.text =
                                context.getString(R.string.title_no_network_connection)
                            binding.errorInfoTextView.text =
                                context.getString(R.string.msg_no_network_connection)
                        }
                        else -> {
//                            binding.errorImageView.setImageResource(R.drawable.img_error)
//                            binding.errorTitleTextView.text =
//                                context.getString(R.string.something_went_wrong)
//                            binding.errorInfoTextView.text = context.getString(R.string.msg_try_again)
                        }
                    }
                }
            }

            is State.RetryingLoad -> {
                isVisible = true
                binding.progressBar.isVisible = true

                binding.errorImageView.isVisible = false
                binding.errorTitleTextView.isVisible = false
                binding.errorInfoTextView.isVisible = false
                binding.retryButton.isVisible = false
            }

            is State.Loaded -> {
                isVisible = false
                binding.progressBar.isVisible = false

                binding.errorImageView.isVisible = false
                binding.errorTitleTextView.isVisible = false
                binding.errorInfoTextView.isVisible = false
                binding.retryButton.isVisible = false

                // Show empty placeholder.
                if (state.data == null || (state.data is List<*> && state.data.isNullOrEmpty())) {
                    isVisible = true
                    binding.progressBar.isVisible = false

                    binding.errorImageView.isVisible = true
                    binding.errorTitleTextView.isVisible = true
                    binding.errorInfoTextView.isVisible = false
                    binding.retryButton.isVisible = false
                }
            }

            is State.ManualReloading -> {
                isVisible = false
                binding.progressBar.isVisible = false

                binding.errorImageView.isVisible = false
                binding.errorTitleTextView.isVisible = false
                binding.errorInfoTextView.isVisible = false
                binding.retryButton.isVisible = false
            }

            is State.ManualReloadingFailed -> {
                isVisible = false
                binding.progressBar.isVisible = false

                binding.errorImageView.isVisible = false
                binding.errorTitleTextView.isVisible = false
                binding.errorInfoTextView.isVisible = false
                binding.retryButton.isVisible = false
            }

            is State.LoadingNextPage -> {
                // Do nothing since this placeholder will not be used for next page loading screens.
            }
        }
    }
}