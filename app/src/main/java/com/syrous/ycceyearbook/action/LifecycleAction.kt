package com.syrous.ycceyearbook.action

enum class LifecycleAction(
    override val eventMethod: TelemetryEventMethod,
    override val eventObject: TelemetryEventObject
): TelemetryAction {
    /**
     * Emitted when an activity owned by the app comes on screen.
     */
    Foreground(TelemetryEventMethod.foreground, TelemetryEventObject.app),
    /**
     * Emitted when an activity owned by the app is no longer on screen.
     */
    Background(TelemetryEventMethod.background, TelemetryEventObject.app),

    /**
     * Emitted when an app is in starting.
     */
    Startup(TelemetryEventMethod.startup, TelemetryEventObject.app),

    /**
     * Emitted when an onStop is called on process.
     */
    ShutDown(TelemetryEventMethod.shut_down, TelemetryEventObject.app)
}