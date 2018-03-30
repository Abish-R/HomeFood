package abish.veettusorudemo.views;

import android.content.Intent;

import abish.veettusorudemo.network.model.FoodDetail;

/**
 * Created by Abish on 10/8/2017.
 * </p>
 */

public interface TransformIntent {

    void onLaunchActivity(FoodDetail foodDetail, int position, Intent intent, boolean needFinishActivity);

    void updateFavourite(String userID, final FoodDetail foodDetail);
}
