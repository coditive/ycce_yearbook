package com.syrous.ycceyearbook.action

import com.syrous.ycceyearbook.flux.Action

interface TelemetryAction: Action {
    val eventMethod: TelemetryEventMethod
    val eventObject: TelemetryEventObject
    val value: String?
        get() = null
    val extras: Map<String, Any>?
        get() = null

}

enum class TelemetryEventMethod {
    tap,
    startup,
    foreground,
    background,
    setting_changed,
    show,
    reset,
    sync_start,
    sync_end,
    sync_error,
    automatic_log_in,
    log_in,
    logged_in,
    touch,
    fetch_paper,
    fetch_subject,
    fetch_resource,
    notification,
    semester,
    update_credentials,
    delete,
    edit,
    create,
    shut_down,
    create_item_error
}

enum class TelemetryEventObject {
    app,
    activity,
    splash_screen,
    welcome_screen,
    notification_screen,
    login_student_mis,
    account_store,
    home_screen,
    semester_screen,
    paper_and_resources_screen,
    open_in_browser,
    back,
    dialog,
    toast
}