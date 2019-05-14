package com.example.mohamedashour.smartovertest.ui.activities.main;

import com.example.mohamedashour.smartovertest.ConnectivityChangeReceiver;
import com.example.mohamedashour.smartovertest.data.models.BurgerDataModel;

import java.util.List;

public interface MainContract {

    interface MainView extends ConnectivityChangeReceiver.OnConnectivityChangedListener{
        void receiveData(List<BurgerDataModel> list);
        void itemAddedSuccessfully();
    }

    interface MainPresenter {
        void getData();
        void addToBasket(BurgerDataModel model);
    }
}
