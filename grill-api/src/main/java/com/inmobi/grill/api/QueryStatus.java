package com.inmobi.grill.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "querystatus")
public class QueryStatus {
  public enum Status {
    QUEUED,
    LAUNCHED,
    RUNNING,
    SUCCESSFUL,
    FAILED,
    CANCELED,
    CLOSED,
    UNKNOWN
  }
  
  @XmlElement
	private double progress;
  @XmlElement
	private String statusMessage;
  @XmlElement
	private Status status;
  @XmlElement
	private boolean hasResultSet = false;
  private final String driverOpHandle;

  public QueryStatus() {
    // for jaxb
  }
  public QueryStatus(double progress, Status status, String statusMessage,
      boolean hasResultSet) {
    this(progress, status, statusMessage, hasResultSet, null);
  }

  public QueryStatus(double progress, Status status, String statusMessage,
                     boolean hasResultSet, String driverOpHandle) {
    this.progress = progress;
    this.status = status;
    this.statusMessage = statusMessage;
    this.hasResultSet = hasResultSet;
    this.driverOpHandle = driverOpHandle;
  }
  
  /**
   * Get status of the query
   * 
   * @return {@link Status}
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Get progress of the query between 0 to 1.
   * 
   * @return progress
   */
  public double getProgress() {
    return progress;
  }

  /**
   * Get status message
   *  
   * @return string message
   */
  public String getStatusMessage() {
    return statusMessage;
  }

  /**
   * Whether query has result set
   * 
   * @return true if result set is available, false otherwise
   */
  public boolean hasResultSet() {
    return hasResultSet;
  }

  /**
   * Return the driver specific operation handle created for this query
   * @return handle identifier
   */
  public String getDriverOpHandle() {
    return driverOpHandle;
  }
  
  @Override
  public String toString() {
  	return new StringBuilder(status.toString()).append(':')
  			.append(progress).append(':')
  			.append(hasResultSet).append(':').
  			append(statusMessage).toString();
  }

  public boolean isFinished() {
    return status.equals(Status.SUCCESSFUL) || status.equals(Status.FAILED) ||
        status.equals(Status.CANCELED);
  }
}
