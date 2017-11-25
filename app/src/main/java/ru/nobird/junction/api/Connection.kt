package ru.nobird.junction.api

import com.movesense.mds.Mds
import com.movesense.mds.MdsException
import com.movesense.mds.MdsSubscription
import ru.nobird.junction.api.sensor.AngularVelocitySensor

class Connection(val device: MoveSenseDevice, val mds: Mds) {
    private var listener: ConnectionListener? = null
    private var avSubscription: MdsSubscription? = null
    private var laSubscription: MdsSubscription? = null

    var status: ConnectStatus = ConnectStatus.DISCONNECTED
    var error: MdsException? = null

    fun subscribe(listener: Any) {

    }

    fun unsubscribe() {
        avSubscription?.unsubscribe()
        laSubscription?.unsubscribe()
    }

    fun attach(listener: ConnectionListener) {
        this.listener = listener
        when (status) {
            ConnectStatus.CONNECTED -> listener.connected(device)
            ConnectStatus.FAILED -> listener.failed(error)
            ConnectStatus.DISCONNECTED -> listener.disconnected()
        }
    }

    fun dettach() {
        this.listener = null
    }

    fun connected() {
        status = ConnectStatus.CONNECTED
        listener?.connected(device)
    }

    fun failed(error: MdsException) {
        status = ConnectStatus.FAILED
        listener?.failed(error)
        this.error = error
    }

    fun disconnected() {
        status = ConnectStatus.DISCONNECTED
        listener?.disconnected()
    }
}