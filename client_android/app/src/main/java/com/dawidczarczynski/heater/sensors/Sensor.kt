package com.dawidczarczynski.heater.sensors

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sensor (val id: String, val name: String) : Parcelable {
    override fun toString(): String {
        return name
    }
}
