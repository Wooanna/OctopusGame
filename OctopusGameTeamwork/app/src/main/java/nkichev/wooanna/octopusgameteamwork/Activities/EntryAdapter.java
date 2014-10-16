package nkichev.wooanna.octopusgameteamwork.Activities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nkichev.wooanna.octopusgameteamwork.HighscoresDB.Entry;
import nkichev.wooanna.octopusgameteamwork.R;

public class EntryAdapter extends ArrayAdapter<Entry> {
    private Context context;
    private int layoutId;
    private List<Entry> data;

    static class ViewHolder {
        public TextView tvName;
        public TextView tvScore;
    }

    public EntryAdapter(Context context, int textViewResourceId, List<Entry> objects){
        super(context, textViewResourceId, objects);
        this.context = context;
        this.layoutId = textViewResourceId;
        data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutId, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) row.findViewById(R.id.tvEntryName);
            viewHolder.tvScore = (TextView) row.findViewById(R.id.tvEntryScore);

            row.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) row.getTag();

        String name = data.get(position).getName();
        Long score = data.get(position).getScore();
        holder.tvName.setText(name);
        holder.tvScore.setText(score.toString());

        return row;
    }
}
