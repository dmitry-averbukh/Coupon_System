package facade;

import common.ex.SystemMalfunctionException;
import data.dao.CompanyDao;
import data.dao.CouponDao;
import data.dao.CustomerDao;
import data.db.CompanyDBDao;
import data.db.CouponDBDao;
import data.db.CustomerDBDao;
import data.ex.InvalidLoginException;
import data.ex.NoSuchCompanyException;
import data.ex.NoSuchCouponException;
import model.Company;
import model.Coupon;
import model.Customer;

import java.util.Collection;
import java.util.Set;

public class AdminFacade extends AbsFacade {
    private CouponDao couponDao;
    private CompanyDao companyDao;
    private CustomerDao customerDao;

    private AdminFacade() {
        couponDao = new CouponDBDao();
        companyDao = new CompanyDBDao();
        customerDao = new CustomerDBDao();
    }

    public static AbsFacade performLogin(String userName, String password) throws InvalidLoginException {
        // TODO: 05-May-20 How can this if statement be improved?
        if (userName.equals("admin") && password.equals("1234"))
            return new AdminFacade();
        throw new InvalidLoginException("Unable to login with provided credentials as admin.");
    }

//    public void removeCompany(long companyId) throws SystemMalfunctionException, NoSuchCompanyException {
//        Set<Coupon> coupons = couponDao.getCoupons(companyId);
//
//        for (Coupon coupon : coupons) {
//            try {
//                couponDao.removeCoupon(coupon.getId());
//            } catch (NoSuchCouponException e) {
//                // Ignore since we know for sure that all the coupons belong to the specified company
//            }
//        }
//        companyDao.removeCompany(companyId);
//    }

    public void createCompany(Company company) {
        // TODO: 05-May-20 Implement.
        // You can not create a company with a name that is already exists.
    }

    public void updateCompany(Company company) {
        // TODO: 05-May-20 Implement.
        // You can not change the name of a company.
    }

    public Collection<Company> getAllCompanies() {
        // TODO: 05-May-20 Implement.
        return null;
    }

    public Company getCompany(long companyId) {
        // TODO: 05-May-20 Implement.
        return null;
    }

    public void createCustomer(Customer customer) {
        // TODO: 05-May-20 Implement.
        // You can not create a customer with an email that is already exists.
    }

    public void updateCustomer(Customer customer) {
        // TODO: 05-May-20 Implement.
        // You can not change the name of a customer.
    }

    public void removeCustomer(long customerId) {
        // TODO: 05-May-20 Implement.
    }

    public Collection<Customer> getAllCustomers() {
        // TODO: 05-May-20 Implement.
        return null;
    }

    public Customer getCustomer(long customerId) {
        // TODO: 05-May-20 Implement.
        return null;
    }


}
