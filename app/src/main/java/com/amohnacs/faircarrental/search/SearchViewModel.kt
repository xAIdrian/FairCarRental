package com.amohnacs.faircarrental.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.amohnacs.common.mvvm.SingleLiveEvent
import com.amohnacs.faircarrental.common.ResourceProvider
import com.amohnacs.faircarrental.search.ui.SearchActivity.DROPOFF_DIALOG
import com.amohnacs.faircarrental.search.ui.SearchActivity.PICKUP_DIALOG
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by adrian mohnacs on 12/1/18
 */
data class LaunchPackage(
        val addressQueryString: String,
        val pickupSelection: String,
        val dropoffSelection: String)

class SearchViewModel(
        private val resourceProvider: ResourceProvider) : ViewModel() {

    private val TAG = SearchPresenter::class.java.simpleName

    var addressIsValid: MutableLiveData<Boolean> = MutableLiveData()

    var errorEvent: SingleLiveEvent<String> = SingleLiveEvent()
    var displayPickupDialogEvent: SingleLiveEvent<String> = SingleLiveEvent()
    var displayDropoffDialogEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val validInputsLaunchFragmentEvent: SingleLiveEvent<LaunchPackage> = SingleLiveEvent()

    private var stringQueryFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var yesterday: Date = Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)
    private var addressQueryString: String? = null
    private var pickupDate: Date? = null
    private var dropoffDate: Date? = null

    companion object {
        private val DATE_HELPER_ZERO = "0"

        @Volatile
        private var instance: SearchViewModel? = null

        @JvmStatic
        fun getInstance(resourceProvider: ResourceProvider): SearchViewModel? {
            if (instance == null) {
                synchronized(SearchPresenter::class.java) {
                    if (instance == null) {
                        instance = SearchViewModel(resourceProvider)
                    }
                }
            }
            return instance
        }
    }

    fun validateAddress(addressString: String) {
        addressQueryString = addressString
        addressIsValid.value = !addressString.isEmpty()
    }

    fun dateSetChecker(viewTag: String, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val offsetZeroIndexMonth = monthOfYear + 1
        val dateQueryString = (year.toString() + "-" + (if (offsetZeroIndexMonth < 10) DATE_HELPER_ZERO + offsetZeroIndexMonth else offsetZeroIndexMonth) + "-"
                + if (dayOfMonth < 10) DATE_HELPER_ZERO + dayOfMonth else dayOfMonth)
        //chaning our string to a date
        var selectedDate = Date()

        try {
            selectedDate = stringQueryFormat?.parse(dateQueryString) ?: Date()
        } catch (e: ParseException) {
            e.printStackTrace()
            errorEvent.value = e.message
        }

        when (viewTag) {
            PICKUP_DIALOG -> {
                pickupDate = selectedDate
                displayPickupDialogEvent.value = stringQueryFormat?.format(pickupDate)
            }
            DROPOFF_DIALOG -> {
                dropoffDate = selectedDate
                displayDropoffDialogEvent.value = stringQueryFormat?.format(dropoffDate)
            }
            else -> Log.e(TAG, "inappropriate tag for date picker dialog")
        }
    }

    fun validateInputsForSearch() {
        //gets our query formatted string
        if (pickupDate != null && dropoffDate != null) {
            val pickupSelection = stringQueryFormat?.format(pickupDate)
            val dropoffSelection = stringQueryFormat?.format(dropoffDate)

            if (!pickupDate!!.after(yesterday)) {
                errorEvent.value = resourceProvider.getPickupBeforeError()
            } else if (!dropoffDate!!.after(pickupDate)) {
                errorEvent.value = resourceProvider.getPickupAfterError()
            } else if (
                !addressIsValid.value!!
                || addressQueryString == null
                || addressQueryString!!.isEmpty()) {

                errorEvent.value = resourceProvider.getInvalidAddresserror()
            } else {
                validInputsLaunchFragmentEvent.value =
                        LaunchPackage(addressQueryString!!, pickupSelection!!, dropoffSelection!!)
            }
        } else {
            errorEvent.value = resourceProvider.getDateMissingError()
        }
    }
}
