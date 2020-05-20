package edu.monash.student.happyactive.memoryReel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * recyclerview for collage data, additionally shows an empty view if there is no data to display
 */
public class CollageRecyclerView extends RecyclerView {
    private View mEmptyView;

    public CollageRecyclerView(Context context) {
        super(context);
    }

    public CollageRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CollageRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void initEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(
                    getAdapter() == null || getAdapter().getItemCount() == 0 ? VISIBLE : GONE);
            CollageRecyclerView.this.setVisibility(
                    getAdapter() == null || getAdapter().getItemCount() == 0 ? GONE : VISIBLE);
        }
    }

    final AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            initEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            initEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            initEmptyView();
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        Adapter oldAdapter = getAdapter();
        super.setAdapter(adapter);

        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }

        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
    }

    public void setEmptyView(View view) {
        this.mEmptyView = view;
        initEmptyView();
    }
}