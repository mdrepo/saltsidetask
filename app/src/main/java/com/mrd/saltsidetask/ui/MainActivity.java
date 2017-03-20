package com.mrd.saltsidetask.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mrd.saltsidetask.R;
import com.mrd.saltsidetask.adapter.ElementAdapter;
import com.mrd.saltsidetask.controller.Controller;
import com.mrd.saltsidetask.controller.ElementController;
import com.mrd.saltsidetask.model.Element;
import com.mrd.saltsidetask.utils.CustomTextView;
import com.mrd.saltsidetask.utils.OnClickListener;
import com.mrd.saltsidetask.utils.VolleyErrorListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mayurdube on 19/03/17.
 */

public class MainActivity extends AppCompatActivity implements Handler.Callback, OnClickListener {

    @BindView(R.id.lst_elements)
    RecyclerView lstElements;
    @BindView(R.id.activity_main)
    View root;
    ProgressDialog mProgress;
    ArrayList<Element> elements;
    boolean isFirstrun = true;
    Handler mHandler;
    ElementController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mHandler = new Handler(this);
        mProgress = new ProgressDialog(this);
        controller = ElementController.getInstance(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.addOutboxHandler(mHandler);
        if (isFirstrun) {
            getData();
            isFirstrun = false;
        }
    }

    private void getData() {
        mProgress.setMessage(getString(R.string.loading));
        mProgress.show();
        controller.handleMessage(ElementController.GET_DATA);
    }

    @Override
    protected void onPause() {
        super.onPause();
        controller.removeOutboxHandler(mHandler);
    }

    @Override
    public boolean handleMessage(Message message) {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
        }
        String msg = "Something went wrong. Please try again";
        boolean hasFailed = false;
        int length = Snackbar.LENGTH_INDEFINITE;
        switch (message.what) {
            case ElementController.GET_DATA:
                elements = (ArrayList<Element>) message.obj;
                if (elements != null) {
                    lstElements.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    ElementAdapter adapter = new ElementAdapter(elements, MainActivity.this);
                    lstElements.setAdapter(adapter);
                }
                break;
            case ElementController.EMPTY_DATA:
                hasFailed = true;
                msg = "No data to show";
                break;
            case Controller.FAILURE:
                hasFailed = true;
                switch (message.arg1) {
                    case VolleyErrorListener.INTERNET_ERROR:
                        msg = "Please check internet connection and try again";
                        break;
                    case VolleyErrorListener.SERVER_ERROR:
                        msg = "Cannot connect to server, Please try again";
                        break;
                }
                break;
        }

        if (hasFailed) {
            Snackbar snackbar = Snackbar
                    .make(root, msg, length)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getData();
                        }
                    });
            snackbar.show();
        }
        return false;
    }

    private void openDetail(Element element, CustomTextView txt, CustomTextView txt2) {
        Intent intent = new Intent(MainActivity.this, DetailActvity.class);
        intent.putExtra("element", element);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && element != null) {
            Pair<View, String> p1 = Pair.create((View) txt, getString(R.string.title));
            Pair<View, String> p2 = Pair.create((View) txt2, getString(R.string.description));
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, p1, p2);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onClick(Object ele, Object obj) {
        if (ele instanceof Element && obj instanceof RecyclerView.ViewHolder) {
            Element element = (Element) ele;
            ElementAdapter.EAViewHolder holder = (ElementAdapter.EAViewHolder) obj;
            openDetail(element, holder.txtTitle, holder.txtDescription);
        }
    }
}
