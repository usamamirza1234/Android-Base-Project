package com.ast.MyBills.MainAuxilaries.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ast.MyBills.MainAuxilaries.DModels.DModelBillDashboardInfo;
import com.ast.MyBills.MainAuxilaries.DModels.DModel_Bills;
import com.ast.MyBills.R;
import com.ast.MyBills.Utils.AppConstt;
import com.ast.MyBills.Utils.IAdapterCallback;

import java.util.ArrayList;



public class Dashboardinforcvadapter extends RecyclerView.Adapter<Dashboardinforcvadapter.ViewHolder> {

    private Integer selectedPosition = null;
    private  ArrayList<DModel_Bills> mData;
    private final Context mContext;
    private final IAdapterCallback iAdapterCallback;
    int _case;



    public Dashboardinforcvadapter(Context mContext, ArrayList<DModel_Bills> mData, int icase,
                                   IAdapterCallback iAdapterCallback) {
        this.mContext = mContext;
        this.mData = mData;
        this._case = icase;
        this.selectedPosition = selectedPosition;

        Log.d("selection", "selectedPosition " + selectedPosition);
        this.iAdapterCallback = iAdapterCallback;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_item_dashboardbill_info, null);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {






        holder.txv_bill.setText(mData.get(position).BillType);
        holder.txv_city.setText(mData.get(position).getDuedate());
        holder.txv_address.setText(mData.get(position).getPayableafterduedate());
//        holder.txv_status.setText(mData.get(position).Status);
//        holder.txv_act.setText(mData.get(position).Act);


        holder.txv_view.setOnClickListener(v -> iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_B, position));


        holder.llDetails.setOnClickListener(v -> {

            if (selectedPosition != null) {

                notifyItemChanged(selectedPosition);
            }
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
            iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A, position);
        });




        switch (_case)
        {

            case AppConstt.bill.OVERDUE:
                Log.d("LOG_AS","OverDue "+AppConstt.bill.OVERDUE);
                holder.itemView.setOnClickListener(v -> iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A, position));
                holder.llParent.setBackground(mContext.getResources().getDrawable(R.drawable.chb_btn_overdue_off));
                break;

            case AppConstt.bill.PAID:
                Log.d("LOG_AS","PAID "+AppConstt.bill.PAID);
                holder.itemView.setOnClickListener(v -> iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_B, position));
                holder.llParent.setBackground(mContext.getResources().getDrawable(R.drawable.chb_btn_paid_off));
                break;

            case AppConstt.bill.UPCOMING:
                Log.d("LOG_AS","UPCOMING "+AppConstt.bill.UPCOMING);
                holder.itemView.setOnClickListener(v -> iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_C, position));
                holder.llParent.setBackground(mContext.getResources().getDrawable(R.drawable.chb_btn_upcoming_off));
                break;

            case AppConstt.bill.ALL:
                Log.d("LOG_AS","ALL "+AppConstt.bill.ALL);
                holder.itemView.setOnClickListener(v -> iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_D, position));
                holder.llParent.setBackground(mContext.getResources().getDrawable(R.drawable.chb_btn_all_off));
                break;
        }

        holder.styleViewSection((selectedPosition != null && selectedPosition == holder.getAdapterPosition()), position);


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
        LinearLayout llDetails, llParent;
        TextView txv_bill, txv_city, txv_address, txv_view,txv_status,txv_act;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txv_address = itemView.findViewById(R.id.lay_bill_info_txv_address);
            txv_city = itemView.findViewById(R.id.lay_bill_info_txv_city);
            txv_bill = itemView.findViewById(R.id.lay_bill_info_txv_billtype);
            txv_view = itemView.findViewById(R.id.lay_bill_info_txv_view);
         //   txv_status = itemView.findViewById(R.id.lay_bill_info_txv_status);
           // txv_act = itemView.findViewById(R.id.lay_bill_info_txv_act);

            llDetails = itemView.findViewById(R.id.lay_bill_info_llDetails);
            llParent = itemView.findViewById(R.id.lay_bill_info_llParent);


        }

        public void styleViewSection(boolean b, int Position) {
            if (!b) {

//                if (_case==20){
//                    llParent.setBackground(mContext.getResources().getDrawable(R.drawable.chb_btn_paid_off));
//                }

//                llParent.setBackgroundColor(mContext.getResources().getColor(R.color.thm_gray_bg));
                txv_address.setTextColor(mContext.getResources().getColor(R.color.black));
                txv_city.setTextColor(mContext.getResources().getColor(R.color.black));
                txv_bill.setTextColor(mContext.getResources().getColor(R.color.black));
              //  txv_status.setTextColor(mContext.getResources().getColor(R.color.black));
               // txv_act.setTextColor(mContext.getResources().getColor(R.color.black));
                txv_view.setTextColor(mContext.getResources().getColor(R.color.black));

            } else {
                llParent.setBackgroundColor(mContext.getResources().getColor(R.color.thm_yellow_2));
                txv_address.setTextColor(mContext.getResources().getColor(R.color.white));
                txv_city.setTextColor(mContext.getResources().getColor(R.color.white));
                txv_bill.setTextColor(mContext.getResources().getColor(R.color.white));
              //  txv_status.setTextColor(mContext.getResources().getColor(R.color.white));
              //  txv_act.setTextColor(mContext.getResources().getColor(R.color.white));
                txv_view.setTextColor(mContext.getResources().getColor(R.color.thm_blue_dark));


            }

        }
    }
    public void filterList(ArrayList<DModel_Bills> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        mData = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

}
