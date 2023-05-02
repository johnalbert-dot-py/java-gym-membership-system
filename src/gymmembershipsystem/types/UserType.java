package gymmembershipsystem.types;

import java.time.LocalDate;

public class UserType {
  /*
   * 
   * `id` INT NOT NULL AUTO_INCREMENT,
   * `first_name` VARCHAR(255) NOT NULL,
   * `last_name` VARCHAR(255) NOT NULL,
   * `middle_name` VARCHAR(255) NULL,
   * `weight_in_kg` FLOAT DEFAULT 0.0,
   * `height_in_ft` FLOAT DEFAULT 0.0,
   * `birthdate` DATE NULL,
   * `gender` VARCHAR(255) NOT NULL,
   * `role` INT NOT NULL,
   * `username` VARCHAR(255) NOT NULL,
   * `password` VARCHAR(255) NOT NULL,
   * `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   * `updated_at` DATETIME on update CURRENT_TIMESTAMP NOT NULL DEFAULT
   * CURRENT_TIMESTAMP,
   * 
   * create fields for user saem with this sql fields
   */

  public int id;
  public String firstName;
  public String lastName;
  public String middleName;
  public float weightInKg;
  public float heightInFt;
  public LocalDate birthDate;
  public String gender;
  public int role;
  public String username;
  public LocalDate createdAt;
  public LocalDate updatedAt;
}
