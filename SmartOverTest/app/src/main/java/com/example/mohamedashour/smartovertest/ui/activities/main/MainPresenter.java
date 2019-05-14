package com.example.mohamedashour.smartovertest.ui.activities.main;

import android.util.Log;

import com.example.mohamedashour.smartovertest.MyApplication;
import com.example.mohamedashour.smartovertest.data.models.BurgerDataModel;
import com.example.mohamedashour.smartovertest.data.network.RetrofitSingleton;
import com.example.mohamedashour.smartovertest.utils.AppTools;
import com.example.mohamedashour.smartovertest.utils.AppUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.MainPresenter {

    MainContract.MainView view;
    Gson gson;

    public MainPresenter(MainContract.MainView view) {
        this.view = view;
        gson = new Gson();
    }

    @Override
    public void getData() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(RetrofitSingleton.webService().getData("mobiletest1.json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BurgerDataModel>>() {
                               @Override
                               public void accept(List<BurgerDataModel> list) throws Exception {
                                   view.receiveData(list);
                                   Log.e("Main", "DONE");
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Log.e("Main", throwable.getMessage() == null? "null" : throwable.getMessage());
                               }
                           }
                ));
    }

    @Override
    public void addToBasket(BurgerDataModel model) {
        // I could use Room database but the app task can be done by SharedPreference faster and easy
        List<BurgerDataModel> localList;
        String data = AppUtils.getFromSharedPreference(MyApplication.getContext(), AppTools.BASKET_KEY);
        if (data.equals("")) {
            localList = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<BurgerDataModel>>(){}.getType();
            localList = gson.fromJson(data, type);
        }
        localList.add(model);
        String jsonBasket = gson.toJson(localList);
        AppUtils.saveInSharedPreference(MyApplication.getContext(), AppTools.BASKET_KEY, jsonBasket);
        view.itemAddedSuccessfully();
    }
}
