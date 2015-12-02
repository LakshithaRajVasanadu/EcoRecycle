package com.ecoRecycle.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RMOS")
public class Rmos {
	
	@Id 
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "name", unique = true)
	private String name;
	
	@OneToOne
	@JoinColumn(name = "locationId")
	private Location location;
	
	@OneToOne
	@JoinColumn(name = "administratorId")
	private Administrator admin;
	
	@Column(name = "createDateTime")
	private Date createDateTime;
	
	@Column(name = "updateDateTime")
	private Date updateDateTime;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "rmos", cascade = CascadeType.ALL)
	private Set<RmosRcmMapping> rmosRcmMappings = new HashSet<RmosRcmMapping>();
	
    public Set<RmosRcmMapping> getRmosRcmMappings() {
        return rmosRcmMappings;
    }
 
    public void setRmosRcmMappings(Set<RmosRcmMapping> rmosRcmMappings) {
        this.rmosRcmMappings = rmosRcmMappings;
    }
     
    public void addRmosRcmMapping(RmosRcmMapping rmosRcmMapping) {
        this.rmosRcmMappings.add(rmosRcmMapping);
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Administrator getAdmin() {
		return admin;
	}

	public void setAdmin(Administrator admin) {
		this.admin = admin;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	@Override
	public String toString() {
		return "Rmos [id=" + id + ", name=" + name + ", location=" + location
				+ ", admin=" + admin + "]";
	} 
}
