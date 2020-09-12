package com.concretepage.model;

import java.io.Serializable;
//import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

//import com.concretepage.DateTimeUtils;
import com.concretepage.service.*;

@Entity
@Table(name="STATUS_ENUM")
public class StatusEnum implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	// @DocumentId
	private long id;

	@Column(name = "NAME", nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Version
	@Column(name = "VERSION", nullable = false)
	private int version;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name ="datum", nullable=false)
	private Date date;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getDate() {
		return date;
	}

//	public void setDate(LocalDate dat) {
//		Date date = DateTimeUtils.toDate(dat);
//		this.date = date;
//	}
	public void setDate(Date dat) {
		this.date = dat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatusEnum other = (StatusEnum) obj;
		if (id != other.id)
			return false;
		if (status != other.status)
			return false;
		if (version != other.version)
			return false;
		return true;
	}



	
}
