package com.amohnacs.common.mvp;


import android.support.v7.app.AppCompatActivity;

/**
 * Created by Adrian Mohnacs on 5/31/17.
 */

public abstract class MvpActivity <P extends BasePresenter<V>, V> extends AppCompatActivity {

    public abstract P getPresenter();

    public abstract V getMvpView();


    @Override
    protected void onStart() {
        super.onStart();
        if(getPresenter() != null) {
            getPresenter().subscribe(getMvpView());
            getPresenter().onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getPresenter() != null) {
            getPresenter().onResume();
        }
    }

    @Override
    public void onPause() {
        if(getPresenter() != null) {
            getPresenter().onPause();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if(getPresenter() != null) {
            getPresenter().onStop();
            getPresenter().unsubscribe(getMvpView());
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if(getPresenter() != null) {
            getPresenter().onDestroy();
        }
        super.onDestroy();
    }
}
