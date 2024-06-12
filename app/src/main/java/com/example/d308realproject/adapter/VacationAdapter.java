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
import com.example.d308realproject.activity.VacationDetails;
import com.example.d308realproject.entities.Vacations;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder>{

    private List<Vacations> mVacations;
    private final Context context;
    private final LayoutInflater mInflator;
    public VacationAdapter(Context context) {
        mInflator= LayoutInflater.from(context);
        this.context=context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationItemView;
        public VacationViewHolder(@NonNull View itemView) {
        super(itemView);
        vacationItemView = itemView.findViewById(R.id.textView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=getAdapterPosition();
                final Vacations current=mVacations.get(position);
                Intent intent = new Intent(context, VacationDetails.class);
                intent.putExtra("id", current.getVacationID());
                intent.putExtra("name", current.getVacationName());
                intent.putExtra("price", current.getPrice());
                context.startActivity(intent);
            }
        });
    }
}
    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.vacation_activity_item, parent, false);
        return new VacationViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if(mVacations != null) {
            Vacations current = mVacations.get(position);
            String name = current.getVacationName();
            holder.vacationItemView.setText(name);
        } else {
            holder.vacationItemView.setText("No vacation name");
        }
    }

    @Override
    public int getItemCount() {
        if(mVacations != null) {
            return mVacations.size();
        } else {
            return 0;
        }
    }

    public void setVacations(List<Vacations> vacations) {
        mVacations=vacations;
        notifyDataSetChanged();
    }

}
