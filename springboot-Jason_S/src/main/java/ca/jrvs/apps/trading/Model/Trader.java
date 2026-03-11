package ca.jrvs.apps.trading.Model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="trader")
public class Trader {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;
  String first_name;
  String last_name;
  LocalDate dob;
  String country;
  String email;

  public Integer getId() {
    return id;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public LocalDate getDob() {
    return dob;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
