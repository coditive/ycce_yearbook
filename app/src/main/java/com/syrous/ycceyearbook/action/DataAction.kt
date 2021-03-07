package com.syrous.ycceyearbook.action

sealed class DataAction(
    override val eventMethod: TelemetryEventMethod,
    override val eventObject: TelemetryEventObject,
    override val value: String? = null,
    override val extras: Map<String, Any>? = null
): TelemetryAction {

    class GetSemester(val department: String
    ): DataAction(TelemetryEventMethod.semester, TelemetryEventObject.semester_screen)

    class GetMseData(val department: String, val sem: Int, val courseCode: String
    ): DataAction(TelemetryEventMethod.fetch_paper, TelemetryEventObject.paper_and_resources_screen)

    class GetEseData(val department: String, val sem: Int, val courseCode: String
    ): DataAction(TelemetryEventMethod.fetch_paper,
        TelemetryEventObject.paper_and_resources_screen)

    class GetSubjects(val department: String
    ): DataAction(TelemetryEventMethod.fetch_subject,
        TelemetryEventObject.paper_and_resources_screen)

    class GetResource(val department: String, val sem: Int, val courseCode: String
    ): DataAction(TelemetryEventMethod.fetch_resource,
        TelemetryEventObject.paper_and_resources_screen)
}