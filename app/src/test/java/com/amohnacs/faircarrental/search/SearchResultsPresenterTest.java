package com.amohnacs.faircarrental.search;


import com.amohnacs.model.amadeus.AmadeusResult;
import com.amohnacs.model.googlemaps.LatLngLocation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class SearchResultsPresenterTest {

    private static final float TEST_LAT = (float) 101.01;
    private static final float TEST_LONG = (float) 200.02;

    @Spy
    private SearchResultsPresenter presenter;

    private ArrayList<AmadeusResult> mockResultsList = AmadeusMockData.getMockResults();
    private LatLngLocation mockLatLngLocation = new LatLngLocation(TEST_LAT, TEST_LONG);

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        presenter = new SearchResultsPresenter();
    }

    @After
    public void cleanUp() {
        presenter = null;
    }

    @Test
    public void sortCarsByCompanyDescending() {

    }

    @Test
    public void sortCarsByDistanceDescending() {
    }

    @Test
    public void sortCarsByPriceDescending() {
    }

    @Test
    public void sortCarsByCompanyAscending() {
    }

    @Test
    public void sortCarsByDistanceAscending() {
    }

    @Test
    public void sortCarsByPriceAscending() {
    }
}