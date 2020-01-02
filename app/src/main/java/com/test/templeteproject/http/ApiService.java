package com.test.templeteproject.http;


import com.test.templeteproject.bean.dittoresponse.DittoResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Vaqib
 */
public interface ApiService {
    @GET(ApiConstants.API_GET_DITTO)
    Observable<DittoResponse> getDittoResponse();


}