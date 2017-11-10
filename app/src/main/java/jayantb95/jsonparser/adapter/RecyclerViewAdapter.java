package jayantb95.jsonparser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jayantb95.jsonparser.R;
import jayantb95.jsonparser.dataModel.FeedItem;

/**
 * Created by Jayant on 2017-10-29.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {
    private List<FeedItem> feedItemList;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, null);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        FeedItem feedItem = feedItemList.get(i);


        //Setting text view title
        customViewHolder.txtTitle.setText(Html.fromHtml(feedItem.getTitle()));
        customViewHolder.txtDescription.setText(Html.fromHtml(feedItem.getDescription()));
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtDescription;

        CustomViewHolder(View view) {
            super(view);
            this.txtTitle = (TextView) view.findViewById(R.id.txt_title);
            this.txtDescription = (TextView) view.findViewById(R.id.txt_description);
        }
    }
}