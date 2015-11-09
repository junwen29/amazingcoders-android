package com.amazingcoders_android.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.models.Feedback;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yesha on 11/5/2015.
 */
public class ViewFeedbackCard extends CardView {
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.category)
    TextView mCategory;
    @InjectView(R.id.content)
    TextView mContent;

    Feedback mFeedback;
    Context mContext;

    public ViewFeedbackCard(Context context) {
        super(context);
        init(context);
    }

    public ViewFeedbackCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewFeedbackCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.view_feedback_card, this, true);
        ButterKnife.inject(this);
    }

    public void update(Feedback feedback){
        mFeedback = feedback;
        update();
    }

    private void update() {
        if (mFeedback == null) return;

        mTitle.setText(mFeedback.getTitle());
        mCategory.setText(mFeedback.getCategory());
        mContent.setText(String.valueOf(mFeedback.getContent()));
    }
}
