package com.keelim.core.open

import com.keelim.data.model.open.LinkSourceContent

interface LinkViewCallback {
    fun onAfterLoading(linkSourceContent: LinkSourceContent)
}