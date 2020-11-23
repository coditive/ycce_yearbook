package com.syrous.ycceyearbook.ui.notices

import com.syrous.ycceyearbook.data.local.NotificationDao
import javax.inject.Inject

class NotificationVM @Inject constructor(
    private val noticeDao: NotificationDao
) {

    val noticeMsg = noticeDao.observeReadNotification(0)
}