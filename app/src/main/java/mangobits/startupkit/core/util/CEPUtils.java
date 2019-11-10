package mangobits.startupkit.core.util;

import android.app.Activity;

/**
 * Created by angela on 5/8/17.
 */

public class CEPUtils {
    private Activity activity;
    private int[] ids;

    public CEPUtils(Activity activity, int... ids ){
        this.activity = activity;
        this.ids = ids;
    }

    public void lockFields( boolean isToLock ){
        for( int id : ids ){
            setLockField( id, isToLock );
        }
    }

    private void setLockField( int fieldId, boolean isToLock ){
        activity.findViewById( fieldId ).setEnabled( !isToLock );
    }

}
