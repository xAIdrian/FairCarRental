package com.amohnacs.faircarrental.search.ui

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.amohnacs.faircarrental.R
import com.amohnacs.faircarrental.common.ResourceProvider
import com.amohnacs.faircarrental.databinding.MainActivityBinding
import com.amohnacs.faircarrental.search.MainViewModel
import com.amohnacs.faircarrental.search.ui.SortingRadioDialog.SORTING_DIALOG_SELECTION
import java.lang.UnsupportedOperationException
import java.util.Calendar
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog


/**
 * Created by adrian mohnacs on 12/1/18
 */
class MainActivity: AppCompatActivity(),
        SearchResultsFragment.OnListFragmentInteractionListener,
        SortingRadioDialog.SortingDialogCallback, DatePickerDialog.OnDateSetListener {

    companion object {
        private val TAG = SearchActivity::class.java.simpleName

        private const val SAVED_INSTANCE_STATE_DIALOG_POSITION = "saved_instance_state_dialog_position"
        private const val SAVED_INSTANCE_STATE_PICKUP_DATE = "saved_instance_state_pickup_date"
        private const val SAVED_INSTANCE_STATE_DROP_OFF_DATE = "saved_instance_state_dropoff_date"
    }
    private lateinit var binding: MainActivityBinding

    private var viewModel: MainViewModel? = null

    private var colorChangingMenuIcon: Drawable? = null
    private var sortingIndex = 1

    private var pickupString: String? = null
    private var dropOffString: String? = null

    private var scrollRange: Int = 0
    private var isShown: Boolean = false

    private var calendar: Calendar = Calendar.getInstance()

    private var searchResultFragment: SearchResultsFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)

        setupViewModel()

        binding.viewmodel = viewModel
        binding.searchHeader.viewmodel = viewModel

        savedInstanceState?.let {
            sortingIndex = savedInstanceState.getInt(SAVED_INSTANCE_STATE_DIALOG_POSITION)
            pickupString = savedInstanceState.getString(SAVED_INSTANCE_STATE_PICKUP_DATE)
            dropOffString = savedInstanceState.getString(SAVED_INSTANCE_STATE_DROP_OFF_DATE)

            binding.searchHeader.pickupResultTextView.text = pickupString
            binding.searchHeader.dropoffResultTextView.text = dropOffString
        }

        searchResultFragment = SearchResultsFragment.newInstance(1)
        val tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.fragment_container, searchResultFragment!!, SearchResultsFragment.TAG)
        tr.addToBackStack(null)
        tr.commit()

        setupToolbar()

        binding.searchHeader.addressEditText.addTextChangedListener(
                SearchTextValidator(binding.searchHeader.addressEditText, binding))
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
            appBarLayout, verticalOffset ->

            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }
            //change the color of the menu icon
            if (colorChangingMenuIcon != null) {
                //scrollRange + verticalOffset == 0 is fully collapsed
                if (scrollRange + verticalOffset > -600) {
                    //collapse appBarLayout
                    isShown = true
                    colorChangingMenuIcon!!.setColorFilter(getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)
                } else if (isShown) {
                    //expanded map
                    isShown = false
                    colorChangingMenuIcon!!.setColorFilter(getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.let {
            it.putInt(SAVED_INSTANCE_STATE_DIALOG_POSITION, sortingIndex)
            it.putString(SAVED_INSTANCE_STATE_PICKUP_DATE, pickupString)
            it.putString(SAVED_INSTANCE_STATE_DROP_OFF_DATE, dropOffString)

            super.onSaveInstanceState(outState)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_search, menu)
        colorChangingMenuIcon = menu?.findItem(R.id.action_sort)?.icon
        if (colorChangingMenuIcon != null) {
            colorChangingMenuIcon!!.mutate() //makes mutable (opposite of immutable)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item?.getItemId()

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort) {
            val manager = supportFragmentManager

            val alert = SortingRadioDialog()
            val b = Bundle()
            b.putInt(SORTING_DIALOG_SELECTION, if (sortingIndex < 0) 1 else sortingIndex)
            alert.arguments = b

            alert.show(manager, SortingRadioDialog.TAG)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        viewModel?.dateSetChecker(view?.tag as String, year, monthOfYear, dayOfMonth)
    }

    override fun resultsLoading(setLoadingIndicator: Boolean) {
        binding.progressBar.visibility = if (setLoadingIndicator) View.VISIBLE else View.GONE
    }

    override fun onPositiveDialogClick(position: Int) {
        //they actually changed their sorting selection
        if (sortingIndex != position) {
            sortingIndex = position
            val f = getDisplayedFragment()
            f?.onPositiveDialogClick(sortingIndex)
        }
    }

    private fun setupViewModel() {
        viewModel = MainViewModel.getInstance(ResourceProvider(this))

        viewModel?.addressIsValid?.observe(this, Observer { isValid ->
            isValid?.let {
                if (it) {
                    binding.searchHeader.addressWrapper.isErrorEnabled = false
                } else {
                    try {
                        binding.searchHeader.addressWrapper.error = getString(R.string.address_error)
                        binding.searchHeader.addressWrapper.requestFocus()
                    } catch (e: UnsupportedOperationException){
                        e.printStackTrace()
                    }
                }
            }
        })

        viewModel?.displayPickupSelection?.observe(this, Observer {
            pickupString = it

            binding.searchHeader.pickupResultTextView.text = pickupString
            binding.searchHeader.dropoffButton.setCompoundDrawablesWithIntrinsicBounds(
                    0, R.drawable.ic_calendar_primary, 0, 0)
            binding.searchHeader.pickupButton.setCompoundDrawablesWithIntrinsicBounds(
                    0, R.drawable.ic_calendar_today_accent, 0, 0)
        })

        viewModel?.displayDropoffSelection?.observe(this, Observer {
            dropOffString = it

            binding.searchHeader.dropoffResultTextView.text = dropOffString
            binding.searchHeader.dropoffButton.setCompoundDrawablesWithIntrinsicBounds(
                    0, R.drawable.ic_calendar_accent, 0, 0)
        })

        viewModel?.errorEvent?.observe(this, Observer {

            val builder = AlertDialog.Builder(this)
            builder.setMessage(it ?: "Default Error :/")
                    .setTitle(R.string.param_error_title)
                    .setPositiveButton(R.string.ok) { dialog, _ ->
                        // User clicked OK button
                        dialog.dismiss()
                    }
                    .create()
                    .show()
        })

        viewModel?.validInputsLaunchFragmentEvent?.observe(this, Observer { launchPackage ->

            getDisplayedFragment()?.makeCarResultsRequest(
                        launchPackage?.addressQueryString,
                        launchPackage?.pickupSelection,
                        launchPackage?.dropoffSelection
                )

        })

        viewModel?.displayPickupDialogEvent?.observe(this, Observer { tag ->
            tag?.let { showDatePickerDialog(it) }
        })

        viewModel?.displayDropoffDialogEvent?.observe(this, Observer { tag ->
            tag?.let { showDatePickerDialog(it) }
        })
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.transparent))
        binding.toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun getDisplayedFragment(): SearchResultsFragment? {
        val fragment = supportFragmentManager.findFragmentByTag(SearchResultsFragment.TAG) as? SearchResultsFragment?
        supportFragmentManager.executePendingTransactions()
        return fragment
    }

    private fun showDatePickerDialog(tag: String) {
        val datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.setOnCancelListener { viewModel?.updateDatePickerActive(false) }
        datePickerDialog.setOnDismissListener { viewModel?.updateDatePickerActive(false)  }
        datePickerDialog.accentColor = ContextCompat.getColor(this, R.color.colorPrimary)
        datePickerDialog.vibrate(true)
        datePickerDialog.dismissOnPause(true)

        datePickerDialog.show(fragmentManager, tag)
    }

    /**
     * When the user has finished entering data in edit text we send the string to the presenter
     * which ensures the string is not empty
     */
    inner class SearchTextValidator(
            private val view: View,
            private val binding: MainActivityBinding
    ) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            //no op
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //no op
        }

        override fun afterTextChanged(s: Editable) {
            when (view.id) {
                R.id.address_editText -> {
                    //yes yes i know. this should generified
                    val addressString = binding.searchHeader.addressEditText.text.toString().trim()
                    viewModel?.validateAddress(addressString)
                }
            }
        }
    }
}