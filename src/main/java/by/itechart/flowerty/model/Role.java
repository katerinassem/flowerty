package by.itechart.flowerty.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 10, nullable = false)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "NAME", nullable = false)
    private ROLE_TYPE name;

    public static enum ROLE_TYPE {
	ORDERS_MANAGER, 
	ORDERS_PROCESSOR, 
	DELIVERY_MANAGER, 
	SUPERVISOR, ADMIN
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "role_right", 
    	joinColumns = { @JoinColumn(name = "ROLE_ID") }, 
    	inverseJoinColumns = { @JoinColumn(name = "RIGHT_ID") })
    private Set<Right> rights = new HashSet<Right>();

    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<User>();

    public Role() {
    }

    public Role(Long id, ROLE_TYPE name, Set<Right> rights, Set<User> users) {
	this.id = id;
	this.name = name;
	this.rights = rights;
	this.users = users;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public ROLE_TYPE getName() {
	return name;
    }

    public void setName(ROLE_TYPE name) {
	this.name = name;
    }

    public Set<Right> getRights() {
	return rights;
    }

    public void setRights(Set<Right> rights) {
	this.rights = rights;
    }

    public Set<User> getUsers() {
	return users;
    }

    public void setUsers(Set<User> users) {
	this.users = users;
    }
}