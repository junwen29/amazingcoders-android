package com.amazingcoders_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazingcoders_android.R;
import com.amazingcoders_android.adapters.base.ArrayAutoLoadAdapter;
import com.amazingcoders_android.models.Feedback;
import com.amazingcoders_android.views.ViewFeedbackCard;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yesha on 11/5/2015.
 */
public class FeedbackAdapter extends ArrayAutoLoadAdapter<Feedback> {

    public FeedbackAdapter(Context context, ArrayList<Feedback> items) {
        super(context, items);
    }

    public FeedbackAdapter(Context context, int numHeaders) {
        super(context, numHeaders);
    }

    @Override
    public void onBindRowViewHolder(RecyclerView.ViewHolder holder, int position) {
        Feedback fb = getItem(position);
        ((ViewHolder) holder).update(fb);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_feedback, viewGroup, false);

        return new ViewHolder(view);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.card_view_feedback)
        ViewFeedbackCard mViewFeedbackCard;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }

        public void update(Feedback feedback) {
            mViewFeedbackCard.update(feedback);
        }
    }
}
