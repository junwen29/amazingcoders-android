package amazingcoders.amazingcoders_android.helpers.images;

import java.io.File;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.squareup.picasso.RequestCreator;

import amazingcoders.amazingcoders_android.R;

public class PicassoRequest {

    /**
     * Return {@link RequestCreator} for generic image request
     * on specified drawable resource ID with no placeholder.
     * <p>
     * <em>Note:</em> Target must be ImageView.
     */
    public static RequestCreator get(Context context, int resourceId) {
        return PicassoCompat.with(context)
                .load(resourceId);
    }

    /**
     * Return {@link RequestCreator} for generic image request
     * on specified file with no placeholder.
     * <p>
     * <em>Note:</em> Target must be ImageView.
     */
    public static RequestCreator get(Context context, File file) {
        return PicassoCompat.with(context)
                .load(file);
    }

    /**
     * Return {@link RequestCreator} for generic image request
     * on specified URI with no placeholder.
     * <p>
     * <em>Note:</em> Target must be ImageView.
     */
    public static RequestCreator get(Context context, Uri uri) {
        return PicassoCompat.with(context)
                .load(uri);
    }

    /**
     * Return {@link RequestCreator} for generic image request
     * on specified path with no placeholder.
     * <p>
     * <em>Note:</em> Target must be ImageView.
     */
    public static RequestCreator get(Context context, String path) {
        if (TextUtils.isEmpty(path)) path = null;
        return PicassoCompat.with(context)
                .load(path);
    }

    /**
     * Return {@link RequestCreator} for generic image request.
     * <p>
     * <em>Note:</em> Target must be ImageView.
     */
    public static RequestCreator get(Context context, String path, int placeholderResId) {
        return get(context, path).placeholder(placeholderResId);
    }

    /**
     * Return {@link RequestCreator} for food image.
     * <p>
     * <em>Note:</em> Target must be ImageView.
     */
    public static RequestCreator getFoodImage(Context context, String path) {
        return get(context, path, R.color.light_gray);
    }

    /**
     * Return {@link RequestCreator} for user image.
     * <p>
     * <em>Note:</em> Target must be ImageView.
     */
    public static RequestCreator getUserImage(Context context, String path) {
        return get(context, path, R.drawable.img_avatar_placeholder);
    }

    public static RequestCreator getUserImageCircular(Context context, String path) {
        return get(context, path, R.drawable.img_avatar_circular_placeholder);
    }

    /**
     * Return {@link RequestCreator} for venue image.
     * <p>
     * <em>Note:</em> Target must be ImageView.
     */
    public static RequestCreator getVenueImage(Context context, String path) {
        return get(context, path, R.drawable.img_venue_placeholder);
    }

    /**
     * Return {@link RequestCreator} for user cover image.
     * <p>
     * <em>Note:</em> Target must be ImageView.
     */
    public static RequestCreator getUserCoverImage(Context context, String path) {
        return get(context, path, R.drawable.img_cover_placeholder);
    }

    /**
     * Return {@link RequestCreator} for guide cover image.
     * <p>
     * <em>Note:</em> Target must be ImageView.
     */
    public static RequestCreator getGuideCoverImage(Context context, String path) {
        return get(context, path, R.drawable.img_guide_placeholder);
    }

}
