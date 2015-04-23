package by.itechart.flowerty.solr.model;

/**
* @author Maria
*         Date: 11.04.15
*/

import org.apache.solr.client.solrj.beans.Field;
//import org.apache.solr.client.solrj.impl.
import org.springframework.data.annotation.Id;

import java.util.Date;

//@SolrDocument
//@Entity
//@Indexed
public class ContactDocument {
    public static Builder getBuilder(Long id, String name) {
        return new Builder(id, name);
    }

    private String id;
    private String name;
    private String surname;
    private String fathername;
    private Date birthday;
    private BirthdaySearchType searchType;
    private String email;
    private String country;
    private String town;
    private String street;
    private String house;
    private String flat;

    private Date birthdayBefore;
    private Date birthdayAfter;
    public static enum BirthdaySearchType {
        BEFORE, BY_DATE, AFTER
    }
    public String getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }
    public String getFathername() {
        return fathername;
    }

    public Date getBirthday() {
        return birthday;
    }

    public BirthdaySearchType getSearchType() {
        return searchType;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public String getTown() {
        return town;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getFlat() {
        return flat;
    }

    public Date getBirthdayBefore() {
        return birthdayBefore;
    }

    public Date getBirthdayAfter() {
        return birthdayAfter;
    }

    @Id
    @Field
    public void setId(String id) {
        this.id = id;
    }


    @Field
   // @Temporal(TemporalType.)
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
 //   @Field
    public void setSearchType(BirthdaySearchType searchType) {
        this.searchType = searchType;
    }
    @Field
    public void setEmail(String email) {
        this.email = email;
    }
    @Field
    public void setName(String name) {
        this.name = name;
    }
    @Field
    public void setSurname(String surname) {
        this.surname = surname;
    }
    @Field
    public void setFathername(String fathername) {
        this.fathername = fathername;
    }
    @Field
    public void setCountry(String country) {
        this.country = country;
    }
    @Field
    public void setTown(String town) {
        this.town = town;
    }
    @Field
    public void setStreet(String street) {
        this.street = street;
    }
    @Field
    public void setHouse(String house) {
        this.house = house;
    }
    @Field
    public void setFlat(String flat) {
        this.flat = flat;
    }

    public void setBirthdayBefore(Date birthdayBefore) {
        this.birthdayBefore = birthdayBefore;
    }

    public void setBirthdayAfter(Date birthdayAfter) {
        this.birthdayAfter = birthdayAfter;
    }

    public static class Builder {
        private ContactDocument build;

        public Builder(Long id, String name) {
            build = new ContactDocument();
            build.id = id.toString();
            build.name = name;
        }

        public Builder surname(String surname) {
            build.surname = surname;
            return this;
        }
        public Builder country(String country) {
            build.country = country;
            return this;
        }
        public Builder street(String street) {
            build.street = street;
            return this;
        }
        public Builder birthday(Date birthday) {
            build.birthday = birthday;
            return this;
        }
        public Builder fathername(String fathername) {
            build.fathername = fathername;
            return this;
        }
        public Builder flat(String flat) {
            build.flat = flat;
            return this;
        }

        public ContactDocument build() {
            return build;
        }
    }

    public ContactDocument(String id, String name, String surname, Date birthday, String email, String country, String town, String street, String house, String flat) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.email = email;
        this.country = country;
        this.town = town;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    public ContactDocument() {
    }
}