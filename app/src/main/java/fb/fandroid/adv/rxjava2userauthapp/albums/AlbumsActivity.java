package fb.fandroid.adv.rxjava2userauthapp.albums;

import android.support.v4.app.Fragment;

import fb.fandroid.adv.rxjava2userauthapp.SingleFragmentActivity;


public class AlbumsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return AlbumsFragment.newInstance();
    }
}
