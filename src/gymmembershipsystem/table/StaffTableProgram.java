package gymmembershipsystem.table;

public class StaffTableProgram {
  private int id;
  private String firstName;
  private String lastName;
  private String middleName;
  private String fullName;
  private String programName;
  private String startDate;
  private String endDate;
  private String paymentStatus;
  private String paymentType;

  public StaffTableProgram(int id, String firstName, String lastName, String middleName, String programName,
      String startDate, String endDate,
      String paymentStatus, String paymentType) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
    this.fullName = firstName + " " + middleName + " " + lastName;
    this.programName = programName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.paymentStatus = paymentStatus;
    this.paymentType = paymentType;
  }

  // Getters
  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getFullName() {
    return fullName;
  }

  public String getProgramName() {
    return programName;
  }

  public String getStartDate() {
    return startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public String getPaymentStatus() {
    return paymentStatus;
  }

  public String getPaymentType() {
    return paymentType;
  }

  // Setters
  public void setId(int id) {
    this.id = id;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public void setProgramName(String programName) {
    this.programName = programName;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public void setPaymentStatus(String paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }
}
