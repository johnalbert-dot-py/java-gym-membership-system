package gymmembershipsystem.table;

import javafx.beans.property.SimpleStringProperty;

public class TableProgram {

  // create class for tableview with program name and program description
  private final int id;
  private final int memberId;
  private final int programId;
  private final SimpleStringProperty name;
  private final SimpleStringProperty description;
  private final SimpleStringProperty startDate;
  private final SimpleStringProperty endDate;
  private final SimpleStringProperty amount;
  private final SimpleStringProperty paymentStatus;
  private final SimpleStringProperty paymentType;

  public TableProgram(int id, int memberId, int programId, String name, String description, String startDate,
      String endDate,
      String amount,
      String paymentStatus,
      String paymentType) {
    this.id = id;
    this.programId = programId;
    this.memberId = memberId;
    this.name = new SimpleStringProperty(name);
    this.description = new SimpleStringProperty(description);
    this.startDate = new SimpleStringProperty(startDate);
    this.endDate = new SimpleStringProperty(endDate);
    this.amount = new SimpleStringProperty(amount);
    this.paymentStatus = new SimpleStringProperty(paymentStatus);
    this.paymentType = new SimpleStringProperty(paymentType);
  }

  public int getId() {
    return id;
  }

  public int getProgramId() {
    return programId;
  }

  public int getMemberId() {
    return memberId;
  }

  public String getName() {
    return name.get();
  }

  public String getDescription() {
    return description.get();
  }

  public String getStartDate() {
    return startDate.get();
  }

  public String getEndDate() {
    return endDate.get();
  }

  public String getAmount() {
    return amount.get();
  }

  public String getPaymentStatus() {
    return paymentStatus.get();
  }

  public String getPaymentType() {
    return paymentType.get();
  }

}
