package com.amohnacs.faircarrental.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.amohnacs.common.mvvm.SingleLiveEvent
import com.amohnacs.faircarrental.common.ResourceProvider
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

class MainViewModel(
        private val resourceProvider: ResourceProvider) : ViewModel() {

    var addressIsValid: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    var displayPickupSelection: MutableLiveData<String> = MutableLiveData()
    var displayDropoffSelection: MutableLiveData<String> = MutableLiveData()

    var errorEvent: SingleLiveEvent<String> = SingleLiveEvent()
    var displayPickupDialogEvent: SingleLiveEvent<String> = SingleLiveEvent()
    var displayDropoffDialogEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val validInputsLaunchFragmentEvent: SingleLiveEvent<LaunchPackage> = SingleLiveEvent()

    private var stringQueryFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var yesterday: Date = Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)
    private var datePickerActive = false //datepickerdialog is a little laggy

    private var addressQueryString: String? = null
    private var pickupDate: Date? = null
    private var dropoffDate: Date? = null

    companion object {
        private val TAG = SearchPresenter::class.java.simpleName

        private val DATE_HELPER_ZERO = "0"
        private val PICKUP_DIALOG = "pickup_dialog"
        private val DROPOFF_DIALOG = "dropoff_dialog"

        @Volatile
        private var instance: MainViewModel? = null

        @JvmStatic
        fun getInstance(resourceProvider: ResourceProvider): MainViewModel? {
            if (instance == null) {
                synchronized(SearchPresenter::class.java) {
                    if (instance == null) {
                        instance = MainViewModel(resourceProvider)
                    }
                }
            }
            return instance
        }
    }

    fun onSearchFabClick() {
        if(!datePickerActive) {
            datePickerActive = true
            validateInputsForSearch()
        }
    }

    fun onPickupButtonClick() {
        if (!datePickerActive) {
            datePickerActive = false
            displayPickupDialogEvent.value = PICKUP_DIALOG
        }
    }

    fun onDropoffButtonClick() {
        if(!datePickerActive) {
            datePickerActive = true
            displayDropoffDialogEvent.value = DROPOFF_DIALOG
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

        datePickerActive = false

        try {
            selectedDate = stringQueryFormat.parse(dateQueryString) ?: Date()
        } catch (e: ParseException) {

            e.printStackTrace()
            errorEvent.value = e.message
        }

        when (viewTag) {
            PICKUP_DIALOG -> {
                pickupDate = selectedDate
                displayPickupSelection.value = stringQueryFormat.format(pickupDate)
            }
            DROPOFF_DIALOG -> {
                dropoffDate = selectedDate
                displayDropoffSelection.value = stringQueryFormat.format(dropoffDate)
            }
            else -> Log.e(TAG, "inappropriate tag for date picker dialog")
        }
    }

    private fun validateInputsForSearch() {
        //gets our query formatted string
        if (pickupDate != null && dropoffDate != null) {

            datePickerActive = false

            val pickupSelection = stringQueryFormat.format(pickupDate)
            val dropoffSelection = stringQueryFormat.format(dropoffDate)
            val safeAddressQueryString = addressQueryString

            val addressValidated = addressIsValid.value == false // this is null or not valid
                || safeAddressQueryString == null
                || safeAddressQueryString.isEmpty()

            if (!pickupDate!!.after(yesterday)) {
                errorEvent.value = resourceProvider.getPickupBeforeError()

            } else if (!dropoffDate!!.after(pickupDate)) {
                errorEvent.value = resourceProvider.getPickupAfterError()

            } else if (addressValidated) {
                errorEvent.value = resourceProvider.getInvalidAddresserror()

            } else {
                validInputsLaunchFragmentEvent.value =
                        LaunchPackage(addressQueryString!!, pickupSelection!!, dropoffSelection!!)
            }
        } else {
            errorEvent.value = resourceProvider.getDateMissingError()
        }
    }

    fun updateDatePickerActive(isActive: Boolean) {
        datePickerActive = isActive
    }
}
