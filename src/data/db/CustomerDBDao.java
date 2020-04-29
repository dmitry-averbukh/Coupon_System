package data.db;

import common.ConnectionPool;
import common.StatementUtils;
import common.ex.NoSuchCustumerException;
import common.ex.SystemMalfunctionException;
import data.dao.CustomerDao;
import model.Coupon;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("LoopStatementThatDoesntLoop")
public class CustomerDBDao implements CustomerDao {

    private static void applyCustomerValuesOnStatement(Customer customer, PreparedStatement ps) throws SQLException {
        ps.setString(1, customer.getFirstName());
        ps.setString(2, customer.getLastName());
        ps.setString(3, customer.getEmail());
        ps.setString(4, customer.getPassword());
    }

    @Override
    public void createCustomer(Customer customer) throws SystemMalfunctionException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Schema.INSERT_CUSTOMER);
            applyCustomerValuesOnStatement(customer, ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SystemMalfunctionException("There was a problem creating customer: " + e.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
            StatementUtils.close(ps);
        }
    }

    @Override
    public void removeCustomer(long id) throws NoSuchCustumerException {
        PreparedStatement ps = null;
        try {
            ps = ConnectionPool.getInstance()
                    .getConnection()
                    .prepareStatement(Schema.REMOVE_CUSTOMER);
            ps.setLong(1, id);
            int rowsAffective = ps.executeUpdate();
            if (rowsAffective == 0) {
                String msg = "There is no customer with id %d";
                throw new NoSuchCustumerException(String.format(msg, id));
            }
        } catch (SQLException | SystemMalfunctionException e) {
            e.printStackTrace();
        } finally {
            StatementUtils.close(ps);
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws SystemMalfunctionException, SQLException, NoSuchCustumerException {
        PreparedStatement preparedStatement = null;
        preparedStatement = ConnectionPool.getInstance()
                .getConnection()
                .prepareStatement(Schema.UPDATE_COUSTOMER);
        preparedStatement.setString(1, customer.getFirstName());
        preparedStatement.setString(2, customer.getLastName());
        preparedStatement.setString(3, customer.getEmail());
        preparedStatement.setString(4, customer.getPassword());
        preparedStatement.setLong(5, customer.getId());
        int executeUpdate = preparedStatement.executeUpdate();
        if (executeUpdate == 0)
            throw new NoSuchCustumerException("You don't have a customer with that id ");
        StatementUtils.close(preparedStatement);
    }

    @Override
    public Customer getCustomer(long id) throws SystemMalfunctionException {
        Statement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Schema.GET_CUSTOMER + id);

            if (resultSet.first()) {
                String first_name = resultSet.getString(2);
                String last_name = resultSet.getString(3);
                String email = resultSet.getString(4);
                String password = resultSet.getString(5);
                return new Customer(id, first_name, last_name, email, password);
            }
        } catch (SystemMalfunctionException | SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
            StatementUtils.close(statement);
        }
        return null;
    }

    @Override
    public Collection<Customer> getAllCustomers() throws SystemMalfunctionException {
        ArrayList<Customer> allCustumers = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(Schema.GET_ALL_CUSTOMERS);

            while (resultSet.next()) {
//                long id = resultSet.getLong(1);
//                String firstName = resultSet.getString(2);
//                String lastName = resultSet.getString(3);
//                String email = resultSet.getString(4);
//                String password = resultSet.getString(5);
//                allCustumers.add(new Customer(id, firstName, lastName, email, password));

                allCustumers.add(getCustomer(resultSet.getLong(1)));
            }
        } catch (SystemMalfunctionException | SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return allCustumers;

    }

    @Override
    public Collection<Coupon> getCoupons(long customerId) throws SystemMalfunctionException, SQLException, NoSuchCustumerException {
        if (customerId <= 0)
            throw new NoSuchCustumerException("you don't have coustomer with that id!");
        ArrayList<Coupon> customerCoupons = new ArrayList<>();
        final Connection connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSetOfSelectedCoupon = null;
        ResultSet resultSetOfSelectedCustomer = connection
                .createStatement()
                .executeQuery(Schema.GET_CUSTOMER_COUPONS + customerId);
        try {
            while (resultSetOfSelectedCustomer.next()) {
                int couponID = resultSetOfSelectedCustomer.getInt(1);
                resultSetOfSelectedCoupon = connection
                        .createStatement()
                        .executeQuery(Schema.GET_COUPON + couponID);
                if (resultSetOfSelectedCoupon.first()) {
                    customerCoupons.add(CouponDBDao
                            .setCouponValuesOnStatement(resultSetOfSelectedCoupon));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
            if (customerCoupons.size() != 0)
                return customerCoupons;
            else
                throw new NoSuchCustumerException("you don't have coustomer with that id!");

        }
    }

    @Override
    public void insertCustomerCoupon(long couponId, long customerId) throws SystemMalfunctionException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(String.format(Schema.SET_CUSTOMER_COUPON, couponId, customerId));
            preparedStatement.executeUpdate();
        } catch (SystemMalfunctionException | SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
            new StatementUtils().close(preparedStatement);
        }

    }

    @Override
    public Customer login(String email, String password) throws SystemMalfunctionException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(String.format(Schema.CHECK_CUSTOMER, email));
            if (resultSet.first()) {
                if (password.equals(resultSet.getString(1))) {
                    int customerId = resultSet.getInt(2);
                    return getCustomer(customerId);
                }
            }

        } catch (SystemMalfunctionException | SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

        return null;
    }
}
