package com.example.apple.datakeuangan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class HistoryMyRecyclerViewAdapter extends RecyclerView.Adapter<HistoryMyRecyclerViewAdapter.ViewHolder>{
private List<HistoryKeuanganClass> mData;
private HistoryMyRecyclerViewAdapter.ItemClickListener mClickListener;
private Context ctx;


public enum TypeClick {
    IMAGE_LAGU,
    IMAGE_VIDEO;
}

    public void refreshDataAdapter(List<HistoryKeuanganClass> newData){
        mData.clear();
        mData = newData;
    }

    // data is passed into the constructor
    HistoryMyRecyclerViewAdapter(Context context, List<HistoryKeuanganClass> data) {
        this.ctx = context;
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public HistoryMyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_history, parent, false);
        return new HistoryMyRecyclerViewAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(HistoryMyRecyclerViewAdapter.ViewHolder holder, int position) {
        HistoryKeuanganClass history = mData.get(position);
        holder.idHistoryTV.setText(Integer.toString(history.getIdHistory()));
        holder.hariTV.setText(history.getHariHistory());
        holder.tanggalTV.setText(history.getTanggalHistory());
        holder.keteranganTV.setText(history.getKeteranganHistory());
        holder.banyaknyaTV.setText(Integer.toString(history.getJumlahHistory()));
        holder.jenisTV.setText(history.getMasukAtauKeluar());
        holder.dariKeManaTV.setText(history.getNamaPenyimpanan());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

// stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView idHistoryTV;
        TextView hariTV;
        TextView tanggalTV;
        TextView keteranganTV;
        TextView banyaknyaTV;
        TextView jenisTV;
        TextView dariKeManaTV;


        ViewHolder(View itemView) {
            super(itemView);
            idHistoryTV = (TextView) itemView.findViewById(R.id.idHistory);
            hariTV = (TextView) itemView.findViewById(R.id.hariHistory);
            tanggalTV = (TextView) itemView.findViewById(R.id.tanggalHistory);
            keteranganTV = (TextView) itemView.findViewById(R.id.keteranganHistory);
            banyaknyaTV = (TextView) itemView.findViewById(R.id.banyaknyaHistory);
            jenisTV = (TextView) itemView.findViewById(R.id.jenisHistory);
            dariKeManaTV = (TextView) itemView.findViewById(R.id.dariKeMana);
            itemView.setOnClickListener(this);//event click untuk view
            idHistoryTV.setOnClickListener(this);//
        }

        @Override
        public void onClick(View view) {
    //            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            // untuk click view
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    // convenience method for getting data at click position
    HistoryKeuanganClass getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(HistoryMyRecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

// parent activity will implement this method to respond to click events untuk view
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
