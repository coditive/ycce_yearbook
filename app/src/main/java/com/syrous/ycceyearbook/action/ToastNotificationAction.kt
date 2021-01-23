package com.syrous.ycceyearbook.action

sealed class ToastNotificationAction()
    :RouteAction(TelemetryEventMethod.show, TelemetryEventObject.toast){
}