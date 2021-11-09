package com.keelim.data.repository.url

import com.keelim.data.model.CallResult
import com.keelim.data.model.open.Url

interface UrlRepository {
    suspend fun share(token: String): List<Url>
    suspend fun inject(token: String, url: String): CallResult
    suspend fun allFolder(token: String): List<Url>
    suspend fun newOneFolder(token: String): CallResult
    suspend fun detailFolder(fid: String): CallResult
    suspend fun renameFolder(fid: String, name: String): CallResult
    suspend fun deleteFolder(fid: String): CallResult
    suspend fun folderPermissionChange(fid: String, email: String, permission: Int): CallResult
    suspend fun folderNewUser(fid: String, email: String, permission: Int): CallResult
    suspend fun folderDeleteUser(fid: String, email: String, permission:Int): CallResult
    suspend fun folderAllUrl(): List<Url>
    suspend fun folderUrl(fid: String, url:String, thumbnail:String, tags:String): List<Url>
    suspend fun folderNewUrl(fid:String,url:String, thumbnail:String, tags:String): CallResult
    suspend fun folderDeleteUrl(fid:String,url:String, thumbnail:String, tags:String): CallResult
    suspend fun urlAllMemo(mid:String): CallResult
    suspend fun urlNewMemo(mid:String, highlight:String, content:String): CallResult
    suspend fun urlChangeMemo(mid:String, highlight:String, content:String): CallResult
    suspend fun urlDeleteMemo(msid:String,mid:String): CallResult
    suspend fun submitUrl(token:String, url:String): CallResult
}