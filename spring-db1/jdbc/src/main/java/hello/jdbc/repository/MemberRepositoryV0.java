package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;//PreparedStatement이걸로 쿼리날림

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);//sql 넘김
            pstmt.setString(1, member.getMemberId());//파라미터 바인딩
            pstmt.setInt(2, member.getMoney());
            //SQL Injection 공격을 예방하려면 PreparedStatement 를 통한 파라미터 바인딩 방식 사용
            pstmt.executeUpdate();//디비에 실행
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;//로그만 남기고 던짐
        } finally {//호출보장
            close(con, pstmt, null);
            //리소스 반환하지않으면 커넥션이 끊어지지않고 유지되는 문제. 결과적으로 커넥션 부족장애발생
        }

    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();//select

            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {//예외가 터져도  stmt.close();  con.close();는 실행한다
                log.info("error", e);
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
