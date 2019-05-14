package com.example.mohamedashour.smartovertest.data.network;

import com.example.mohamedashour.smartovertest.data.models.BurgerDataModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {
    @GET
    Observable<List<BurgerDataModel>> getData(@Url String url);
}
