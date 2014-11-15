package com.trecstudios.pillbox;

import java.util.List;

import com.trecstudios.pillbox.logger.Log;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.ViewHolder> {
    private static final String TAG = "MedicationAdapter";

    private PillStore mDataSet;
    private RecyclerViewFragment frag;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom viewholder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mDrugName,  mTextView;
        private final Button mButton;
        
        public ViewHolder(View v) {
            super(v);
            mDrugName= (TextView) v.findViewById(R.id.drugName);
            mTextView = (TextView) v.findViewById(R.id.dosage);
            mButton = (Button) v.findViewById(R.id.button1);
        }
        public TextView getmDrugNameView() {
        	return mDrugName;
        }
        public TextView getmTextView() {
            return mTextView;
        }
        
        public Button getmButtomView() {
        	return mButton;
        }
        
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public MedicationAdapter(PillStore pillStore, RecyclerViewFragment frag) {
        mDataSet = pillStore;
        frag = frag;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getmDrugNameView().setText(mDataSet.getUntakenPills().get(position).getDisplayName());
        viewHolder.getmTextView().setText("Days Remaining: " + Integer.toString(mDataSet.getUntakenPills().get(position).getApproxDaysRemaining()));
        
        final int pos = position;
        
        viewHolder.getmButtomView().setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDataSet.markPillAsTaken(mDataSet.getUntakenPills().get(pos));
				 notifyDataSetChanged();
			}
		});
       // viewHolder.getmButtomView().
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.getUntakenPills().size();
    }

    public void updateList(){
    	 notifyDataSetChanged();
    }
}
