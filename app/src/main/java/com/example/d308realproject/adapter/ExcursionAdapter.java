package com.example.d308realproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308realproject.R;
import com.example.d308realproject.activity.ExcursionDetails;
import com.example.d308realproject.entities.Excursion;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursion> mExcursion;
    private final Context context;
    private final LayoutInflater mInflater;

    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionItemView;
        private final TextView excursionItemView2;
        public ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.textView2);
            excursionItemView2 = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Excursion current = mExcursion.get(position);
                Intent intent = new Intent(context, ExcursionDetails.class);
                intent.putExtra("id", current.getId());
                intent.putExtra("name", current.getExcursionName());
                intent.putExtra("price", current.getPrice());
                intent.putExtra("vacationID", current.getVacationID());
                context.startActivity(intent);
            });
        }
    }
    @NonNull
    @Override
    public ExcursionAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {

        if(mExcursion != null) {
            Excursion current = mExcursion.get(position);
            String name = current.getExcursionName();
            int vacationID = current.getVacationID();
            holder.excursionItemView.setText(name);
            holder.excursionItemView2.setText(Integer.toString(vacationID));
        } else {
            holder.excursionItemView.setText("No excursion name");
            holder.excursionItemView2.setText("No vacation ID");
        }
    }

    public void setExcursion(List<Excursion> excursion) {
        mExcursion = excursion;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mExcursion == null) {
            return 0;
        }
        return mExcursion.size();
    }

}
