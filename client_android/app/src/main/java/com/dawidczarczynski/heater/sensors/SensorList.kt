package com.dawidczarczynski.heater.sensors

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SensorList(val sensors: ArrayList<Sensor>) : Parcelable {}