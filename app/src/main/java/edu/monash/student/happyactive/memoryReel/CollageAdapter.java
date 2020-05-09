package edu.monash.student.happyactive.memoryReel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.relationships.ActivitySessionWithPhotos;

public class CollageAdapter extends RecyclerView.Adapter<CollageAdapter.CollageViewHolder>  {
    List<ActivitySessionWithPhotos> sessionWithPhotosList = new ArrayList<>();

    public static class CollageViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public CollageViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public CollageAdapter(List<ActivitySessionWithPhotos> myDataSet) {
        sessionWithPhotosList = myDataSet;
    }

    @NonNull
    @Override
    public CollageAdapter.CollageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_memory_reel_collage, parent, false);
        CollageAdapter.CollageViewHolder vh = new CollageAdapter.CollageViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CollageAdapter.CollageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return sessionWithPhotosList.size();
    }
}
