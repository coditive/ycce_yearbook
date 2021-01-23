package com.syrous.ycceyearbook.action

import com.syrous.ycceyearbook.flux.Action

sealed class NetworkAction: Action {
    object CheckConnectivity: NetworkAction()
}