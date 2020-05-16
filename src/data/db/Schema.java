package data.db;

public class Schema {
    private static final String TABLE_NAME_COMPANY = "company";
    private static final String TABLE_NAME_COUPON = "coupon";
    private static final String TABLE_NAME_CUSTOMER = "customer";
    private static final String COL_COMPANY_ID = "company_id";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";
    private static final String COL_FIRST_NAME = "first_name";
    private static final String COL_LAST_NAME = "last_name";
    private static final String COL_CATEGORY = "category";
    private static final String COL_TITLE = "title";
    private static final String COL_START_DATE = "start_date";
    private static final String COL_END_DATE = "end_date";
    private static final String COL_AMOUNT = "amount";
    private static final String COL_DESC = "description";
    private static final String COL_PRICE = "price";
    private static final String COL_IMAGE = "image";

    /**
    SQL commands working with "company" table;
     */

    public static final String INSERT_COMPANY = "insert into " + TABLE_NAME_COMPANY
            + "("
            + COL_NAME + ","
            + COL_EMAIL + ","
            + COL_PASSWORD
            + ")" +
            " values "
            + "(?,?,?)";
    public static final String UPDATE_COMPANY = "update " + TABLE_NAME_COMPANY +
            " set "
            + COL_NAME + "=?, "
            + COL_EMAIL + "=?, "
            + COL_PASSWORD + "=? "
            + "where "
            + COL_ID + "=?";
    public static final String SELECT_COMPANY_BY_EMAIL_AND_PASSWORD = "select * from " + TABLE_NAME_COMPANY
            + " where " + COL_EMAIL + "=?" +
            " and " +
            COL_PASSWORD + "=?";
    public static final String DELETE_COMPANY = "delete from " + TABLE_NAME_COMPANY
            + " where " + COL_ID + " = ?";
    public static final String SELECT_COMPANY_BY_ID = "select * from " + TABLE_NAME_COMPANY
            + " where " + COL_ID + "=?";
    public static final String SELECT_ID_COMPANIES = "select " + COL_ID + " from " + TABLE_NAME_COMPANY;

    /**
     SQL commands working with "coustomer" table;
     */

    public static final String INSERT_CUSTOMER = "insert into " + TABLE_NAME_CUSTOMER
            + "("
            + COL_FIRST_NAME + ","
            + COL_LAST_NAME + ","
            + COL_EMAIL + ","
            + COL_PASSWORD
            + ")" +
            " values "
            + "(?,?,?,?)";
    public static final String REMOVE_CUSTOMER = "delete from customer where id= ?";
    public static final String UPDATE_COUSTOMER = "UPDATE customer SET" +
            " first_name=?" +
            ",last_name=?" +
            ",email=?" +
            ",password=? " +
            "WHERE id= ?";
    public static final String CHECK_CUSTOMER = "SELECT password,id FROM customer where email = '%s'";
    public static final String GET_CUSTOMER = "SELECT * FROM coupon_system.customer where id =";
    public static final String GET_ALL_CUSTOMERS = "SELECT * FROM coupon_system.customer";

    /**
     SQL commands working with "coupon" table;
     */

    public static final String GET_COUPON = "SELECT * FROM coupon_system.coupon where id = ";
    public static final String SELECT_COUPONS_BY_COMPANY_ID = "select * from " + TABLE_NAME_COUPON
            + " where " + COL_COMPANY_ID + "=?";
    public static final String REMOVE_COUPON = "delete from coupon where id = ?";
    public static final String REMOVE_COMPANY_COUPONS = "delete from coupon where company_id = ?";
    public static final String GET_ALL_COUPONS = "SELECT * FROM coupon_system.coupon";
    public static final String GET_ALL_COUPONS_CATEG = "SELECT * FROM coupon_system.coupon WHERE category = ";
    public static final String INSERT_COUPON = "insert into " + TABLE_NAME_COUPON
            + "("
            + COL_COMPANY_ID + ","
            + COL_CATEGORY + ","
            + COL_TITLE + ","
            + COL_START_DATE + ","
            + COL_END_DATE + ","
            + COL_AMOUNT + ","
            + COL_DESC + ","
            + COL_PRICE + ","
            + COL_IMAGE
            + ")" +
            " values "
            + "(?,?,?,?,?,?,?,?,?)";
    public static final String UPDATE_COUPON = "UPDATE COUPON SET" +
            " company_id=?" +
            ",category=?" +
            ",title=?" +
            ",start_date=? " +
            ",end_date=? " +
            ",amount=? " +
            ",description=? " +
            ",price=? " +
            ",image=? " +
            "WHERE id= ?";
    public static final String DECREMENT_COUPON = "UPDATE COUPON SET" +
            " amount = amount - 1" +
            " where id = ?" +
            " and amount > 0";
    /**
     SQL commands working with "coustomer_coupons" table;
     */

    public static final String GET_CUSTOMER_COUPONS = "SELECT * FROM coupon_system.customer_coupon where customer_id = ";
    public static final String SET_CUSTOMER_COUPON = "insert into customer_coupon (coupon_id,customer_id) values (%d,%d)";
}
