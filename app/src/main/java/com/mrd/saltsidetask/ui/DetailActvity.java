package com.mrd.saltsidetask.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.volley.toolbox.NetworkImageView;
import com.mrd.saltsidetask.R;
import com.mrd.saltsidetask.model.Element;
import com.mrd.saltsidetask.utils.Common;
import com.mrd.saltsidetask.utils.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActvity extends AppCompatActivity {

    @BindView(R.id.txt_title)
    CustomTextView txtTitle;
    @BindView(R.id.txt_description)
    CustomTextView txtDescription;
    @BindView(R.id.img_element)
    NetworkImageView imgElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_actvity);
        ButterKnife.bind(this);
        Element element = (Element) getIntent().getSerializableExtra("element");
        if(element != null) {
            setupUI(element);
        }
    }

    public void setupUI(Element element) {
        txtTitle.setText(element.getTitle());
        txtDescription.setText(element.getDescription());
        if(!TextUtils.isEmpty(element.getImage())) {
            // replace http with https because http giving an error of 301:moved permanently
            String imageUrl = element.getImage().replace("http", "https");
            Common.loadImage(getApplicationContext(), imgElement, imageUrl, R.drawable.placeholder);
        }
    }
}
