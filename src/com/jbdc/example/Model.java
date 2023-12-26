package com.jbdc.example;

import java.sql.*;
import java.util.ArrayList;

public class Model {

        private String url = "jdbc:oracle:thin:@localhost:1521:xe";
        private String uid = "hr";
        private String upw = "hr";

    Model(){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (Exception e){

        }
    }
    public void selectOne(String a){


        String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID >=?";
        //모든 jdbc코드는 try~catch구문에서 작성이 들어가야 합니다.()

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            //1. jdbc드라이버 준비
            //2. conn객체생성
            conn = DriverManager.getConnection(url, uid, upw);
            //3. conn으로부터 statement객체 생성 - sql상태를 지정하기 위한 객체
            pstmt = conn.prepareStatement(sql);
            //?에 대한 값을 채웁니다.
            pstmt.setString(1, a);
            //4. 실행
            //executeQuery - select문에 사용합니다.
            //executeUpdate - insert, update, delete문에 사용합니다.
            rs = pstmt.executeQuery();

            while(rs.next()){ // 다음이 있다면 true, 없다면 false
                //rs.getString(컬럼명) - 문자열 반환
                //rs.getInt(컬럼명) - 정수반환
                //rs.getDouble(컬럼명) - 실수형반환
                //rs.getDate(컬럼명) - 날짜형반환
                int emp_id = rs.getInt("EMPLOYEE_ID");
                String first_name = rs.getString("FIRST_NAME");
                String phone_number = rs.getString("PHONE_NUMBER");
                //String hire_date = rs.getString("HIRE_DATE");
                Timestamp hire_date = rs.getTimestamp("HIRE_DATE");
                int salary = rs.getInt("salary");

                System.out.println("-----------------------------------");
                System.out.printf("아이디 : %d%n", emp_id);
                System.out.printf("이름 : %s%n", first_name);
                System.out.println("전화번호 : " + phone_number);
                System.out.printf("입사일 : %s%n", hire_date);
                System.out.printf("급여 : %d%n", salary);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                conn.close();
                pstmt.close();
                rs.close();
            }catch (Exception e2){

            }
        }
    }

    //insert
    public void insertOne(int a, String b, String c, String d){

        Connection conn = null;
        PreparedStatement pstmt = null;
        //resultSet은 insert에서 필요가 없습니다.

        String sql = "INSERT INTO DEPTS VALUES(?,?,?,?)";

        try{

            //1. conn생성
            conn = DriverManager.getConnection(url, upw, uid);
            //2. pstmt생성
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,a);
            pstmt.setString(2,b);
            pstmt.setString(3,c);
            pstmt.setString(4,d);
            //3.sql실행
            int result = pstmt.executeUpdate(); // 성공시 1 or 실패시 0

            if(result == 1) System.out.println("insert 성공");
            else System.out.println("insert 실패");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                conn.close();
                pstmt.close();
            }catch (Exception e2){

            }
        }
    }

    public void updateOne(int c, String a, String b){
        String sql ="UPDATE DEPTS SET DEPARTMENT_NAME = ?, MANAGER_ID = ? WHERE DEPARTMENT_ID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{


            conn = DriverManager.getConnection(url, upw, uid);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,a);
            pstmt.setString(2,b);
            pstmt.setInt(3,c);

            if(pstmt.executeUpdate()>0) System.out.println("update 성공");
            else System.out.println("update 실패");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                conn.close();
                pstmt.close();
            }catch (Exception e2){

            }
        }
    }

    public void deleteOne(int a){
        String sql ="DELETE FROM DEPTS WHERE DEPARTMENT_ID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{


            conn = DriverManager.getConnection(url, upw, uid);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,a);

            if(pstmt.executeUpdate()>0) System.out.println("성공");
            else System.out.println("실패");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                conn.close();
                pstmt.close();
            }catch (Exception e2){

            }
        }

    }

    // 사워번호, 이름 ,부사명 - 급여순으로 정렬을해서 10~20번에 속해있는 데이터. 출력
    public ArrayList<EmployeeVO> selectTwo(){

        //값을 담을 ArrayList
        ArrayList<EmployeeVO> list = new ArrayList<>();
        String sql = "SELECT *\n" +
                "FROM(\n" +
                "    SELECT ROWNUM AS RN, A.*\n" +
                "    FROM\n" +
                "        (SELECT EMPLOYEE_ID, LAST_NAME, DEPARTMENT_NAME, SALARY \n" +
                "        FROM EMPLOYEES E\n" +
                "        JOIN DEPARTMENTS D\n" +
                "        ON E.DEPARTMENT_ID = D.DEPARTMENT_ID\n" +
                "        ORDER BY SALARY) A\n" +
                "    )\n" +
                "WHERE RN BETWEEN 10 AND 20";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = DriverManager.getConnection(url, uid, upw);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                int emp_id =  rs.getInt("EMPLOYEE_ID");
                String emp_name = rs.getString("LAST_NAME");
                String emp_dName = rs.getString("DEPARTMENT_NAME");
                int emp_salary = rs.getInt("SALARY");
                list.add(new EmployeeVO(emp_id,emp_name,emp_salary,emp_dName));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                conn.close();
                pstmt.close();
                rs.close();
            }catch (Exception e2){

            }
        }
        return list;
    }
}
