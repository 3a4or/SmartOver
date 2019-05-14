package com.example.mohamedashour.smartovertest.ui.fragments.basket;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mohamedashour.smartovertest.MyApplication;
import com.example.mohamedashour.smartovertest.R;
import com.example.mohamedashour.smartovertest.data.models.BurgerDataModel;
import com.example.mohamedashour.smartovertest.utils.AppUtils;
import com.example.mohamedashour.smartovertest.utils.interfaces.RecyclerViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasketFragment extends Fragment implements BasketContract.BasketView {

    View rootView;
    @BindView(R.id.rv_basket_items)
    RecyclerView basketItemsRecyclerView;
    BasketAdapter adapter;

    BasketContract.BasketPresenter presenter;

    public BasketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_basket, container, false);
        ButterKnife.bind(this, rootView);
        init();

        return rootView;
    }

    private void init() {
        AppUtils.initVerticalRV(basketItemsRecyclerView, getActivity(), 1);
        // init basket presenter
        presenter = new BasketPresenter(this);
        // get local data
        presenter.getData();
    }

    @Override
    public void receiveData(List<BurgerDataModel> list) {
        adapter = new BasketAdapter(getActivity(), list, (v, position) -> presenter.deleteItem(list.get(position).getRef(), list));
        basketItemsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void itemDeletedSuccessfully() {
        Toast.makeText(getActivity(), MyApplication.getContext().getResources().getString(R.string.msg_deleted), Toast.LENGTH_SHORT).show();
        presenter.getData();
    }
}
