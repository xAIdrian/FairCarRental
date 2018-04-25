<img width="1280" alt="screen shot 2018-04-25 at 1 56 19 am" src="https://user-images.githubusercontent.com/7444521/39235864-f0848110-482b-11e8-9b96-bd5c5f06301a.png">

# FairCarRental
https://github.com/wearefair/interview/blob/master/ios/README.md

Android application that pulls data from the Car Rental Geosearch API provide by Amadeus Travel and Google Map  and Directions APIs.  The MVP design pattern is used in coordination with clean architecture principles to ensure a separation of responsibilites and loose coupling betwen layers.

We retrieve the data we need using OkHttp3, Retrofit, and Google's GSON library.  After making our initial request to Google's GeoCoding api we are returned a latitude and longitude coordinate of our initial search address.  A second request is made to get the list of rental cars that are nearby.  This is done easily enough with a RecyclerView and Adapter.  The user then has the option to sort the list of results by company, distance, and price in ascending and descending order.  Java provides the Comparator interface to be overridden to allow sorting through addition or subration expressions.

Clicking on an items passes the selected car object as a Parcelable to the next activity, the DetailsActivity.  Parcelable is used over Serializable because Parcelable assumes a certain structure and way of processing it, which makes it more performant and efficient (at the cost of more boilerplate code).  We use a Google Maps Lite MapView as the header for the details view using the coordinates of the rental car to set the camera position and markers.  Everyhting else is simple enough.





How to run the project
