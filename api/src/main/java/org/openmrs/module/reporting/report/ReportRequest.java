package org.openmrs.module.reporting.report;

import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.ObjectUtil;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.renderer.RenderingMode;
import org.openmrs.util.OpenmrsUtil;

/**
 * Represents a request to run and render a report.
 * (The natural ordering of this class places higher priority requests first, so that it may be used in a PriorityQueue.) 
 */
public class ReportRequest extends BaseOpenmrsObject {
	
	//***** PROPERTIES *****

	private Integer id;
	private Mapped<CohortDefinition> baseCohort; //optional
	private Mapped<ReportDefinition> reportDefinition;
	private RenderingMode renderingMode;
	private Priority priority = Priority.NORMAL;
	private String schedule; //optional, in cron format
	private boolean saveAutomatically = false;
	private User requestedBy;
	private Date requestDate;
	private Status status;
	private Date evaluateStartDatetime;
	private Date evaluateCompleteDatetime;
	private Date renderCompleteDatetime;
	private String description;
	
	//*****  CONSTRUCTORS ******
	
	/**
	 * Default Constructor
	 */
	public ReportRequest() { 
		super();
		setUuid(UUID.randomUUID().toString());
		this.requestDate = new Date();
		this.requestedBy = Context.getAuthenticatedUser();
	}

	/**
	 * Full Constructor
	 */
	public ReportRequest(Mapped<ReportDefinition> reportDefinition, Mapped<CohortDefinition> baseCohort,
	                     RenderingMode renderingMode, Priority priority, String schedule) {
	    this();
	    this.reportDefinition = reportDefinition;
	    this.baseCohort = baseCohort;
	    this.renderingMode = renderingMode;
	    this.priority = priority;
	    this.schedule = schedule;
    }
	
	//*****  INSTANCE METHODS ******

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(reportDefinition);
		sb.append(" rendered to " + renderingMode);
		return sb.toString();
	}
	
	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object o) {
		ReportRequest other = (ReportRequest) o;
		if (getUuid() == null || other.getUuid() == null) {
			return this == other;
		} 
		else {
			return getUuid().equals(other.getUuid());
		}
	}
	
	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return (getUuid() == null ? super.hashCode() : getUuid().hashCode());
	}
	
	//*****  PROPERTY ACCESS ******

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
    /**
     * @return the baseCohort
     */
    public Mapped<CohortDefinition> getBaseCohort() {
    	return baseCohort;
    }

    /**
     * @param baseCohort the baseCohort to set
     */
    public void setBaseCohort(Mapped<CohortDefinition> baseCohort) {
    	this.baseCohort = baseCohort;
    }
	
	/**
     * @return the reportDefinition
     */
    public Mapped<ReportDefinition> getReportDefinition() {
    	return reportDefinition;
    }

    /**
     * @param reportDefinition the reportDefinition to set
     */
    public void setReportDefinition(Mapped<ReportDefinition> reportDefinition) {
    	this.reportDefinition = reportDefinition;
    }

    /**
     * @return the renderingMode
     */
    public RenderingMode getRenderingMode() {
    	return renderingMode;
    }
	
    /**
     * @param renderingMode the renderingMode to set
     */
    public void setRenderingMode(RenderingMode renderingMode) {
    	this.renderingMode = renderingMode;
    }

    /**
     * @return the priority
     */
    public Priority getPriority() {
    	return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Priority priority) {
    	this.priority = priority;
    }

	/**
	 * @return the schedule
	 */
	public String getSchedule() {
		return schedule;
	}

	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	
	/**
	 * @return the saveAutomatically
	 */
	public boolean isSaveAutomatically() {
		return saveAutomatically;
	}

	/**
	 * @param saveAutomatically the saveAutomatically to set
	 */
	public void setSaveAutomatically(boolean saveAutomatically) {
		this.saveAutomatically = saveAutomatically;
	}

	/**
     * @return the requestedBy
     */
    public User getRequestedBy() {
    	return requestedBy;
    }

    /**
     * @param requestedBy the requestedBy to set
     */
    public void setRequestedBy(User requestedBy) {
    	this.requestedBy = requestedBy;
    }

    /**
     * @return the requestDate
     */
    public Date getRequestDate() {
    	return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(Date requestDate) {
    	this.requestDate = requestDate;
    }
    
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	
	/**
	 * @return the evaluateStartDatetime
	 */
	public Date getEvaluateStartDatetime() {
		return evaluateStartDatetime;
	}

	/**
	 * @param evaluateStartDatetime the evaluateStartDatetime to set
	 */
	public void setEvaluateStartDatetime(Date evaluateStartDatetime) {
		this.evaluateStartDatetime = evaluateStartDatetime;
	}

	/**
	 * @return the evaluateCompleteDatetime
	 */
	public Date getEvaluateCompleteDatetime() {
		return evaluateCompleteDatetime;
	}

	/**
	 * @param evaluateCompleteDatetime the evaluateCompleteDatetime to set
	 */
	public void setEvaluateCompleteDatetime(Date evaluateCompleteDatetime) {
		this.evaluateCompleteDatetime = evaluateCompleteDatetime;
	}

	/**
	 * @return the renderCompleteDatetime
	 */
	public Date getRenderCompleteDatetime() {
		return renderCompleteDatetime;
	}

	/**
	 * @param renderCompleteDatetime the renderCompleteDatetime to set
	 */
	public void setRenderCompleteDatetime(Date renderCompleteDatetime) {
		this.renderCompleteDatetime = renderCompleteDatetime;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	//***** ENUMS *****

	/**
	 * Priority with which to run these reports. Generally speaking
	 * <ul>
	 * <li>a request that a User initiates that expects an interactive result (e.g. a WebRenderer)
	 * should get HIGHEST priority</li>
	 * <li>a request that a User initiates that expects a file download result should get HIGH
	 * priority</li>
	 * <li>a request created by a demon thread should have LOW or LOWEST priority.</li>
	 * </ul>
	 */
	public enum Priority {
		HIGHEST,
		HIGH,
		NORMAL,
		LOW,
		LOWEST
	}
	
	/**
	 * Represents the Status of a Report
	 */
	public enum Status {
		REQUESTED,
		SCHEDULED,
		PROCESSING,
		FAILED,
		COMPLETED,
		SAVED,
		SCHEDULE_COMPLETED
	}
	
	/**
	 * Compares 2 Requests By Priority, in order to determine which should be evaluated first
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * @should compare by priority
	 * @should compare by request date when priority is the same
	 */
	public static class PriorityComparator implements Comparator<ReportRequest> {
		public int compare(ReportRequest r1, ReportRequest r2) {
			if (r1 == null && r2 == null) { return 0; }
			if (r1 == null) { return -1; }
			if (r2 == null) { return 1; }
			Date d1 = ObjectUtil.nvl(r1.getEvaluateCompleteDatetime(), r1.getEvaluateStartDatetime());
			Date d2 = ObjectUtil.nvl(r2.getEvaluateCompleteDatetime(), r2.getEvaluateStartDatetime());
			int ret = OpenmrsUtil.compareWithNullAsLatest(d1, d2);
			if (ret == 0) {
				ret = r1.getPriority().compareTo(r2.getPriority());
			}
			if (ret == 0) {
				ret = OpenmrsUtil.compareWithNullAsLatest(r1.getRequestDate(), r2.getRequestDate());
			}
		    if (ret == 0) {
		    	ret = OpenmrsUtil.compareWithNullAsGreatest(r1.getUuid(), r2.getUuid());
		    }
			return ret;
		}
	}
}
