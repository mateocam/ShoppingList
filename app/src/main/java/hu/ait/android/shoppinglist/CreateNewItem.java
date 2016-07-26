package hu.ait.android.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

import hu.ait.android.shoppinglist.data.Item;

public class CreateNewItem extends AppCompatActivity {

    public static final String KEY_ITEM = "KEY_ITEM";
    private Spinner spinnerItemCategory;
    private EditText etItem;
    private EditText etPrice;
    private EditText etItemDesc;
    private CheckBox cbDone;
    private Button btnSave;
    private int priceBefore;
    private Item itemToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_item);

        if (getIntent().getSerializableExtra(MainActivity.KEY_EDIT) != null) {
            itemToEdit = (Item) getIntent().getSerializableExtra(MainActivity.KEY_EDIT);
        }

        spinnerItemCategory = (Spinner) findViewById(R.id.spinnerItemType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.itemtypes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItemCategory.setAdapter(adapter);

        etItem = (EditText) findViewById(R.id.etItemName);
        etItemDesc = (EditText) findViewById(R.id.etItemDesc);
        etPrice = (EditText) findViewById(R.id.etItemPrice);
        cbDone = (CheckBox) findViewById(R.id.cbDone2);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });

        if (itemToEdit != null) {
            etItem.setText(itemToEdit.getItemName());
            etItemDesc.setText(itemToEdit.getDescription());
            etPrice.setText(itemToEdit.getEstimated_price().toString());
            spinnerItemCategory.setSelection(itemToEdit.getCategory().getValue());
            cbDone.setChecked(itemToEdit.getStatus());
            //priceBefore = itemToEdit.getEstimated_price();
        }
    }

    private void saveItem() {
        Intent intentResult = new Intent();
        Item itemResult;
        if (itemToEdit != null) {
            itemResult = itemToEdit;
        } else {
            itemResult = new Item();
        }

        //itemResult.setEditedPrice(Integer.parseInt(etPrice.getText().toString()) - priceBefore);

        if (etItem.getText() == null && etPrice.getText() == null) {
            Toast.makeText(CreateNewItem.this, R.string.create_warning,
                    Toast.LENGTH_SHORT).show();
        }
        itemResult.setItemName(etItem.getText().toString());
        itemResult.setEstimated_price(Integer.parseInt(etPrice.getText().toString()));
        itemResult.setDescription(etItemDesc.getText().toString());
        itemResult.setCategory(
                Item.CategoryType.fromInt(spinnerItemCategory.getSelectedItemPosition()));
        itemResult.setStatus(cbDone.isChecked());

        intentResult.putExtra(KEY_ITEM, itemResult);
        setResult(RESULT_OK, intentResult);
        finish();
    }
}
