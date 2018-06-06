package com.example.apple.datakeuangan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewClickRefreshAdapter extends RecyclerView.Adapter<MyRecyclerViewClickRefreshAdapter.ViewHolder> {

    private List<PenyimpananClass> mData;
    private ItemClickListener mClickListener;
    private Context ctx;


    public enum TypeClick {
        IMAGE_LAGU,
        IMAGE_VIDEO;
    }

    public void refreshDataAdapter(List<PenyimpananClass> newData){
        mData.clear();
        mData = newData;
    }

    // data is passed into the constructor
    MyRecyclerViewClickRefreshAdapter(Context context, List<PenyimpananClass> data) {
        this.ctx = context;
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_penyimpanan, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PenyimpananClass kamus = mData.get(position);
        holder.myTextIstilah.setText(Integer.toString(kamus.getIdPenyimpanan()));
        holder.myTextArti.setText(kamus.getNamaPenyimpanan());
        holder.myTextKeterangan.setText(Integer.toString(kamus.getIsiPenyimpanan()));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextIstilah;
        TextView myTextArti;
        TextView myTextKeterangan;


        ViewHolder(View itemView) {
            super(itemView);
            myTextIstilah = (TextView) itemView.findViewById(R.id.idPenyimpanan);
            myTextArti = (TextView) itemView.findViewById(R.id.namaPenyimpanan);
            myTextKeterangan = (TextView) itemView.findViewById(R.id.isiPenyimpanan);
            itemView.setOnClickListener(this);//event click untuk view
            myTextIstilah.setOnClickListener(this);//
            myTextKeterangan.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            // untuk click view
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    // convenience method for getting data at click position
    PenyimpananClass getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events untuk view
    //    public interface ItemClickListener {
    //        void onItemClick(View view, int position);
    //    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

//    private ArrayList<PenyimpananClass> mData;
//    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;
//    private Context ctx;
//
//    public void refreshDataAdapter(ArrayList<PenyimpananClass> newData){
//        mData.clear();
//        mData = newData;
//    }
//
//    // data is passed into the constructor
//    MyRecyclerViewClickRefreshAdapter(Context context, ArrayList<PenyimpananClass> data) {
//        this.ctx = context;
//        this.mData = data;
//    }
//
//    // inflates the row layout from xml when needed
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_penyimpanan, parent, false);
//        return new ViewHolder(view);
//    }
//
//    // binds the data to the TextView in each row
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        PenyimpananClass penyimpananClass = mData.get(position);
//        holder.idPenyimpanan.setText(penyimpananClass.getIdPenyimpanan());
//        holder.namaPenyimpanan.setText(penyimpananClass.getNamaPenyimpanan());
//        holder.isiPenyimpanan.setText(penyimpananClass.getIsiPenyimpanan());
//    }
//
//    // total number of rows
//    @Override
//    public int getItemCount() {
//        return mData.size();
//    }
//
//    // stores and recycles views as they are scrolled off screen
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        TextView idPenyimpanan;
//        TextView namaPenyimpanan;
//        TextView isiPenyimpanan;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            idPenyimpanan = (TextView) itemView.findViewById(R.id.idPenyimpanan);
//            namaPenyimpanan = (TextView) itemView.findViewById(R.id.namaPenyimpanan);
//            isiPenyimpanan = (TextView) itemView.findViewById(R.id.isiPenyimpanan);
//            itemView.setOnClickListener(this);//event click
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
//    }
//
//    // convenience method for getting data at click position
//    PenyimpananClass getItem(int id) {
//        return mData.get(id);
//    }
//
//    // allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
//
//    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
}
