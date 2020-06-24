package zhoubao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "spitter")
public class Spitter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//IDENTITY
	@Column(name="id")
	private Long id;

	@NotNull
	@Size(min = 5, max = 16)
	@Column(name="username")
	private String username;

	@NotNull
	@Size(min = 5, max = 25)
	@Column(name="password")
	private String password;

	@NotNull
	@Size(min = 2, max = 30)
	@Column(name="first_name")
	private String firstName;

	@NotNull
	@Size(min = 2, max = 30)
	@Column(name="last_name")
	private String lastName;

	@NotNull
	@Email
	@Column(name="email")
	private String email;

	public Spitter() {
	}

	public Spitter(String username, String password, String firstName, String lastName, String email) {
		this(null, username, password, firstName, lastName, email);
	}

	public Spitter(Long id, String username, String password, String firstName, String lastName, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object that) {
		return EqualsBuilder.reflectionEquals(this, that, "firstName", "lastName", "username", "password", "email");
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "firstName", "lastName", "username", "password", "email");
	}

}
