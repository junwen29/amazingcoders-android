package com.amazingcoders_android.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.amazingcoders_android.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by junwen29 on 10/28/2015.
 */
public class FeedbackCard extends CardView {
    @InjectView(R.id.spinner)
    MaterialSpinner mSpinner;
    @InjectView(R.id.title)
    EditText mTitle;
    @InjectView(R.id.desc)
    EditText mDetails;

    private static final String[] ITEMS = {"Suggestion", "Complaint", "Issue"};
    private Context mContext;
    private String mCategory = "";

    public FeedbackCard(Context context) {
        super(context);
        init(context);
    }

    public FeedbackCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.feedback_card, this, true);
        ButterKnife.inject(this);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0)
                    mCategory = adapter.getItem(position);
                else {
                    mCategory = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCategory = "";
            }
        });
    }

    public MaterialSpinner getSpinner() {
        return mSpinner;
    }

    public String getCategory() {
        return mCategory;
    }

    public EditText getTitle() {
        return mTitle;
    }

    public EditText getDetails() {
        return mDetails;
    }
}
