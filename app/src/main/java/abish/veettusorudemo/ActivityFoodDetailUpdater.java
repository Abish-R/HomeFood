package abish.veettusorudemo;

/**
 * Update values between activities
 * Created by Abish on 12/1/2017.
 */

public class ActivityFoodDetailUpdater {

    private static ActivityFoodDetailUpdater mInstance;
    private ActivityRefresh mListener;
    private boolean mState;

    public interface ActivityRefresh {
        void refreshData ();
    }

    public ActivityFoodDetailUpdater(){

    }

    public static ActivityFoodDetailUpdater getInstance() {
        if(mInstance == null) {
            mInstance = new ActivityFoodDetailUpdater();
        }
        return mInstance;
    }

    public void setListener(ActivityRefresh listener) {
        mListener = listener;
    }

    public void changeState(boolean state) {
        if(mListener != null) {
            mState = state;
            notifyStateChange();
        }
    }

    public boolean getState() {
        return mState;
    }

    private void notifyStateChange() {
        mListener.refreshData();
    }

}
