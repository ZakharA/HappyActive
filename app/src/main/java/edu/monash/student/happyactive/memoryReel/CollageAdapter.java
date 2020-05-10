package edu.monash.student.happyactive.memoryReel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.stfalcon.multiimageview.MultiImageView;

import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.relationships.ActivitySessionWithPhotos;

public class CollageAdapter extends RecyclerView.Adapter<CollageAdapter.CollageViewHolder>  {
    List<ActivitySessionWithPhotos> sessionWithPhotosList;

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
        TextView titleView = (TextView) holder.view.findViewById(R.id.collage_activity);
        TextView dateView = (TextView) holder.view.findViewById(R.id.collage_date);
        MultiImageView imageView = (MultiImageView) holder.view.findViewById(R.id.collage_image);
        String pattern = " dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(sessionWithPhotosList.get(position).activitySession.completedDateTime);
        dateView.setText(date.toString());

        if(sessionWithPhotosList.get(position).sessionPhoto.size() > 0){
            String photoPath = holder.view.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) +"/" + sessionWithPhotosList.get(position).sessionPhoto.get(0).path;
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            imageView.clear();
            imageView.addImage(bitmap);

        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(holder.view).navigate(
                        PhotoCollageFragmentDirections.showSessionMemoryReel(sessionWithPhotosList.get(position).activitySession.id)
                );
            }
        });

    }


    @Override
    public int getItemCount() {
        return sessionWithPhotosList.size();
    }
}
