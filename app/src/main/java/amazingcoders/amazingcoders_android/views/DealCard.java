package amazingcoders.amazingcoders_android.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

/**
 * Created by junwen29 on 9/17/2015.
 */
public class DealCard extends CardView {
    public DealCard(Context context) {
        super(context);
        init();
    }

    public DealCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DealCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.widget_cell_view_venue_explore, this, true);

    }
}
