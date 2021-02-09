package com.cc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.cc.db.operations.DatabaseOperation;

@ManagedBean
@RequestScoped
public class StudentBean implements Serializable {
	private static final long serialVersionUID = -7250065889869767422L;
	private int id;
	private String name;
	private String userName;
	private String password;
	private String gender;
	private String email;
	private Integer contactNumber;
	private List<String> programmingSkill;
	private String college;
	private String skills;

	public ArrayList<StudentBean> studentsListFromDB;

	public StudentBean() {
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Integer contactNumber) {
		this.contactNumber = contactNumber;
	}

	public List<String> getProgrammingSkill() {
		return programmingSkill;
	}

	public void setProgrammingSkill(List<String> programmingSkill) {
		this.programmingSkill = programmingSkill;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	/*
	 * Method Will Avoid Multiple Calls To DB For Fetching The Students Records. If
	 * This Is Not Used & Data Is Fetched From Getter Method, JSF DataTable Will
	 * Make Multiple Calls To DB
	 */
	@PostConstruct
	public void init() {
		studentsListFromDB = DatabaseOperation.getStudentsListFromDB();
	}

	/* Method Used To Fetch All Records From The Database */
	public ArrayList<StudentBean> studentsList() {
		return studentsListFromDB;
	}

	/* Method Used To Save New Student Record */
	public String saveStudentDetails(StudentBean newStudentObj) throws Exception {
		String navigationResult = "";
		if (null != newStudentObj) {
			return DatabaseOperation.saveStudentDetailsInDB(newStudentObj);
		}
		navigationResult = "createStudent.xhtml?faces-redirect=true";
		return navigationResult;

	}

	public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
		String confirm = (String) value;
		UIInput passComp = (UIInput) toValidate.getAttributes().get("passwordComponent");
		String password = (String) passComp.getValue();
		if (!password.equals(confirm)) {
			FacesMessage message = new FacesMessage("Password and Confirm Password Should match");
			throw new ValidatorException(message);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		StudentBean other = (StudentBean) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
