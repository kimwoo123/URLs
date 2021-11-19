package com.keelim.free.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.keelim.free.databinding.ActivityTutorialBinding
import com.keelim.free.ui.auth.AuthActivity
import com.keelim.orange.ui.splash.TutorialFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TutorialActivity : AppCompatActivity() {
    private val binding by lazy { ActivityTutorialBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding) {
        viewpager.adapter = ViewPager2Adapter()
        btnSkip.setOnClickListener {
            goNext()
        }
        btnNext.setOnClickListener {
            val current = viewpager.currentItem + 1
            if (current < 5) {
                viewpager.currentItem = current
            } else {
                goNext()
            }
        }
        TabLayoutMediator(tabIndicator, viewpager) { tab, position ->
        }.attach()
    }

    private fun goNext() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    inner class ViewPager2Adapter : FragmentStateAdapter(this) {
        private val fragments = listOf<Fragment>(
            TutorialFragment(
                null,
                "안녕하세요!\n 정보를 가장 똑똑하게 저장하는 방법 URLS 입니다.",
                "당신을 기다렸어요!!",
            ),

            TutorialFragment(
                null,
                "저희가 사용법을 알려드릴게요",
                "율스에 오신 것을 환영합니다."
            ),

            TutorialFragment(
                null,
                "사용법1",
                "율스에 오신 것을 환영합니다."
            ),

            TutorialFragment(
                null,
                "사용법2",
                "율스에 오신 것을 환영합니다."
            ),

            TutorialFragment(
                null,
                "사용법3",
                "율스에 오신 것을 환영합니다."
            ),
        )

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }
}