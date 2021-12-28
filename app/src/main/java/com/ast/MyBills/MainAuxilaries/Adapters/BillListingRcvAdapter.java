package com.ast.MyBills.MainAuxilaries.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bills;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.IAdapterCallback;

import java.util.ArrayList;




public class BillListingRcvAdapter extends RecyclerView.Adapter<BillListingRcvAdapter.ViewHolder> {


    private final ArrayList<DModel_Bills> mData;
    private final Context mContext;
    private final IAdapterCallback iAdapterCallback;


    public BillListingRcvAdapter(Context mContext, ArrayList<DModel_Bills> mData,
                                 IAdapterCallback iAdapterCallback) {
        this.mContext = mContext;
        this.mData = mData;

        this.iAdapterCallback = iAdapterCallback;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_item_my_bills, null);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {



        holder.txv_bill.setText(mData.get(position).BillType);
        holder.txv_ref.setText(mData.get(position).Refference_number);
        holder.txv_acc.setText(mData.get(position).Account_number);

        holder.itemView.setOnClickListener(v -> iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A, position));
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txv_bill, txv_ref, txv_acc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txv_acc = itemView.findViewById(R.id.lay_my_bill_txv_acc);
            txv_ref = itemView.findViewById(R.id.lay_my_bill_txv_Ref);
            txv_bill = itemView.findViewById(R.id.lay_my_bill_txv_billtype);


        }
    }

}
