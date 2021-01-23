package com.syrous.ycceyearbook.action

import androidx.annotation.StringRes
import com.syrous.ycceyearbook.util.Constant

open class RouteAction (
    override val eventMethod: TelemetryEventMethod,
    override val eventObject: TelemetryEventObject
): TelemetryAction {
    object StartUp: RouteAction(TelemetryEventMethod.show, TelemetryEventObject.splash_screen)
    object Welcome: RouteAction(TelemetryEventMethod.show, TelemetryEventObject.welcome_screen)
    object Login: RouteAction(TelemetryEventMethod.show, TelemetryEventObject.login_student_mis)
    object Home: RouteAction(TelemetryEventMethod.show, TelemetryEventObject.home_screen)
    object Semester: RouteAction(TelemetryEventMethod.show, TelemetryEventObject.semester_screen)
    object PaperAndResource: RouteAction(TelemetryEventMethod.show,
        TelemetryEventObject.paper_and_resources_screen)

    object InternalBack: RouteAction(TelemetryEventMethod.tap, TelemetryEventObject.back)
    sealed class DialogFragment(
        @StringRes val dialogTitle: Int,
        @StringRes val dialogSubtitle: Int? = null
    ): RouteAction(TelemetryEventMethod.show, TelemetryEventObject.dialog) {
        //Todo( Add Dialog Classes Here )
    }

    open class SystemIntent(
        val requestCode: Int = Constant.RequestCode.noResult,
        eventMethod: TelemetryEventMethod,
        eventObject: TelemetryEventObject
    ): RouteAction(eventMethod, eventObject)

    data class OpenWebsite(val url: String): SystemIntent(
        Constant.RequestCode.noResult,
        TelemetryEventMethod.show,
        TelemetryEventObject.open_in_browser
    )
}