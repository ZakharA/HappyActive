package edu.monash.student.happyactive.memoryReel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.entities.InteractivePrompt;
import edu.monash.student.happyactive.data.enumerations.PromptType;
import edu.monash.student.happyactive.data.relationships.ActivitySessionWithPhotos;
import edu.monash.student.happyactive.data.relationships.SessionWithPhotoAndJournal;

public class ReelItemAdapter extends RecyclerView.Adapter<ReelItemAdapter.ReelItemViewHolder>  {

    private List<InteractivePrompt> mDataSet;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ReelItemViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public ReelItemViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ReelItemAdapter(List<InteractivePrompt> myDataSet) {
        mDataSet = myDataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ReelItemAdapter.ReelItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_reel_session_item, parent, false);
        ReelItemViewHolder vh = new ReelItemViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ReelItemViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if(mDataSet.get(position).promptType == PromptType.PHOTO) {
            ImageView imageView = (ImageView) holder.view.findViewById(R.id.reel_session_image);
            Bitmap bitmap = BitmapFactory.decodeFile(mDataSet.get(position).answer);
            imageView.setImageBitmap(bitmap);
        } else {
            TextView titleView = (TextView) holder.view.findViewById(R.id.reel_session_activity);
            titleView.setText(mDataSet.get(position).answer);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 1;
    }
}
