<img width="1280" alt="screen shot 2018-04-25 at 1 56 19 am" src="https://user-images.githubusercontent.com/7444521/39235864-f0848110-482b-11e8-9b96-bd5c5f06301a.png">

# FairCarRental
https://github.com/wearefair/interview/blob/master/ios/README.md

Android application that pulls data from the Car Rental Geosearch API provide by Amadeus Travel and Google Map  and Directions APIs.  The MVP design pattern is used in coordination with clean architecture principles to ensure a separation of responsibilites and loose coupling betwen layers.

We retrieve the data we need using OkHttp3, Retrofit, and Google's GSON library.  After making our initial request to Google's GeoCoding api we are returned a latitude and longitude coordinate of our initial search address.  A second request is made to get the list of rental cars that are nearby.  This is done easily enough with a RecyclerView and Adapter.  The user then has the option to sort the list of results by company, distance, and price in ascending and descending order.  Java provides the Comparator interface to be overridden to allow sorting through addition or subration expressions.

Clicking on an items passes the selected car object as a Parcelable to the next activity, the DetailsActivity.  Parcelable is used over Serializable because Parcelable assumes a certain structure and way of processing it, which makes it more performant and efficient (at the cost of more boilerplate code).  We use a Google Maps Lite MapView as the header for the details view using the coordinates of the rental car to set the camera position and markers.  Everyhting else is simple enough.

Clicking through we make it to our NavigationAcitivity.  Here we have a Google Maps Fragment displayed as a header to allow us to dynamically set multiple markers and poly lines.  Based on our network connectivity status we are unsure as to how long the making the request to Directions API is going to take to come back to us so we create an AsyncTask where the download is handled in the background and the map is update onPostResult (on the ui/main thread).  Before we get to that point through we are using the DirectionsAPI provide by Google and we are using each leg's steps to display the information we need to display the appropriately decoded polylines and steps in our recyclerview.  

![screenshot_20180425-135215](https://user-images.githubusercontent.com/7444521/39272594-33548872-4891-11e8-9675-9d86b034ef30.png)

![screenshot_20180425-135251](https://user-images.githubusercontent.com/7444521/39272600-3a00edc8-4891-11e8-9143-5c6b7e9cb660.png)

![screenshot_20180425-135302](https://user-images.githubusercontent.com/7444521/39272641-55ba97b2-4891-11e8-8178-7014eb198f9d.png)

If you have any questions, comments, or concerns please do not hesitate to reach out to me at amohnacs@gmail.com

