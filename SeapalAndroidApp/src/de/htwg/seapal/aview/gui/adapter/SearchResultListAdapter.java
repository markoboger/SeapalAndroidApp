package de.htwg.seapal.aview.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.htwg.seapal.R;

/**
 * Created by robin on 08.01.14.
 */
public class SearchResultListAdapter extends BaseAdapter {

    Context context;
    String[] title;
    String[] text;
    private LayoutInflater inflater = null;

    public SearchResultListAdapter(Context context, String[] title, String[] text) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.title = title;
        this.text = text;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return title[position];
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
            vi = inflater.inflate(R.layout.search_list_item_layout, null);

        TextView titleText = (TextView) vi.findViewById(R.id.list_item_entry_title);
        TextView textText = (TextView) vi.findViewById(R.id.list_item_entry_summary);
        titleText.setText(title[position]);
        textText.setText(text[position]);

        return vi;
    }
}
