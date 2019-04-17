package fb.fandroid.adv.rxjava2userauthapp;

import android.support.v4.app.Fragment;

/**
 * Created by tanchuev on 08.11.2017.
 */

public class AuthActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return AuthFragment.newInstance();
    }
}
