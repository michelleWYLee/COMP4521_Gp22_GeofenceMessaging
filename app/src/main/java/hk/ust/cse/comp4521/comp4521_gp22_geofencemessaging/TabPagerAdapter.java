package hk.ust.cse.comp4521.comp4521_gp22_geofencemessaging;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TabPagerAdapter extends FragmentPagerAdapter {

    //set tab name
    private static String[] titles = new String[]{"PIN", "Public", "New", "Private","Me"};
    Context context;
    //pass user permission choice from Activity down to Fragment
    public static Fragment[] fragments = new Fragment[titles.length];

    public TabPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new MapFragment();

            case 1:
                return new PublicMessageFragment();

            case 2:
                return new NewFragment();

            case 3:
                return new PrivateFragment();

            case 4:
                return new AccountFragment();

        }
        return null;
    }


    @Override
    public int getCount() {
        return titles.length;
    }

    @Override //set icon of each tab
    public CharSequence getPageTitle(int position) {

        /*Drawable icon = context.getResources().getDrawable(R.drawable.pic);
        //set icon size
        // icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        icon.setBounds(0, 0, 50, 50);
        ImageSpan iconSpan = new ImageSpan(icon, ImageSpan.ALIGN_BOTTOM);

        //if any string
        SpannableString ss = new SpannableString("  " + "Tab  " + position);
        ss.setSpan(iconSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        */


        return titles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        fragments[position]  = createdFragment;
        return createdFragment;
    }

}
