package amazingcoders.amazingcoders_android.adapters.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * Created by tsangjunwen on 21/4/15.
 *
 */
public abstract class ArrayAutoLoadAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public interface AutoLoadListener {
        void onLoad();
    }

    protected static final int TYPE_LOADING_VIEW = 3;
    protected static final int TYPE_HEADER = 2;
    public static final int TYPE_ITEM = 1;

    private boolean mAutoLoadEnabled;
    private AutoLoadListener mAutoLoadListener;

    protected Context mContext;
    protected ArrayList<T> mItems;
    protected int numHeaders = 0;
    protected int numFooters = 0;

    protected View mFooter;

    public ArrayAutoLoadAdapter(Context context, ArrayList<T> items) {
        mContext = context;
        mItems = items;
    }

    public ArrayAutoLoadAdapter(Context context, int numHeaders) {
        this(context, new ArrayList<T>());
        this.numHeaders = numHeaders;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1 && mAutoLoadEnabled && mAutoLoadListener != null) {
            mAutoLoadEnabled = false;
            mAutoLoadListener.onLoad();
        }
        onBindRowViewHolder(holder, position);
    }

    public abstract void onBindRowViewHolder(RecyclerView.ViewHolder holder, int position);

    public final Context getContext() {
        return mContext;
    }

    @Override
    public int getItemCount() {
        return getArrayItemCount() + numHeaders + numFooters;
    }

    // old getItemCount()
    public int getArrayItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        else if (isPositionFooter(position)){
            return TYPE_LOADING_VIEW;
        }
        else
            return TYPE_ITEM;
    }

    public T getItem(int position) {
        return mItems.get(position); // no need to worry about headers or footers as they are not in the arraylist
    }

    public List<T> getAllItems() {
        return mItems;
    }

    public void add(T item) {
        mItems.add(item);
    }

    public void add(int index, T item) {
        mItems.add(index, item);
    }

    public void addAll(Collection<? extends T> items) {
        mItems.addAll(items);
    }

    public void addAll(int index, Collection<? extends T> items) {
        mItems.addAll(index, items);
    }

    public void set(int index, T item) {
        mItems.set(index, item);
    }

    public void clear() {
        mItems.clear();
    }

    public void remove(T item) {
        mItems.remove(item);
    }

//    public void removeAll(Collection<? extends T> items) {
//        mItems.removeAll(items);
//    }

//    public boolean isAutoLoadEnabled() {
//        return mAutoLoadEnabled;
//    }

    public void setAutoLoadEnabled(boolean enabled) {
        mAutoLoadEnabled = enabled;
    }

    public void setAutoLoadListener(AutoLoadListener listener) {
        mAutoLoadListener = listener;
    }

    //added a method to check if given position is a header
    public boolean isPositionHeader(int position) {
        return position < numHeaders;
    }

    public boolean isPositionFooter(int position){
        return position == getItemCount() -1;
    }

    public int getNumHeaders() {
        return numHeaders;
    }
}
