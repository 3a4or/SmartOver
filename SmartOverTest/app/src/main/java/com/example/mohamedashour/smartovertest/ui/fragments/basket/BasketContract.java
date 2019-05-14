package com.example.mohamedashour.smartovertest.ui.fragments.basket;

import com.example.mohamedashour.smartovertest.data.models.BurgerDataModel;

import java.util.List;

public interface BasketContract {

    interface BasketView {
        void receiveData(List<BurgerDataModel> list);
        void itemDeletedSuccessfully();
    }

    interface BasketPresenter {
        void getData();
        void deleteItem(String ref, List<BurgerDataModel> localList);
    }
}
