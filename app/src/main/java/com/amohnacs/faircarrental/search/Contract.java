package com.amohnacs.faircarrental.search;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public interface Contract {

    interface Provider {

        interface Callback {

        }
    }

    interface Presenter {

        void validateAddress(String addressString);

        void validatePickup(String pickupString);

        void validateDropOff(String dropoffString);
    }

    interface View {
        void addressValidity(boolean isValid);
    }
}
