package ghettyinc.ghetty;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import ghettyinc.ghetty.Utils.Group;
import ghettyinc.ghetty.Utils.Invitation;

/**
 * Created by Tomás on 01/11/2015.
 */

public class MyGroupsAdapter extends BaseAdapter {

    Context context;
    List<Group> data;
    private static LayoutInflater inflater = null;

    public MyGroupsAdapter(Activity activity) {
        inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.my_groups_row, null);

        TextView text = (TextView) vi.findViewById(R.id.myGroupRowText);
        text.setText(data.get(position).getString("name"));

        RoundedImageView roundedImage = (RoundedImageView) vi.findViewById(R.id.groupImage);
        Group.getImage(data.get(position), roundedImage);

        return vi;
    }

    public void setData(List<Group> groups){
        data = groups;
    }
}
