package com.syrous.ycceyearbook.action

import com.syrous.ycceyearbook.flux.Action


sealed class DialogAction(
    val positiveButtonActionList: List<Action> = emptyList(),
    val negativeButtonActionList: List<Action> = emptyList()
) : RouteAction(TelemetryEventMethod.show, TelemetryEventObject.dialog) {
}