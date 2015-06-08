package by.itechart.flowerty.persistence.model;

import by.itechart.flowerty.solr.model.ContactDocument;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.beans.Field;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "contact")
public class Contact {
        private Long id;
        private String name;
        private String surname;
        private String fathername;
        private Date birthday;
        private String email;
        private Address address;
        private Set<Phone> phones;
        private Company company;

        public Contact() {

        }

        public Contact(Long id,
                        String name,
                        String surname,
                        String fathername,
                        Date birthday,
                        String email,
                        Address address,
                        Company company) {

                this.id = id;
                this.name = name;
                this.surname = surname;
                this.fathername = fathername;
                this.birthday = birthday;
                this.email = email;
                this.address = address;
                this.company = company;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        @Field
        public Long getId() {

                return id;
        }

        @Column(name = "NAME", length = 20, nullable = true)
        @NotNull
        @Size(max = 20)
        public String getName() {

                return name;
        }

        @Column(name = "SURNAME", length = 20, nullable = true)
        @NotNull
        @Size(max = 20)
        public String getSurname() {

                return surname;
        }

        @Column(name = "FATHERNAME", length = 20, nullable = true)
        @Size(max = 20)
        public String getFathername() {

                return fathername;
        }

        @Column(name = "BIRTHDAY", nullable = true)
        @Temporal(value = TemporalType.DATE)
        @Past
        public Date getBirthday() {

                return birthday;
        }

        @Column(name = "EMAIL", length = 50, nullable = true)
        @Size(max = 50)
        @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
        public String getEmail() {

                return email;
        }

        @OneToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
        @JoinColumn(name = "ADDRESS_ID")
        @Valid
        public Address getAddress() {

                return address;
        }

        @Valid
        @OneToMany(mappedBy = "contact", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
        public Set<Phone> getPhones() {

                return phones;
        }

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "COMPANY_ID")
        @Valid
        public Company getCompany() {

                return company;
        }

        public void setId(Long id) {

                this.id = id;
        }

        public void setName(String name) {

                this.name = name;
        }

        public void setSurname(String surname) {

                this.surname = surname;
        }

        public void setFathername(String fathername) {

                this.fathername = fathername;
        }

        public void setBirthday(Date birthday) {

                this.birthday = birthday;
        }

        public void setAddress(Address address) {

                this.address = address;
        }

        public void setPhones(Set<Phone> phones) {

                this.phones = phones;
        }

        public void setCompany(Company company) {

                this.company = company;
        }

        public void setEmail(String email) {

                this.email = email;
        }

        @Transient
        @JsonIgnore
        public String getFullName() {

                String theName = StringUtils.isNotEmpty(name) ? "" : name;
                String theSurname = StringUtils.isNotEmpty(surname) ? "" : surname;
                String theFathername = StringUtils.isNotEmpty(fathername) ? "" : fathername;
                return String.format("%s %s %s", theFathername, theName, theSurname);
        }

        @Transient
        @JsonIgnore
        public ContactDocument getContactDocument() {

                return id == null ? (address == null ? new ContactDocument(name, surname, fathername, birthday, email, company.getId())
                                : new ContactDocument("", name, surname, fathername, birthday, email, address.getCountry(),
                                                address.getTown(), address.getStreet(), address.getHouse(), address.getFlat(),
                                                company.getId())) : new ContactDocument(id.toString(), name, surname, fathername, birthday,
                                email, address.getCountry(), address.getTown(), address.getStreet(), address.getHouse(), address.getFlat(),
                                company.getId());
        }

        @Override
        public String toString() {

                StringBuilder sb = new StringBuilder();
                sb.append("[id:").append(id).append("\n name:").append(name).append("\n surname:").append(surname).append("\n fathername:")
                                .append(fathername).append("\n birthday:").append(birthday).append("\n email:").append(email)
                                .append("\n address:").append(address);
                if (phones != null) {
                        sb.append("; phones:");
                        for (Phone phone : phones) {
                                sb.append(phone);
                        }
                }
                return sb.append("]\n").toString();
        }

        @Override
        public boolean equals(Object obj) {

                // TODO
                if (!(obj instanceof Contact)) {
                        return false;
                }
                return true;
        }
}
