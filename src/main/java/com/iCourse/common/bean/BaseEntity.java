package com.iCourse.common.bean;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity<PK extends Serializable> 
{
	private PK id;

	public BaseEntity(PK id2) {
		this.id = id2;
	}

	public BaseEntity() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public PK getId() {
		return id;
	}
	
	public void setId(PK id) {
		this.id = id;
	}
}
