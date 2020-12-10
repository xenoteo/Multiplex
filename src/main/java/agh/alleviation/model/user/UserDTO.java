package agh.alleviation.model.user;

/**
 * User data transfer object used for login.
 *
 * @see agh.alleviation.controller.access_dialog.LoginDialogController
 * @author Ksenia Fiodarava
 */
public class UserDTO {
    private String login;
    private String password;

    /**
     * Creates user data transfer object with login and password.
     * @param login provided login
     * @param password provided password
     */
    public UserDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
