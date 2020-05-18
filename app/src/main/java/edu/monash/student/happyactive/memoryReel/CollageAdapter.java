package edu.monash.student.happyactive.memoryReel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.entities.InteractivePrompt;
import edu.monash.student.happyactive.data.enumerations.PromptType;
import edu.monash.student.happyactive.data.relationships.ActivitySessionWithPhotosAndPrompts;

public class CollageAdapter extends RecyclerView.Adapter<CollageAdapter.CollageViewHolder>  {
    List<ActivitySessionWithPhotosAndPrompts> sessionWithPhotosList;
    private CollageView imageView;

    public static class CollageViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public CollageViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public CollageAdapter(List<ActivitySessionWithPhotosAndPrompts> myDataSet) {
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

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull CollageAdapter.CollageViewHolder holder, int position) {
        TextView titleView = (TextView) holder.view.findViewById(R.id.collage_activity);
        TextView dateView = (TextView) holder.view.findViewById(R.id.collage_date);
        imageView = (CollageView) holder.view.findViewById(R.id.collage_image);
        List<String> bitmaps = new ArrayList<>();
        imageView
                .photoMargin(1)
                .photoPadding(3)
                .useFirstAsHeader(true)
                .defaultPhotosForLine(3)
                .headerForm(CollageView.ImageForm.IMAGE_FORM_SQUARE)
                .photosForm(CollageView.ImageForm.IMAGE_FORM_HALF_HEIGHT);
        String pattern = " dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(sessionWithPhotosList.get(position).activitySession.completedDateTime);
        dateView.setText(date.toString());
        titleView.setText(sessionWithPhotosList.get(position).activityPackage.title);

        if(sessionWithPhotosList.get(position).sessionPhoto.size() > 0){
            String photoPath = holder.view.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) +"/" + sessionWithPhotosList.get(position).sessionPhoto.get(0).path;
            bitmaps.add(photoPath);
        }

        for(InteractivePrompt prompt: sessionWithPhotosList.get(position).interactivePrompts) {
            if(prompt.promptType == PromptType.PHOTO){
               bitmaps.add(prompt.answer);
            }
        }

        imageView.loadPhotos(bitmaps);

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
