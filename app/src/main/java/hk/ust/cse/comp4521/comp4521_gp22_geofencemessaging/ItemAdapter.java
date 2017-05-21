package hk.ust.cse.comp4521.comp4521_gp22_geofencemessaging;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<ItemData> itemDataList;

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTopic,mName,mContent;

        public ViewHolder(View view) {
            super(view);
            mTopic = (TextView) view.findViewById(R.id.item_topic);
            mName = (TextView) view.findViewById(R.id.item_user);
            mContent = (TextView) view.findViewById(R.id.item_content);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ItemAdapter(List<ItemData> itemDataList) {
        this.itemDataList = itemDataList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemData itemData = itemDataList.get(position);
        holder.mTopic.setText(itemData.getTopic());
        holder.mName.setText(itemData.getName());
        holder.mContent.setText(itemData.getContent());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemDataList.size();
    }
}
