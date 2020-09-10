package com.syrous.ycceyearbook.util

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.syrous.ycceyearbook.data.local.NotificationDao
import com.syrous.ycceyearbook.data.local.UserSharedPrefStorage
import com.syrous.ycceyearbook.data.local.YearBookDatabase
import com.syrous.ycceyearbook.model.Notification
import com.syrous.ycceyearbook.model.Result.Success
import timber.log.Timber

class NotificationService: FirebaseMessagingService() {

    private lateinit var storage: UserSharedPrefStorage

    private lateinit var notificationDao: NotificationDao

    override fun onCreate() {
        super.onCreate()
        storage = UserSharedPrefStorage(applicationContext
            .getSharedPreferences(USER_SP_KEY, Context.MODE_PRIVATE))

        notificationDao = initializeDB(applicationContext)
    }

    private fun initializeDB (applicationContext: Context): NotificationDao {
        val db = Room.databaseBuilder(applicationContext,
        YearBookDatabase::class.java,
        YEARBOOK_DB_NAME
        ).build()
        return db.notificationDao()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            Timber.d("Notification Received : ${it.body}")
            sendNotification(applicationContext, it.title!!,it.body!!)
            try {
                notificationDao.insertNotification(Notification(remoteMessage.messageId!!, 0,
                    remoteMessage.sentTime, it.title, it.body))
            } catch (e : Exception) {
                Timber.d("Error In Notification Service : ${e.message}")
            }
        }
    }

    override fun onNewToken(token: String) {
        Timber.d("Token : $token")
        storage.saveNotificationToken(token)
        sendTokenToServer()
    }

    private fun sendTokenToServer() {
       val result = storage.getLoggedInUser()
        val db = FirebaseFirestore.getInstance()
        if(result is Success) {
            val user = result.data
            val query = db.collection(USER_COLLECTION).document(user.googleid)
            query.set(user)
        }
    }

}