package data.db;

import common.ConnectionPool;
import common.StatementUtils;
import common.ex.SystemMalfunctionException;
import data.dao.CompanyDao;
import data.ex.InvalidLoginException;
import data.ex.NoSuchCompanyException;
import model.Company;
import model.Coupon;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CompanyDBDao implements CompanyDao {

    private static void applyCompanyValuesOnStatement(PreparedStatement ps
                                   ,Company company) throws SQLException {

        ps.setString(1, company.getName());
        ps.setString(2, company.getEmail());
        ps.setString(3, company.getPassword());
    }

    @Override
    public void createCompany(Company company) throws SystemMalfunctionException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Schema.INSERT_COMPANY);
            applyCompanyValuesOnStatement(ps, company);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SystemMalfunctionException("There was a problem creating company: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
            StatementUtils.close(ps);
        }
    }


    @Override
    public void removeCompany(long id) throws NoSuchCompanyException, SystemMalfunctionException {
        if (id <= 0) {
            throw new NoSuchCompanyException("Unable to remove company with id: " + id);
        }
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Schema.DELETE_COMPANY);
            ps.setLong(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new NoSuchCompanyException("Unable to remove company with id: " + id);
            }
        } catch (SQLException e) {
            throw new SystemMalfunctionException("There was a problem removing a company: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
            StatementUtils.close(ps);
        }
    }

    @Override
    public void updateCompany(Company company) throws SystemMalfunctionException, NoSuchCompanyException {
        long id = company.getId();

        if (id <= 0) {
            throw new NoSuchCompanyException("Unable to update company with id: " + id);
        }

        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Schema.UPDATE_COMPANY);
            applyCompanyValuesOnStatement(ps, company);
            ps.setLong(4, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new NoSuchCompanyException("Unable to update company with id: " + id);
            }
        } catch (SQLException e) {
            throw new SystemMalfunctionException("There was a problem updating a company: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
            StatementUtils.close(ps);
        }
    }

    @Override
    public Set<Coupon> getCoupons(long id) throws SystemMalfunctionException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        Set<Coupon> coupons;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Schema.SELECT_COUPONS_BY_COMPANY_ID);
            ps.setLong(1, id);
            coupons = new HashSet<>();
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                coupons.add(resultSetToCoupon(rs));
            }
        } catch (SQLException e) {
            throw new SystemMalfunctionException("Unable to get company's coupons:" + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
            StatementUtils.close(ps);
        }
        return coupons;
    }

    @Override
    public Company getCompany(long id) throws SystemMalfunctionException, NoSuchCompanyException {

        if (id <= 0) {
            throw new NoSuchCompanyException("Unable to get company with id: " + id);
        }

        Connection connection = ConnectionPool.getInstance().getConnection();
        Company company;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Schema.SELECT_COMPANY_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.first()) {
                company = resultSetToCompany(rs);
                company.setCoupons(getCoupons(id));
            } else {
                throw new NoSuchCompanyException("Unable to get company with id: " + id);
            }

        } catch (SQLException e) {
            throw new SystemMalfunctionException("Unable to get company: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
            StatementUtils.close(ps);
        }

        return company;
    }

    private static Company resultSetToCompany(ResultSet rs) throws SQLException {
        long id = rs.getLong(1);
        String name = rs.getString(2);
        String email = rs.getString(3);
        String password = rs.getString(4);

        return new Company(id, name, email, password);
    }

    private static Coupon resultSetToCoupon(ResultSet rs) throws SQLException {
        Coupon coupon = new Coupon();
        coupon.setId(rs.getLong(1));
        coupon.setCompanyId(rs.getLong(2));
        coupon.setCategory(rs.getInt(3));
        coupon.setTitle(rs.getString(4));
        coupon.setStartDate(rs.getDate(5));
        coupon.setEndDate(rs.getDate(6));
        coupon.setAmount(rs.getInt(7));
        coupon.setDescription(rs.getString(8));
        coupon.setPrice(rs.getDouble(9));
        coupon.setImage(rs.getString(10));

        return coupon;
    }

    @Override
    public Collection<Company> getAllCompanies() throws SystemMalfunctionException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        Collection<Company> companies;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Schema.SELECT_ID_COMPANIES);

            companies = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long id = rs.getLong(1);
                Company company = getCompany(id);
                companies.add(company);
            }
        } catch (SQLException | NoSuchCompanyException e) {
            throw new SystemMalfunctionException("Failed getting all companies: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
            StatementUtils.close(ps);
        }
        return companies;
    }

    @Override
    public Company login(String email, String password) throws SystemMalfunctionException, InvalidLoginException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        Company company;
        try {
            ps = connection.prepareStatement(Schema.SELECT_COMPANY_BY_EMAIL_AND_PASSWORD);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.first()) {
                company = resultSetToCompany(rs);
                company.setCoupons(getCoupons(company.getId()));
            } else {
                throw new InvalidLoginException(String.format("Invalid login with %s, %s", email, password));
            }
        } catch (SQLException e) {
            throw new InvalidLoginException(String.format("Invalid login with %s, %s", email, password));
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
            StatementUtils.close(ps);
        }
        return company;
    }
}
