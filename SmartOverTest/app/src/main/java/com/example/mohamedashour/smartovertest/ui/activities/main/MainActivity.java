package com.example.mohamedashour.smartovertest.ui.activities.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.mohamedashour.smartovertest.ConnectivityChangeReceiver;
import com.example.mohamedashour.smartovertest.MyApplication;
import com.example.mohamedashour.smartovertest.R;
import com.example.mohamedashour.smartovertest.data.models.BurgerDataModel;
import com.example.mohamedashour.smartovertest.ui.fragments.basket.BasketFragment;
import com.example.mohamedashour.smartovertest.utils.AppTools;
import com.example.mohamedashour.smartovertest.utils.AppUtils;
import com.example.mohamedashour.smartovertest.utils.interfaces.RecyclerViewClickListener;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    @BindView(R.id.fl_main_container)
    FrameLayout containerFrameLayout;
    @BindView(R.id.rv_main_burger_items)
    RecyclerView burgerItemsRecyclerView;
    BurgerAdapter adapter;

    @BindView(R.id.swl_refresh)
    SwipeRefreshLayout refreshLayout;

    MainContract.MainPresenter presenter;

    ConnectivityChangeReceiver connectivityChangeReceiver;
    AlertDialog noConnectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        setupNetworkListener();
    }

    // init widgets
    private void init() {
        AppUtils.initVerticalRV(burgerItemsRecyclerView, this, 2);
        // init our main presenter
        presenter = new MainPresenter(this);
        // make presenter fetch our data
        presenter.getData();
        refreshLayout.setOnRefreshListener(() -> presenter.getData());

        // test method
        double[] array = {7, -10, 13, 8, 4, -7.2, -12, -3.7, 3.5, -9.6, 6.5, -1.7, -6.2, 7};
        //double[] array = {9,5,-5,-9};
        closestToZero(array);
    }

    // to check for internet connection
    private void setupNetworkListener() {
        connectivityChangeReceiver = new ConnectivityChangeReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectivityChangeReceiver, filter);
    }

    private AlertDialog showAlertDialog(String title, String msg, String buttonName, int icon, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialog);
        dialogBuilder.setTitle(title);
        dialogBuilder.setIcon(icon);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton(buttonName, listener);
        dialogBuilder.setNegativeButton("Close", listener);

        dialogBuilder.setCancelable(false);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    private void noInternetConnectionAvailable() {
        noConnectionDialog = showAlertDialog("Offline", getString(R.string.label_no_internet), "WiFi Settings", R.drawable.ic_delete,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //showProgressDialog(getString(R.string.label_connecting), getString(R.string.message_reconnect));
                        switch (which) {
                            case BUTTON_POSITIVE:
                                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                dialog.cancel();
                                break;
                            case BUTTON_NEGATIVE:
                                dialog.cancel();
                                break;
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tb_cart:
                AppUtils.openFragmentFromActivity(MainActivity.this, new BasketFragment(),
                        R.id.fl_main_container, AppTools.STACK_NAME);
                break;
        }
        return true;
    }

    @Override
    public void onConnectivityChanged(boolean isConnected) {
        if (isConnected) {
            if (noConnectionDialog != null) {
                noConnectionDialog.dismiss();
            }
        } else {
            noInternetConnectionAvailable();
        }
    }

    @Override
    public void receiveData(List<BurgerDataModel> list) {
        if (list != null && list.size() > 0) {
            adapter = new BurgerAdapter(this, list, (v, position) -> presenter.addToBasket(list.get(position)));
            burgerItemsRecyclerView.setAdapter(adapter);
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void itemAddedSuccessfully() {
        Toast.makeText(this, MyApplication.getContext().getResources().getString(R.string.msg_added), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectivityChangeReceiver);
    }

    // method closestToZero
    private void closestToZero(double[] array) {
        double currentNumber = 0.0;
        double closestNumber = array[0];
        Arrays.sort(array);

        for ( int i = 0; i < array.length; i++ ){
            currentNumber = Math.abs(array[i]);
            if ( currentNumber <= Math.abs(closestNumber) )  {
                closestNumber = array[i];
            }
        }
        System.out.println( closestNumber );
        Toast.makeText(this, String.valueOf(closestNumber), Toast.LENGTH_LONG).show();
    }
}
