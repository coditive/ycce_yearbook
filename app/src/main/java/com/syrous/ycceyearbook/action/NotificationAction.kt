package com.syrous.ycceyearbook.action

sealed class NotificationAction(
    override val eventMethod: TelemetryEventMethod,
    override val eventObject: TelemetryEventObject,
    override val value: String? = null,
    override val extras: Map<String, Any>? = null
): TelemetryAction {
    object GetUnReadNotification: NotificationAction(TelemetryEventMethod.notification,
        TelemetryEventObject.notification_screen)

    data class MarkNotificationRead(val notificationId: String): NotificationAction(
        TelemetryEventMethod.notification,
        TelemetryEventObject.notification_screen)
}