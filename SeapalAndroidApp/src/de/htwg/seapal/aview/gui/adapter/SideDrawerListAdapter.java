package de.htwg.seapal.aview.gui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.client.cache.Resource;

import de.htwg.seapal.R;


/**
 * Created by robin on 10.01.14.
 */
public class SideDrawerListAdapter extends BaseAdapter {

    Context context;
    String[] title;
    Resources resource;
    DrawerSide side;
    int[] icons;
    private LayoutInflater inflater = null;
    public enum DrawerSide { LEFT, RIGHT;}

    public SideDrawerListAdapter(Context context, String[] title, int[] icons, Resources resource, DrawerSide side){

        this.context = context;
        this.title = title;
        this.resource = resource;
        this.side = side;
        this.icons = icons;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return title[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(side == DrawerSide.LEFT){

            if(view == null)
                view = inflater.inflate(R.layout.drawer_menu_list_item, null);

            TextView tv = (TextView) view.findViewById(R.id.drawer_list_item_left);
            ImageView iv = (ImageView) view.findViewById(R.id.drawer_list_item_icon);

            tv.setText(title[position]);
            iv.setImageDrawable(resource.getDrawable(icons[position]));

        }else{

            if(view == null)
                view = inflater.inflate(R.layout.drawer_list_item, null);

            TextView tv = (TextView) view.findViewById(R.id.drawer_list_item_textview);
            ImageView iv = (ImageView) view.findViewById(R.id.drawer_list_item_icon_right);

            iv.setImageDrawable(resource.getDrawable(icons[position]));
            tv.setText(title[position]);
        }

        return view;
    }
}
