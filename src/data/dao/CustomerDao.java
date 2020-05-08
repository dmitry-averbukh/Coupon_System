package data.dao;

import common.ex.NoSuchCustumerException;
import common.ex.SystemMalfunctionException;
import data.ex.CouponAlreayPurcuaseExeption;
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

    void removeCustomer(long id) throws NoSuchCustumerException;

    void updateCustomer(Customer customer) throws SystemMalfunctionException, SQLException, NoSuchCustumerException;

    Customer getCustomer(long id) throws SQLException, SystemMalfunctionException;

    Collection<Customer> getAllCustomers() throws SystemMalfunctionException;

    Collection<Coupon> getCoupons(long customerId) throws SystemMalfunctionException, SQLException, NoSuchCustumerException;

    void insertCustomerCoupon(long couponId, long customerId) throws SystemMalfunctionException, NoSuchCouponException, NoSuchCustumerException, SQLException, CouponAlreayPurcuaseExeption;

    Customer login(String email, String password) throws SystemMalfunctionException;
}
