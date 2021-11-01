package com.keelim.free.ui.main.open

import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.keelim.core.extensions.showToast
import com.keelim.core.open.LinkViewCallback
import com.keelim.data.model.open.LinkSourceContent
import com.keelim.free.databinding.ActivityOpenBinding
import com.keelim.free.util.OgTagParser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.util.regex.Matcher
import java.util.regex.Pattern

@AndroidEntryPoint
class OpenActivity : AppCompatActivity() {
    private val binding: ActivityOpenBinding by lazy { ActivityOpenBinding.inflate(layoutInflater) }
    private val viewModel: OpenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
//        observeData()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun getUrls(input: String): ArrayList<String> {
        val containedUrls = ArrayList<String>()
        val pattern = "(?:(?:https?|ftp):\\/\\/)?[\\w/\\-?=%.]+\\.[\\w/\\-?=%.]+"
        val urlMatcher = getPatternMatcher(
            pattern,
            input
        )
        while (urlMatcher.find()) {
            containedUrls.add(
                input.substring(
                    urlMatcher.start(0),
                    urlMatcher.end(0)
                )
            )
        }
        return containedUrls
    }

    private fun getPatternMatcher(urlRegex: String, input: String): Matcher {
        val pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE)
        return pattern.matcher(input)
    }

    private fun initViews() = with(binding) {
        button.setOnClickListener {
            if (TextUtils.isEmpty(editText.text.toString())) {
                showToast("Please Enter URL")
            } else {
                val linkArray = getUrls(editText.text.toString().trim())

                OgTagParser()
                    .getContents(
                        linkArray[0],
                        object : LinkViewCallback {
                            override suspend fun onAfterLoading(linkSourceContent: LinkSourceContent) {
                                textView2.text = String.format(
                                    "R.string.display_data",
                                    linkSourceContent.ogTitle,
                                    linkSourceContent.ogDescription,
                                    linkSourceContent.ogUrl,
                                    linkSourceContent.ogSiteName,
                                    linkSourceContent.ogType,
                                    linkSourceContent.images
                                )
                                tvTitle.text = linkSourceContent.ogTitle
                                tvUrl.text = linkSourceContent.ogUrl
                                tvDescription.text = linkSourceContent.ogDescription
                                tvSiteName.text = linkSourceContent.ogSiteName
                                ivImage.load(linkSourceContent.images)
                            }
                        }
                    )
            }
        }
//        button.setOnClickListener {
//            if (TextUtils.isEmpty(editText.text.toString())) {
//                showToast("Please Enter URL")
//            } else {
//                val linkArray = getUrls(editText.text.toString().trim())
//                viewModel.getContent(linkArray[0])
//            }
//        }
    }

    private fun observeData() = lifecycleScope.launchWhenStarted {
        viewModel.state.collect {
            when (it) {
                is OpenState.UnInitialized -> {
                    Snackbar.make(binding.root, "URL 을 입력해주세요", Snackbar.LENGTH_SHORT).show()
                }
                is OpenState.Loading -> {
                    Timber.d("데이터 로딩 중입니다.")
                }
                is OpenState.Error -> {
                    showToast(it.message)
                }
                is OpenState.Success -> with(binding) {
                    val linkSourceContent = it.data
                    textView2.text = String.format(
                        "R.string.display_data",
                        linkSourceContent.ogTitle,
                        linkSourceContent.ogDescription,
                        linkSourceContent.ogUrl,
                        linkSourceContent.ogSiteName,
                        linkSourceContent.ogType,
                        linkSourceContent.images
                    )
                    tvTitle.text = linkSourceContent.ogTitle
                    tvUrl.text = linkSourceContent.ogUrl
                    tvDescription.text = linkSourceContent.ogDescription
                    tvSiteName.text = linkSourceContent.ogSiteName
                    ivImage.load(linkSourceContent.images)
                }
            }
        }
    }
}

