package com.example.mohamedashour.smartovertest.ui.fragments.basket;

import com.example.mohamedashour.smartovertest.MyApplication;
import com.example.mohamedashour.smartovertest.data.models.BurgerDataModel;
import com.example.mohamedashour.smartovertest.utils.AppTools;
import com.example.mohamedashour.smartovertest.utils.AppUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class BasketPresenter implements BasketContract.BasketPresenter {

    BasketContract.BasketView view;
    Gson gson;

    public BasketPresenter(BasketContract.BasketView view) {
        this.view = view;
        gson = new Gson();
    }

    @Override
    public void getData() {
        String data = AppUtils.getFromSharedPreference(MyApplication.getContext(), AppTools.BASKET_KEY);
        if (!data.equals("")) {
            Type type = new TypeToken<List<BurgerDataModel>>(){}.getType();
            List<BurgerDataModel> localList = gson.fromJson(data, type);
            view.receiveData(localList);
        }
    }

    @Override
    public void deleteItem(String ref, List<BurgerDataModel> localList) {
        for (int i = 0; i < localList.size(); i++) {
            BurgerDataModel model = localList.get(i);
            if (model.getRef().equals(ref)) {
                localList.remove(i);
                break;
            }
        }
        String jsonBasket = gson.toJson(localList);
        AppUtils.saveInSharedPreference(MyApplication.getContext(), AppTools.BASKET_KEY, jsonBasket);
        view.itemDeletedSuccessfully();
    }
}
