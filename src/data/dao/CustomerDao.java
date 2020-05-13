package data.dao;

import common.ex.NoSuchCustomerException;
import common.ex.SystemMalfunctionException;
import data.ex.CouponAlreadyPurchaseException;
import data.ex.NoSuchCouponException;
import model.Coupon;
import model.Customer;

import java.sql.SQLException;
import java.util.Collection;

/**
 * This interface is used to define a Data Access Object for the customer data-source.
 */

public interface CustomerDao {

    void createCustomer(Customer customer) throws SystemMalfunctionException;

    void removeCustomer(long id) throws NoSuchCustomerException;

    void updateCustomer(Customer customer) throws SystemMalfunctionException, SQLException, NoSuchCustomerException;

    Customer getCustomer(long id) throws SQLException, SystemMalfunctionException;

    Collection<Customer> getAllCustomers() throws SystemMalfunctionException;

    Collection<Coupon> getCoupons(long customerId) throws SystemMalfunctionException, SQLException, NoSuchCustomerException;

    void insertCustomerCoupon(long couponId, long customerId) throws SystemMalfunctionException, NoSuchCouponException, NoSuchCustomerException, SQLException, CouponAlreadyPurchaseException;

    Customer login(String email, String password) throws SystemMalfunctionException;
}
