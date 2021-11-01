package com.keelim.free.ui.main.open

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.keelim.core.extensions.showToast
import com.keelim.core.open.LinkViewCallback
import com.keelim.data.model.open.LinkSourceContent
import com.keelim.free.databinding.ActivityOpenBinding
import com.keelim.free.util.OgTagParser
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Matcher
import java.util.regex.Pattern

@AndroidEntryPoint
class OpenActivity : AppCompatActivity() {
    private val binding: ActivityOpenBinding by lazy { ActivityOpenBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            if (TextUtils.isEmpty(binding.editText.text.toString())) {
                showToast("Please Enter URL")
            } else {
                val linkArray = getUrls(binding.editText.text.toString().trim())
                OgTagParser().getContents(
                    linkArray[0],
                    object : LinkViewCallback {
                        override fun onAfterLoading(linkSourceContent: LinkSourceContent) {
                            binding.textView2.text = String.format(
                                "R.string.display_data",
                                linkSourceContent.ogTitle,
                                linkSourceContent.ogDescription,
                                linkSourceContent.ogUrl,
                                linkSourceContent.ogSiteName,
                                linkSourceContent.ogType,
                                linkSourceContent.images
                            )
                            binding.tvTitle.text = linkSourceContent.ogTitle
                            binding.tvUrl.text = linkSourceContent.ogUrl
                            binding.tvDescription.text = linkSourceContent.ogDescription
                            binding.tvSiteName.text = linkSourceContent.ogSiteName
                            binding.ivImage.load(linkSourceContent.images)
                        }
                    }
                )
            }
        }
    }

    private fun getUrls(input: String): ArrayList<String> {
        val containedUrls = ArrayList<String>()
        val urlMatcher = getPatternMatcher(
            "(?:(?:https?|ftp):\\/\\/)?[\\w/\\-?=%.]+\\.[\\w/\\-?=%.]+",
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
}

