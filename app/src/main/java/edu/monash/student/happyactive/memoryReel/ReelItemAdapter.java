package edu.monash.student.happyactive.memoryReel;

        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;


        import com.bumptech.glide.Glide;
        import com.bumptech.glide.RequestManager;

        import java.io.File;
        import java.util.List;

        import edu.monash.student.happyactive.R;
        import edu.monash.student.happyactive.data.entities.InteractivePrompt;
        import edu.monash.student.happyactive.data.enumerations.PromptType;

public class ReelItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

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

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position).promptType.ordinal();
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ReelItemAdapter(List<InteractivePrompt> myDataSet) {
        mDataSet = myDataSet;
    }


    class PhotoViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        private TextView textView;
        private RequestManager glide;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.reel_session_image);
            textView = itemView.findViewById(R.id.reel_session_activity);
            glide = Glide.with(itemView.getContext());
        }

        public void setPhotoDetails(InteractivePrompt interactivePrompt) {
            glide.load(new File(interactivePrompt.answer)).into(imageView);
        }
    }

    class TextViewHolder extends RecyclerView.ViewHolder {
        private TextView promptTextView;
        private TextView textView;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            promptTextView = itemView.findViewById(R.id.reel_session_activity);
            textView = itemView.findViewById(R.id.reel_session_date);
        }

        public void setTextDetails(InteractivePrompt interactivePrompt) {
            promptTextView.setText(interactivePrompt.answer);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == PromptType.PHOTO.ordinal()) { // for photo layout
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_reel_session_item_photo, viewGroup, false);
            return new PhotoViewHolder(view);

        } else { // for text layout
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_reel_session_item_text, viewGroup, false);
            return new TextViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) ==  PromptType.PHOTO.ordinal()) {
            ((PhotoViewHolder) holder).setPhotoDetails(mDataSet.get(position));
        } else {
            ((TextViewHolder) holder).setTextDetails(mDataSet.get(position));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addData(List<InteractivePrompt> myDataSet) {
        mDataSet = myDataSet;
    }
}

