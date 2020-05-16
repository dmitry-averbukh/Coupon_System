package facade;

import common.LoginType;
import common.ex.SystemMalfunctionException;
import data.ex.InvalidLoginException;

public abstract class AbsFacade {

    /**
     * A helper method for logging a user into the system by getting its relevant facade class.+
     *
     * @param userName
     * @param password
     * @param loginType
     * @return
     * @throws InvalidLoginException
     */
    public static AbsFacade login(String userName, String password, int loginType) throws InvalidLoginException, SystemMalfunctionException {
        switch (loginType) {
            case LoginType.ADMIN:
                return AdminFacade.performLogin(userName, password);
            case LoginType.COMPANY:
                return CompanyFacade.performLogin(userName, password);
            case LoginType.CUSTOMER:
                return CustomerFacade.performLogin(userName, password);
            default:
                throw new InvalidLoginException("Login type is not supported!");
        }
    }
}
