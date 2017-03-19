package com.mrd.saltsidetask.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrd.saltsidetask.R;
import com.mrd.saltsidetask.model.Element;
import com.mrd.saltsidetask.ui.MainActivity;
import com.mrd.saltsidetask.utils.CustomTextView;
import com.mrd.saltsidetask.utils.OnClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mayurdube on 19/03/17.
 */

public class ElementAdapter extends RecyclerView.Adapter<ElementAdapter.EAViewHolder> {

    ArrayList<Element> mElementList;
    OnClickListener mListener;

    public ElementAdapter(ArrayList<Element> list, OnClickListener listener) {
        mElementList = list;
        mListener = listener;
    }

    @Override
    public EAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_element, parent, false);
        EAViewHolder holder = new EAViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(EAViewHolder holder, int position) {
        Element element = mElementList.get(position);
        holder.bind(element);

    }

    @Override
    public int getItemCount() {
        return mElementList == null ? 0 : mElementList.size();
    }

    public class EAViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_title)
        public CustomTextView txtTitle;
        @BindView(R.id.txt_description)
        public CustomTextView txtDescription;

        public EAViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(Element element) {
            txtTitle.setText(element.getTitle());
            txtDescription.setText(element.getDescription());
        }

        @Override
        public void onClick(View view) {
            if(mListener != null) {
                Element element = mElementList.get(getAdapterPosition());
                mListener.onClick(element,this);
            }
        }
    }
}
