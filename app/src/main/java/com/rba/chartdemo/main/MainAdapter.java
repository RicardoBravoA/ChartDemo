package com.rba.chartdemo.main;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rba.chartdemo.R;
import com.rba.chartdemo.model.entity.DataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricardobravo on 13/12/16.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<DataEntity> dataEntityList;
    static LayoutInflater inflater = null;
    private Context context;
    private MainPresenter mainPresenter;

    public MainAdapter(Context context, MainPresenter mainPresenter){
        this.context = context;
        this.mainPresenter = mainPresenter;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dataEntityList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    public void load(List<DataEntity> dataEntityList){
        this.dataEntityList = dataEntityList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataEntity dataEntity = dataEntityList.get(position);
        holder.lblDescription.setText(dataEntity.getDescription());
    }

    @Override
    public int getItemCount() {
        return dataEntityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView cvList;
        private TextView lblDescription;

        public ViewHolder(View view) {
            super(view);
            cvList = (CardView) view.findViewById(R.id.cvList);
            lblDescription = (TextView) view.findViewById(R.id.lblDescription);
            cvList.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.cvList){
                mainPresenter.onClickCardView(dataEntityList.get(getAdapterPosition()));
            }
        }
    }

}
