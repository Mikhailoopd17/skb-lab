package org.skb_lab.test.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.skb_lab.test.exception.UserExceptions;
import org.skb_lab.test.pojo.base.BaseEntity;
import org.skb_lab.test.utils.Utils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Setter
@Getter
public class RequestBean extends BaseEntity implements Serializable {
    private String login;
    private String password;
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    public void setEmail(String email) {
        if (!Utils.validate(email)) {
            throw new UserExceptions.RestException("Email not valid!");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return "RequestBean{" +
                "id='" + getId() + '\'' +
                ", created='" + getCreated() + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestBean)) return false;
        RequestBean that = (RequestBean) o;
        return getId().equals(that.getId()) &&
                getLogin().equals(that.getLogin()) &&
                getPassword().equals(that.getPassword()) &&
                getEmail().equals(that.getEmail()) &&
                getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getPassword(), getEmail(), getFirstName(), getLastName());
    }
}
