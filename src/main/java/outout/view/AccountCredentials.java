package outout.view;

import org.hibernate.validator.constraints.Length;


import jakarta.validation.constraints.NotNull;

public class AccountCredentials {

    @NotNull
    @Length(min = 5, max = 20)
    private String username;
    @NotNull
    @Length(min = 10, max = 50)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
