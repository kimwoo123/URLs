package com.keelim.core.open

import com.keelim.data.model.open.LinkSourceContent

interface LinkViewCallback {
    suspend fun onAfterLoading(linkSourceContent: LinkSourceContent)
}