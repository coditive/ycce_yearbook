package com.syrous.ycceyearbook.store

import com.syrous.ycceyearbook.action.NotificationAction
import com.syrous.ycceyearbook.data.local.NotificationDao
import com.syrous.ycceyearbook.flux.Dispatcher
import com.syrous.ycceyearbook.model.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class NotificationStore(
    private val dispatcher: Dispatcher,
    private val notificationDao: NotificationDao,
    coroutineContext: CoroutineContext
) {
    private val coroutineScope = CoroutineScope(coroutineContext)

    private val _unReadNotificationList = MutableStateFlow(emptyList<Notification>())
    val unReadNotificationList: StateFlow<List<Notification>> = _unReadNotificationList

    init {
        coroutineScope.launch {
            dispatcher.getDispatcherChannelSubscription()
                .receiveAsFlow()
                .filterIsInstance<NotificationAction>()
                .collect {
                    notificationAction ->
                    when(notificationAction) {
                        NotificationAction.GetUnReadNotification -> getUnReadNotifications()
                        is NotificationAction.MarkNotificationRead ->
                            updateNotificationReadStatus(notificationAction.notificationId)
                    }
                }
        }
    }

    private suspend fun getUnReadNotifications() {
        notificationDao.observeReadNotification(0)
            .collect {
                notificationList ->
                _unReadNotificationList.emit(notificationList)
            }
    }

    private suspend fun updateNotificationReadStatus(notificationId: String) {
        notificationDao.updateNotificationReadState(notificationId)
    }
}