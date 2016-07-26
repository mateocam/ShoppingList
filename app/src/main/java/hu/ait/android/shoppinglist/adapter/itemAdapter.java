package hu.ait.android.shoppinglist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hu.ait.android.shoppinglist.MainActivity;
import hu.ait.android.shoppinglist.R;
import hu.ait.android.shoppinglist.data.Item;

/**
 * Created by mateocar on 11/9/15.
 */
public class itemAdapter extends RecyclerView.Adapter<itemAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemIcon;
        public TextView tvName;
        public TextView tvPrice;
        public Button btnDelete;
        public Button btnEdit;
        public CheckBox cbDone;

        public ViewHolder(View itemView) {
            super(itemView);
            itemIcon = (ImageView) itemView.findViewById(R.id.itemIcon);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            cbDone = (CheckBox) itemView.findViewById(R.id.cbDone);
        }
    }

    private List<Item> itemsList;
    private Context context;

    public itemAdapter(List<Item> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.tvName.setText(itemsList.get(position).getItemName());
        viewHolder.tvPrice.setText(itemsList.get(position).getEstimated_price().toString());
        viewHolder.itemIcon.setImageResource(itemsList.get(position).getCategory().getIconId());

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).updateTotalPrice
                        (itemsList.get(position).getEstimated_price());
                removeItem(position);
            }
        });

        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).showEditPlaceActivity(itemsList.get(position), position);
            }
        });

        viewHolder.cbDone.setChecked(itemsList.get(position).getStatus());
        viewHolder.cbDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsList.get(position).setStatus(viewHolder.cbDone.isChecked());
                itemsList.get(position).save();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void addItem(Item item) {
        item.save();
        itemsList.add(item);
        notifyDataSetChanged();
    }

    public void updateItem(int index, Item item) {
        itemsList.set(index, item);
        item.save();
        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        // remove it from the DB
        itemsList.get(index).delete();
        // remove it from the list
        itemsList.remove(index);
        notifyDataSetChanged();
    }

    public void removeAll() {
        // remove it from the DB
        Item.deleteAll(Item.class);
        // remove it from the list
        itemsList.clear();
        notifyDataSetChanged();
    }

    public Item getItem(int i) {
        return itemsList.get(i);
    }
}
