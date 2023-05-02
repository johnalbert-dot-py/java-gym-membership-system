package gymmembershipsystem.table;

import javafx.beans.property.SimpleStringProperty;

public class TableUser {
  private SimpleStringProperty id;
  private SimpleStringProperty firstName;
  private SimpleStringProperty lastName;
  private SimpleStringProperty middleName;
  private SimpleStringProperty weightInKg;
  private SimpleStringProperty heightInFt;
  private SimpleStringProperty birthdate;
  private SimpleStringProperty gender;

  public TableUser(String id, String firstName, String lastName, String middleName, String weightInKg,
      String heightInFt, String birthdate, String gender) {
    this.id = new SimpleStringProperty(id);
    this.firstName = new SimpleStringProperty(firstName);
    this.lastName = new SimpleStringProperty(lastName);
    this.middleName = new SimpleStringProperty(middleName);
    this.weightInKg = new SimpleStringProperty(weightInKg);
    this.heightInFt = new SimpleStringProperty(heightInFt);
    this.birthdate = new SimpleStringProperty(birthdate);
    this.gender = new SimpleStringProperty(gender);
  }

  // Getters
  public String getId() {
    return id.get();
  }

  public String getFirstName() {
    return firstName.get();
  }

  public String getLastName() {
    return lastName.get();
  }

  public String getMiddleName() {
    return middleName.get();
  }

  public String getWeightInKg() {
    return weightInKg.get();
  }

  public String getHeightInFt() {
    return heightInFt.get();
  }

  public String getBirthdate() {
    return birthdate.get();
  }

  public String getGender() {
    return gender.get();
  }

  // Setters
  public void setId(String id) {
    this.id.set(id);
  }

  public void setFirstName(String firstName) {
    this.firstName.set(firstName);
  }

  public void setLastName(String lastName) {
    this.lastName.set(lastName);
  }

  public void setMiddleName(String middleName) {
    this.middleName.set(middleName);
  }

  public void setWeightInKg(String weightInKg) {
    this.weightInKg.set(weightInKg);
  }

  public void setHeightInFt(String heightInFt) {
    this.heightInFt.set(heightInFt);
  }

  public void setBirthdate(String birthdate) {
    this.birthdate.set(birthdate);
  }

  public void setGender(String gender) {
    this.gender.set(gender);
  }
}
