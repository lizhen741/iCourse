package com.iCourse.user.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.iCourse.common.bean.BaseEntity;
import com.iCourse.course.bean.Clazz;

@Entity
@Table(name = "t_student")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Student extends BaseEntity<Long> implements Serializable {
	private String student_name;
	private String student_phone;
	private StuCertificate certificate;
	private Account account;
	private StuQualificate qualificate;
	private List<Clazz> clazzes = new ArrayList<Clazz>();
	private List<TeacherStudentLib> teacherStudentLib = new ArrayList<TeacherStudentLib>();

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	@JsonIgnore
	public List<TeacherStudentLib> getTeacherStudentLib() {
		return teacherStudentLib;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
	public Account getAccount() {
		return account;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
	public StuCertificate getCertificate() {
		return certificate;
	}

	@JsonBackReference
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "t_clazz_student", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "clazz_id"))
	public List<Clazz> getClazzes() {
		return clazzes;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "qualificate_id")
//	@JsonIgnore
	public StuQualificate getQualificate() {
		return qualificate;
	}

	public void setQualificate(StuQualificate qualificate) {
		this.qualificate = qualificate;
	}

	public void setClazzes(List<Clazz> clazzes) {
		this.clazzes = clazzes;
	}

	public String getStudent_phone() {
		return student_phone;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public void setStudent_phone(String student_phone) {
		this.student_phone = student_phone;
	}

	public void setCertificate(StuCertificate certificate) {
		this.certificate = certificate;
	}

	public void setTeacherStudentLib(List<TeacherStudentLib> teacherStudentLib) {
		this.teacherStudentLib = teacherStudentLib;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Student [student_name=" + student_name + "]";
	}

}
