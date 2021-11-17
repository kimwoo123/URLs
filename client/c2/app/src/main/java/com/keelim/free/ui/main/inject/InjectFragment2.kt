package com.keelim.free.ui.main.inject

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import coil.load
import com.keelim.free.R
import com.keelim.free.databinding.LayoutCollapseBinding
import com.keelim.free.databinding.LayoutExpandBinding
import kr.co.prnd.persistbottomsheetfragment.PersistBottomSheetFragment

class InjectFragment2: PersistBottomSheetFragment<LayoutCollapseBinding, LayoutExpandBinding>(
    R.layout.layout_collapse,
    R.layout.layout_expand
){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapseBinding.viewSelect.setOnClickListener {
            expand()
        }
        expandBinding.injectImageView.load("https://avatars.githubusercontent.com/u/26667456?v=4")
    }
    companion object {
        private val TAG = InjectFragment2::class.simpleName
        fun show(
            fragmentManager: FragmentManager,
            @IdRes containerViewId: Int,
        ): InjectFragment2 =
            fragmentManager.findFragmentByTag(TAG) as? InjectFragment2
                ?: InjectFragment2().apply {
                    fragmentManager.beginTransaction()
                        .replace(containerViewId, this, TAG)
                        .commitAllowingStateLoss()
                }
    }
}
