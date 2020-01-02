package com.test.templeteproject.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.test.templeteproject.R;
import com.test.templeteproject.bean.dittoresponse.DittoResponse;
import com.test.templeteproject.http.Http;
import com.test.templeteproject.http.NetworkUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (NetworkUtil.isNetworkAvailable(this)) {
            Http.getApiService().getDittoResponse()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DittoResponse>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(DittoResponse dittoResponse) {

                        }
                    });

        }
    }
}
