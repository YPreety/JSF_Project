package com.cc.db.operations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cc.bean.StudentBean;

public class DatabaseOperation {

	public static Statement stmtObj;
	public static Connection connObj;
	public static ResultSet resultSetObj;
	public static PreparedStatement pstmt;

	/* Method To Establish Database Connection */
	public static Connection getConnection() {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			String db_url = "jdbc:mysql://localhost:3306/students?" + "useSSL=FALSE";
			String db_userName = "root";
			String db_password = "";
			connObj = DriverManager.getConnection(db_url, db_userName, db_password);
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
		return connObj;
	}

	/* Method To Fetch The Student Records From Database */
	public static ArrayList<StudentBean> getStudentsListFromDB() {
		ArrayList<StudentBean> studentsList = new ArrayList<StudentBean>();
		try {
			stmtObj = getConnection().createStatement();
			resultSetObj = stmtObj.executeQuery("select * from student_record");
			while (resultSetObj.next()) {
				StudentBean stuObj = new StudentBean();
				stuObj.setId(resultSetObj.getInt("student_id"));
				stuObj.setName(resultSetObj.getString("student_name"));
				stuObj.setUserName(resultSetObj.getString("student_username"));
				stuObj.setGender(resultSetObj.getString("student_gender"));
				stuObj.setContactNumber(Integer.valueOf(resultSetObj.getString("contact_number")));
				stuObj.setEmail(resultSetObj.getString("student_email"));
				stuObj.setCollege(resultSetObj.getString("student_college"));
				stuObj.setSkills(resultSetObj.getString("programming_skill"));
				studentsList.add(stuObj);

			}
			connObj.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
		return studentsList;
	}

	/* Method Used To Save New Student Record In Database */
	public static String saveStudentDetailsInDB(StudentBean newStudentObj) throws Exception {
		int saveResult = 0;
		String navigationResult = "";

		try {
			pstmt = getConnection().prepareStatement(
					"insert into student_record (student_name, student_username, student_password, student_gender, contact_number , student_email,student_college,programming_skill) values (?,?, ?, ?, ?, ?,?,?)");
			pstmt.setString(1, newStudentObj.getName());
			pstmt.setString(2, newStudentObj.getUserName());
			pstmt.setString(3, newStudentObj.getPassword());
			pstmt.setString(4, newStudentObj.getGender());
			pstmt.setInt(5, newStudentObj.getContactNumber());
			pstmt.setString(6, newStudentObj.getEmail());
			pstmt.setString(7, newStudentObj.getCollege());

			String skills = list2String(newStudentObj.getProgrammingSkill());
			String replace = skills.replaceAll("^\\[|]$", "");
			pstmt.setString(8, replace);
			saveResult = pstmt.executeUpdate();
			connObj.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
		if (saveResult != 0) {
			navigationResult = "studentsList.xhtml?faces-redirect=true";
		} else {
			navigationResult = "createStudent.xhtml?faces-redirect=true";
		}
		return navigationResult;
	}

	public static String list2String(List<String> list) {
		String str = null;
		str = list.toString();
		return str;
	}
}
