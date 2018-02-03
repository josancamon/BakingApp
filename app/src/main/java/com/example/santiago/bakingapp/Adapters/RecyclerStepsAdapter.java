package com.example.santiago.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santiago.bakingapp.Model.Step;
import com.example.santiago.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santiago on 29/01/2018.
 */

public class RecyclerStepsAdapter extends RecyclerView.Adapter<RecyclerStepsAdapter.StepsViewHolder>{
    List<Step> stepsList = new ArrayList<>();
    private Context mContext;
    final private StepOnclickListener mStepOnclickListener;
    public interface StepOnclickListener{
        void onClick(Step stepClicked);
    }
    public RecyclerStepsAdapter(Context context,StepOnclickListener stepOnclickListener ){
        mContext = context;
        mStepOnclickListener = stepOnclickListener;
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView sTepTextView;
        public StepsViewHolder(View itemView) {
            super(itemView);
            sTepTextView = itemView.findViewById(R.id.step_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mStepOnclickListener.onClick(stepsList.get(getAdapterPosition()));
        }
    }
    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_steps,parent,false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        Step step = stepsList.get(position);
        holder.sTepTextView.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (stepsList == null) return 0;
        return stepsList.size();
    }
    public void setData(List<Step> stepsListReceived){
        stepsList = stepsListReceived;
        //Toast.makeText(mContext, stepsList.get(3).getShortDescription(), Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

}
