package ghettyinc.ghetty;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ghettyinc.ghetty.Utils.Group;

/**
 * Created by Tomás on 01/11/2015.
 */
public class CardPagerAdapter extends PagerAdapter{

    private LayoutInflater inflater;
    private List<Group> data;

    public CardPagerAdapter(Activity activity) {
        inflater = activity.getLayoutInflater();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Group group = data.get(position);

        View view = inflater.inflate(R.layout.card, null);

        TextView groupNameTextView = (TextView) view.findViewById(R.id.groupNameTextView);
        groupNameTextView.setText(group.getString("name"));

        TextView groupDescriptionTextView = (TextView) view.findViewById(R.id.groupDescriptionTextView);
        groupDescriptionTextView.setText(group.getString("desc"));

        TextView peopleCount = (TextView) view.findViewById(R.id.peopleCount);
        peopleCount.setText(String.valueOf(group.getInt("peopleCount")));

        ImageView house = (ImageView) view.findViewById(R.id.house);

        if(group.getBoolean("house")){

        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    public void setData(List<Group> groups) {
        data = groups;
    }
}
