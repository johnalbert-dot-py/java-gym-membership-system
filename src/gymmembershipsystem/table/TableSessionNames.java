package gymmembershipsystem.table;

import javafx.beans.property.SimpleStringProperty;

public class TableSessionNames {
  private final SimpleStringProperty name;

  public TableSessionNames(String name) {
    this.name = new SimpleStringProperty(name);
  }

  public String getName() {
    return name.get();
  }

}
