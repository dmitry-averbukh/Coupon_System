package facade;

import common.ex.NoSuchCustomerException;
import common.ex.SystemMalfunctionException;
import data.dao.CompanyDao;
import data.dao.CouponDao;
import data.dao.CustomerDao;
import data.db.CompanyDBDao;
import data.db.CouponDBDao;
import data.db.CustomerDBDao;
import data.ex.InvalidLoginException;
import data.ex.NoSuchCompanyException;
import model.Company;
import model.Customer;

import javax.naming.NameAlreadyBoundException;
import java.sql.SQLException;
import java.util.Collection;

public class AdminFacade extends AbsFacade {
    private final CouponDao couponDao;
    private final CompanyDao companyDao;
    private final CustomerDao customerDao;

    private AdminFacade() {
        couponDao = new CouponDBDao();
        companyDao = new CompanyDBDao();
        customerDao = new CustomerDBDao();
    }

    public static AbsFacade performLogin(String userName, String password) throws InvalidLoginException {
        if (userName.equals("admin") && password.equals("1234"))
            return new AdminFacade();
        throw new InvalidLoginException("Unable to login with provided credentials as admin.");
    }

    public void removeCompany(long companyId) throws SystemMalfunctionException, NoSuchCompanyException {
        couponDao.removeCompanyCoupons(companyId);
        companyDao.removeCompany(companyId);
    }

    public void createCompany(Company company) throws SystemMalfunctionException, NoSuchCompanyException, NameAlreadyBoundException {
        for (int i = companyDao.getAllCompanies().size() - 1; i >= 0; i--) {
            if (companyDao.getCompany(i).getName().equals(company.getName())) {
                throw new NameAlreadyBoundException("You already have company with that name");
            }
        }
        companyDao.createCompany(company);
    }

    public void updateCompany(Company company) throws SystemMalfunctionException, NoSuchCompanyException {
        if (companyDao.getCompany(company.getId()).getName().equals(company.getName())) {
            companyDao.updateCompany(company);
        }
    }

    public Collection<Company> getAllCompanies() throws SystemMalfunctionException {
        return companyDao.getAllCompanies();
    }

    public Company getCompany(long companyId) throws NoSuchCompanyException, SystemMalfunctionException {
        return companyDao.getCompany(companyId);
    }

    public void createCustomer(Customer customer) throws SystemMalfunctionException {
        customerDao.createCustomer(customer);
    }

    public void updateCustomer(Customer customer) throws NoSuchCustomerException, SQLException, SystemMalfunctionException {
        if (customerDao.getCustomer(customer.getId()).getFirstName().equals(customer.getFirstName()))
            customerDao.updateCustomer(customer);
    }

    public void removeCustomer(long customerId) throws NoSuchCustomerException {
        customerDao.removeCustomer(customerId);
    }

    public Collection<Customer> getAllCustomers() throws SystemMalfunctionException {
        return customerDao.getAllCustomers();
    }

    public Customer getCustomer(long customerId) throws SQLException, SystemMalfunctionException {
        return customerDao.getCustomer(customerId);
    }


}
