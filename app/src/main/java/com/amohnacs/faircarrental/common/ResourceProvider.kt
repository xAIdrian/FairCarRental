package com.amohnacs.faircarrental.common

import android.content.Context
import com.amohnacs.faircarrental.R

class ResourceProvider(val context: Context) {

    fun getPickupBeforeError(): String {
        return context.getString(R.string.pickup_before_today)
    }

    fun getPickupAfterError(): String {
        return context.getString(R.string.pickup_after_dropoff)
    }

    fun getInvalidAddresserror(): String {
        return context.getString(R.string.invalid_address)
    }

    fun getDateMissingError(): String {
        return context.getString(R.string.date_missing_error)
    }
}
