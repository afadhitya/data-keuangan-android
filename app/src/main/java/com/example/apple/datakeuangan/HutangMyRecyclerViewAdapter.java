package com.example.apple.datakeuangan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HutangMyRecyclerViewAdapter extends RecyclerView.Adapter<HutangMyRecyclerViewAdapter.ViewHolder> {

    private List<HutangClass> mData;
    private HutangMyRecyclerViewAdapter.ItemClickListener mClickListener;
    private Context ctx;


    public enum TypeClick {
        IMAGE_LAGU,
        IMAGE_VIDEO;
    }

    public void refreshDataAdapter(List<HutangClass> newData){
        mData.clear();
        mData = newData;
    }

    // data is passed into the constructor
    HutangMyRecyclerViewAdapter(Context context, List<HutangClass> data) {
        this.ctx = context;
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public HutangMyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_hutang, parent, false);
        return new HutangMyRecyclerViewAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(HutangMyRecyclerViewAdapter.ViewHolder holder, int position) {
        HutangClass kamus = mData.get(position);
        holder.myTextIstilah.setText(Integer.toString(kamus.getIdHutang()));
        holder.myTextArti.setText(kamus.getKeteranganHutang());
        holder.myTextKeterangan.setText(NumberFormat.getNumberInstance(Locale.US).format(kamus.getJumlahHutang()));
//        holder.myTextKeterangan.setText(Integer.toString(kamus.getJumlahHutang()));
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
            myTextIstilah = (TextView) itemView.findViewById(R.id.idHutang);
            myTextArti = (TextView) itemView.findViewById(R.id.keteranganHutang);
            myTextKeterangan = (TextView) itemView.findViewById(R.id.banyaknyaHutang);
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
    HutangClass getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(HutangMyRecyclerViewAdapter.ItemClickListener itemClickListener) {
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
