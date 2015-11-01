package ghettyinc.ghetty;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

import ghettyinc.ghetty.R;
import ghettyinc.ghetty.Utils.Group;
import ghettyinc.ghetty.Utils.Invitation;

/**
 * Created by Tomás on 30/09/2015.
 */
public class Chats extends SlidingMenu{

    private static LayoutInflater inflater = null;
    private ListView listView;
    private ChatAdapter adapter;

    public Chats(Context context) {
        super(context);
        setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        setMode(SlidingMenu.RIGHT);
        setShadowWidthRes(R.dimen.shadow_width);
        setShadowDrawable(R.drawable.shadow_right);
        setBehindOffsetRes(R.dimen.slidingmenu_offset);
        setMenu(R.layout.chat_list);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ChatAdapter(context);

        fillInfo();
    }

    public void fillInfo(){
        Invitation.getUserInvitations(new FindCallback<Invitation>() {
            @Override
            public void done(List<Invitation> objects, ParseException e) {

                Log.d("objects", objects.toString());

                if (objects.size() > 0) {
                    adapter.setData(objects);
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    class ChatAdapter extends BaseAdapter {

        Context context;
        List<Invitation> data;

        public ChatAdapter(Context context) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = convertView;
            if (vi == null)
                vi = inflater.inflate(R.layout.chat_list_row, null);
            TextView text = (TextView) vi.findViewById(R.id.text);
            text.setText((String) data.get(position).get("type"));
            return vi;
        }

        public void setData(List<Invitation> invitations){
            data = invitations;
        }
    }
}


