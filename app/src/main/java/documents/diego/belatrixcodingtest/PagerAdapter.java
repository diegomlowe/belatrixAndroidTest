package documents.diego.belatrixcodingtest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numTabs;

    public PagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs=numTabs;
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment;

        if (i==0) fragment = new FragmentShaker();
        else fragment = new FragmentCompass();

        return fragment;
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
