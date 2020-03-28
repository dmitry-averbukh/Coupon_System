package db;

public class Schema {
    /*Table names*/
    private static final String TABLE_NAME_COMPANY = "company";
    private static final String TABLE_NAME_COUPON = "coupon";
    /*Common column names*/
    private static final String COL_NAME = "name";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";
    /*Coupon specific columns*/
    private static final String COL_COMPANY_ID = "company_id";
    private static final String COL_CATEGORY = "category";
    private static final String COL_TITLE = "title";
    private static final String COL_START_DATE = "start_date";
    private static final String COL_END_DATE = "end_date";
    private static final String COL_AMOUNT = "amount";
    private static final String COL_DESC = "description";
    private static final String COL_PRICE = "price";
    private static final String COL_IMAGE = "image";

    private static final String TABLE_NAME_COUSTOMER = "customer";
    private static final String COL_FN = "first_name";
    private static final String COL_LN ="last_name";

    /*Queries*/
    public static final String INSERT_COMPANY = "insert into " + TABLE_NAME_COMPANY
            + " ("
            + COL_NAME + ","
            + COL_EMAIL + ","
            + COL_PASSWORD
            + ") values"
            + " (?,?,?)";

    public static final String INSERT_COUPON = "insert into " + TABLE_NAME_COUPON
            + " ("
            + COL_COMPANY_ID + ","
            + COL_CATEGORY + ","
            + COL_TITLE + ","
            + COL_START_DATE + ","
            + COL_END_DATE + ","
            + COL_AMOUNT + ","
            + COL_DESC + ","
            + COL_PRICE + ","
            + COL_IMAGE
            + ") values"
            + " (?,?,?,?,?,?,?,?,?)";



    public static final String INSERT_COUSTOMER = "insert into " + TABLE_NAME_COUSTOMER
            + " ("
            + COL_FN + ","
            + COL_LN + ","
            + COL_EMAIL + ","
            + COL_PASSWORD
            + ") values"
            + " (?,?,?,?)";


}
