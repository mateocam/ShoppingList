package hu.ait.android.shoppinglist;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import hu.ait.android.shoppinglist.adapter.itemAdapter;
import hu.ait.android.shoppinglist.data.Item;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_NEW_ITEM = 101;
    public static final int REQUEST_EDIT_ITEM = 102;
    public static final String KEY_EDIT = "KEY_EDIT";
    private itemAdapter itemsAdapter;
    private LinearLayout layoutContent;
    private Item itemToEditHolder;
    private int itemToEditPosition = -1;
    private int totalPrice;
    private int priceBefore;
    private TextView etTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();

        final List<Item> itemsList = Item.listAll (Item.class);

        itemsAdapter = new itemAdapter(itemsList, this);
        RecyclerView recyclerViewItems = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItems.setAdapter(itemsAdapter);

        layoutContent = (LinearLayout) findViewById(R.id.layoutContent);

        etTotalPrice = (TextView) findViewById(R.id.totalPrice);

        Button btnDelete = (Button) findViewById(R.id.btnDeleteAll);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTotalPrice.setText("total price: 0");
                totalPrice = 0;
                itemsAdapter.removeAll();
            }
        });
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void showCreatePlaceActivity() {
        Intent intentStart = new Intent(MainActivity.this,
                CreateNewItem.class);
        startActivityForResult(intentStart, REQUEST_NEW_ITEM);
    }

    public void updateTotalPrice (int n) {
        totalPrice = totalPrice - n;
        etTotalPrice.setText("total price: " + totalPrice);
    }

    public void showEditPlaceActivity(Item itemToEdit, int position) {
        Intent intentStart = new Intent(MainActivity.this,
                CreateNewItem.class);

        itemToEditHolder = itemToEdit;
        itemToEditPosition = position;

        priceBefore = itemToEdit.getEstimated_price();

        intentStart.putExtra(KEY_EDIT, itemToEdit);
        startActivityForResult(intentStart, REQUEST_EDIT_ITEM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdd:
                showCreatePlaceActivity();
                return true;
            default:
                showCreatePlaceActivity();
                return true;
        }
    }

    private void showSnackBarMessage(String message) {
        Snackbar.make(layoutContent,
                message,
                Snackbar.LENGTH_LONG
        ).setAction(R.string.action_hide, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //...
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_NEW_ITEM) {
                    Item item = (Item) data.getSerializableExtra(
                            CreateNewItem.KEY_ITEM);

                    totalPrice = totalPrice + item.getEstimated_price();
                    etTotalPrice.setText("total price: " + totalPrice);

                    itemsAdapter.addItem(item);
                    showSnackBarMessage(getString(R.string.txt_item_added));
                } else if (requestCode == REQUEST_EDIT_ITEM) {
                    Item itemTemp = (Item) data.getSerializableExtra(
                            CreateNewItem.KEY_ITEM);

                    totalPrice = totalPrice + (itemTemp.getEstimated_price() - priceBefore);
                    etTotalPrice.setText("total price: " + totalPrice);

                    itemToEditHolder.setItemName(itemTemp.getItemName());
                    itemToEditHolder.setDescription(itemTemp.getDescription());
                    itemToEditHolder.setCategory(itemTemp.getCategory());
                    itemToEditHolder.setEstimated_price(itemTemp.getEstimated_price());
                    itemToEditHolder.setStatus(itemTemp.getStatus());

                    if (itemToEditPosition != -1) {
                        itemsAdapter.updateItem(itemToEditPosition, itemToEditHolder);
                        itemToEditPosition = -1;
                    }else {
                        itemsAdapter.notifyDataSetChanged();
                    }
                    showSnackBarMessage(getString(R.string.txt_item_edited));
                }
                break;
            case RESULT_CANCELED:
                showSnackBarMessage(getString(R.string.txt_add_cancel));
                break;
        }
    }
}
