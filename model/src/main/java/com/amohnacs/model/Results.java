package com.amohnacs.model;

import java.util.List;

/**
 * Created by adrianmohnacs on 4/20/18.
 */

public class Results {

    private List<Result> results;

    public Results() {
    }

    public Results(List<Result> results) {
        this.results = results;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
