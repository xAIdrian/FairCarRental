package com.amohnacs.faircarrental.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.amohnacs.faircarrental.R
import com.amohnacs.faircarrental.MyUtils
import com.amohnacs.model.amadeus.Address
import com.amohnacs.model.amadeus.Car
import com.amohnacs.model.amadeus.AmadeusLocation
import com.amohnacs.model.amadeus.VehicleInfo
import com.amohnacs.model.googlemaps.LatLngLocation

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

import com.amohnacs.faircarrental.MyUtils.getDistanceString

class CarAdapter(
        items: ArrayList<Car>,
        private val recyclerClickListener: RecyclerClickListener,
        private val presenter: SearchResultsPresenter
) : RecyclerView.Adapter<CarAdapter.CarResultViewHolder>() {

    private val values: List<Car>

    init {
        values = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarResultViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.search_results_fragment_item, parent, false)
        return CarResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarResultViewHolder, position: Int) {
        val car = values[position]
        val vi = car.vehicleInfo

        holder.setTitle(vi.acrissCode + " : " + vi.category + " " + vi.type)
        holder.setCompany(car.companyName)

        val address = car.address
        val addressString = (address.line1 + ", " + address.city + ", " + address.country
                + ", " + address.postalCode)
        holder.setAddress(addressString)

        holder.setCalculatedDistance(
                getDistanceString(car.amadeusLocation, presenter.userLatLngLocation!!)
        )

        val formatter = NumberFormat.getCurrencyInstance()
        val output = formatter.format(car.estimatedTotal.amount.toDouble())
        holder.setPrice(PRICE_PREPEND + output)

    }

    override fun getItemCount(): Int {
        return values.size
    }

    //////////////

    inner class CarResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.company_code_name_textView)
        var companyTitleTextView: TextView? = null
        @BindView(R.id.company_textView)
        var companyTextView: TextView? = null
        @BindView(R.id.address_textView)
        var addressTextView: TextView? = null
        @BindView(R.id.distance_textView)
        var distanceTextView: TextView? = null
        @BindView(R.id.price_textView)
        var priceTextView: TextView? = null

        init {

            ButterKnife.bind(this, view)

            view.setOnClickListener { recyclerClickListener.onItemClick(values[adapterPosition]) }
        }

        fun setTitle(title: String) {
            companyTitleTextView!!.text = title
        }

        fun setCompany(airport: String) {
            companyTextView!!.text = airport
        }

        fun setAddress(address: String) {
            addressTextView!!.text = address
        }

        fun setCalculatedDistance(calculatedDistance: String) {
            distanceTextView!!.text = calculatedDistance
        }

        fun setPrice(price: String) {
            priceTextView!!.text = price
        }
    }

    interface RecyclerClickListener {
        fun onItemClick(car: Car)
    }

    companion object {

        val PRICE_PREPEND = "Price : "
    }
}
