package edu.monash.student.happyactive.memoryReel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {
    private Map<Integer, List<ActivitySession>> mDataSet = new HashMap<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class SessionViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public SessionViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SessionAdapter(List<ActivitySession> myDataSet) {
        for(ActivitySession session: myDataSet) {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
            cal.setTime(session.completedDateTime);
            int month = cal.get(Calendar.MONTH);
            if(!mDataSet.containsKey(month)) {
                ArrayList<ActivitySession> sessionList = new ArrayList<>();
                sessionList.add(session);
                mDataSet.put(month, sessionList);
            } else {
                mDataSet.get(month).add(session);
            }
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SessionAdapter.SessionViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memory_reel_list_item, parent, false);
        SessionViewHolder vh = new SessionViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SessionViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView t = (TextView) holder.view.findViewById(R.id.memory_reel_item);
        t.setText( new DateFormatSymbols().getMonths()[(int) mDataSet.keySet().toArray()[position]]);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(holder.view).navigate(
                        MemoryReelFragmentDirections.showSelectedCollage(mDataSet.keySet().toArray()[position].toString())
                );
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
