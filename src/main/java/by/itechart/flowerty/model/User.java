package by.itechart.flowerty.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Мария
 * Date: 19.03.15
 * Time: 22:29
 */
@Entity
@Table(name="USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOGIN", length = 20, nullable = false)
    private String login;

    @Column(name = "PASSWORD", length = 20, nullable = false)
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;
    
    @OneToOne(mappedBy = "user")
    private Contact contact;
    
    public User() {
    }

    public User(Long id, String login, String password, Role role, Contact contact) {
	super();
	this.id = id;
	this.login = login;
	this.password = password;
	this.role = role;
	this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
