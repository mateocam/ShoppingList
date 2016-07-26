package hu.ait.android.shoppinglist.data;

import com.orm.SugarRecord;

import java.io.Serializable;

import hu.ait.android.shoppinglist.R;

/**
 * Created by mateocar on 11/8/15.
 */
public class Item extends SugarRecord<Item> implements Serializable {

    public enum CategoryType {
        FOOD(0, R.drawable.ic_restaurant_menu_black_24dp),
        HOUSE_ITEM(1, R.drawable.ic_home_black_24dp),
        ELECTRONIC(2, R.drawable.ic_cast_connected_black_24dp);

        private int value;
        private int iconId;

        private CategoryType(int value, int iconId) {
            this.value = value;
            this.iconId = iconId;
        }

        public int getValue() {
            return value;
        }

        public int getIconId() {
            return iconId;
        }

        public static CategoryType fromInt(int value) {
            for (CategoryType p : CategoryType.values()) {
                if (p.value == value) {
                    return p;
                }
            }
            return FOOD;
        }
    }

    private String itemName;
    private String description;
    private CategoryType category;
    private Integer estimated_price;
    private boolean status;

    public Item() {

    }

    public Item(String placeName, String description, CategoryType category, Integer price,
                boolean status) {
        this.itemName = placeName;
        this.description = description;
        this.category = category;
        this.estimated_price = price;
        this.status = status;
    }

    public String getItemName() { return itemName; }

    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public CategoryType getCategory() { return category; }

    public void setCategory(CategoryType category) { this.category = category; }

    public Integer getEstimated_price() { return estimated_price; }

    public void setEstimated_price(int estimated_price) { this.estimated_price = estimated_price; }

    public boolean getStatus() { return status; }

    public void setStatus(boolean status) { this.status = status; }
}
