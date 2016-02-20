package com.amberzile.magnusfernandes.platteratti;

public final class ItemContract {
    public ItemContract() {
    }
    public static abstract class ItemEntry{
        public static final String[] TABLE_NAME = {"BREAKFAST", "APPETIZERS", "SNACKS", "LUNCH", "DINNER", "DESSERTS", "BEVERAGES"};
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_TITLE = "TITLE";
        public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
        public static final String COLUMN_PRICE = "PRICE";
        public static final String COLUMN_IMAGE= "IMAGE";
    }
}
