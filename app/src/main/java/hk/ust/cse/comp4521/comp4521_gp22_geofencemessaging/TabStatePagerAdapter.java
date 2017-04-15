package hk.ust.cse.comp4521.comp4521_gp22_geofencemessaging;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabStatePagerAdapter extends FragmentStatePagerAdapter {

    //set tab name
    private String[] titles = new String[]{"PIN", "Public", "New", "Private","Me"};

    public TabStatePagerAdapter(FragmentManager fm){
        super(fm);


    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                MapFragment pin =  new MapFragment();
                return  pin;
            case 1:
                PublicMessageFragment publicMessage = new PublicMessageFragment();
                return publicMessage;
            case 2:
                NewFragment newPost = new NewFragment();
                return newPost;
            case 3:
                PrivateFragment privateMessage = new PrivateFragment();
                return privateMessage;
            case 4:
                AccountFragment myAccount = new AccountFragment();
                return myAccount;
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
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
}
