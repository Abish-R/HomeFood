package abish.veettusorudemo.views;

import android.content.Intent;

import abish.veettusorudemo.network.response.FoodDetail;

/**
 * Created by Abish on 10/8/2017.
 * </p>
 */

public interface TransformIntent {

    void onLaunchActivity(FoodDetail foodDetail, Intent intent, boolean needFinishActivity);
}
