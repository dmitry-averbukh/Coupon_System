package data.dao;

import common.ex.SystemMalfunctionException;
import data.ex.InvalidLoginException;
import data.ex.NoSuchCompanyException;
import model.Company;
import model.Coupon;

import java.util.Collection;
import java.util.Set;

/**
 * This interface is used to define a Data Access Object for the company data-source.
 */

public interface CompanyDao {
    void createCompany(Company company) throws SystemMalfunctionException;

    void removeCompany(long id) throws NoSuchCompanyException, SystemMalfunctionException;

    void updateCompany(Company company) throws SystemMalfunctionException, NoSuchCompanyException;

    Company getCompany(long id) throws SystemMalfunctionException, NoSuchCompanyException;

    Collection<Company> getAllCompanies() throws SystemMalfunctionException;

    Set<Coupon> getCoupons(long id) throws SystemMalfunctionException;

    Company login(String email, String password) throws SystemMalfunctionException, InvalidLoginException;
}
