package Assets.Database;


import java.util.Date;


public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Date createdAt;
    private Date lastLogin;
    private boolean isAdmin;

    // user permissions
    private boolean canCreateProducts;
    private boolean canViewProdcuts;
    private boolean canUpdateProdcuts;
    private boolean canDeleteProducts;

    public boolean isCanCreateProducts() {
        return canCreateProducts;
    }

    public void setCanCreateProducts(boolean canCreateProducts) {
        this.canCreateProducts = canCreateProducts;
    }

    public boolean isCanViewProdcuts() {
        return canViewProdcuts;
    }

    public void setCanViewProdcuts(boolean canViewProdcuts) {
        this.canViewProdcuts = canViewProdcuts;
    }

    public boolean isCanUpdateProdcuts() {
        return canUpdateProdcuts;
    }

    public void setCanUpdateProdcuts(boolean canUpdateProdcuts) {
        this.canUpdateProdcuts = canUpdateProdcuts;
    }

    public boolean isCanDeleteProducts() {
        return canDeleteProducts;
    }

    public void setCanDeleteProducts(boolean canDeleteProducts) {
        this.canDeleteProducts = canDeleteProducts;
    }

    public User(int id, String username, String password, String email, String firstName, String lastName, Date createdAt, Date lastLogin, boolean isAdmin, boolean canCreateProducts, boolean canDeleteProducts, boolean canUpdateProdcuts, boolean canViewProdcuts) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.isAdmin = isAdmin;

        this.canCreateProducts = canCreateProducts;
        this.canDeleteProducts = canDeleteProducts;
        this.canUpdateProdcuts = canUpdateProdcuts;
        this.canViewProdcuts = canViewProdcuts;
    }

    public User(int id, String username, String password, String email, String firstName, String lastName, Date createdAt, Date lastLogin, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.isAdmin = isAdmin;

        this.canCreateProducts = false;
        this.canDeleteProducts = false;
        this.canUpdateProdcuts = false;
        this.canViewProdcuts = false;
    }



    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    // toString method to print User object
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                ", lastLogin=" + lastLogin +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
