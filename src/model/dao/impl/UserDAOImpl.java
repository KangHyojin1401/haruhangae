
package model.dao.impl;

import java.sql.ResultSet;

import model.UserDTO;
import model.dao.UserDAO;

/**
 * 사용자 관리를 위해 데이터베이스 작업을 전담하는 DAO 클래스 USER 테이블에 사용자 정보를 추가, 수정, 삭제, 검색 수행
 */
public class UserDAOImpl implements UserDAO {
	private JDBCUtil jdbcUtil = null;

	public UserDAOImpl() {
		jdbcUtil = new JDBCUtil();
	}

	/* 회원가입 */
	public int createUser(UserDTO user) {
		String sql = "INSERT INTO MEMBER (userID, password, alias, email, phone) " + "VALUES (?, ?, ?, ?, ?)";
		Object[] param = new Object[] { user.getUserID(), user.getPassword(), user.getAlias(), user.getEmail(),
				user.getPhone() };
		jdbcUtil.setSqlAndParameters(sql, param);

		try {
			int result = jdbcUtil.executeUpdate();
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}
		return 0;
	}

	/* 회원 정보 수정 */
	public int updateUser(UserDTO user) {
		String sql = "UPDATE MEMBER " + "SET password=?, alias=?, email=?, phone=? " + "WHERE userID=?";
		Object[] param = new Object[] { user.getPassword(), user.getAlias(), user.getEmail(), user.getPhone(),
				user.getUserID() };
		jdbcUtil.setSqlAndParameters(sql, param);

		try {
			int result = jdbcUtil.executeUpdate();
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}
		return 0;
	}

	/* 회원 탈퇴 */
	public int removeUser(String userID) {
		String sql = "DELETE FROM MEMBER WHERE USERID=?";
		jdbcUtil.setSqlAndParameters(sql, new Object[] { userID });

		try {
			int result = jdbcUtil.executeUpdate();
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}
		return 0;
	}

	/**
	 * 주어진 사용자 ID에 해당하는 사용자 정보를 데이터베이스에서 찾아 User 도메인 클래스에 저장하여 반환.
	 */

	public UserDTO findUser(String userID) {
		String sql = "SELECT password, alias, email, phone " + "FROM MEMBER " + "WHERE userID = ? ";
		jdbcUtil.setSqlAndParameters(sql, new Object[] { userID });

		try {
			ResultSet rs = jdbcUtil.executeQuery();

			if (rs.next()) {
				String password = rs.getString("password");
				String alias = rs.getString("alias");
				String email = rs.getString("email");
				String phone = rs.getString("phone");

				return new UserDTO(userID, password, alias, email, phone, addTotalMission(userID),
						addTotalReward(userID), findCatName(userID));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return null;
	}

	public int addTotalMission(String userID) {
		String sql = "SELECT count(*) " + "FROM POST " + "WHERE userID = ?";
		jdbcUtil.setSqlAndParameters(sql, new Object[] { userID });

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return 0;
	}

	public int addTotalReward(String userID) {
		String sql = "SELECT nvl(count(*), 0) " + "FROM USERREWARD " + "WHERE userID = ?";
		jdbcUtil.setSqlAndParameters(sql, new Object[] { userID });

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return 0;
	}

	/* 현재 진행중인 카테고리 이름을 반환 */
	public String findCatName(String userID) {
		String sql = "SELECT catName "
				+ "FROM (SELECT rownum, uCatID, catName FROM USERCATEGORYINFO uc, category c WHERE uc.catid = c.catid and userID = ? ORDER BY uCatID DESC) "
				+ "WHERE rownum = 1 ";

		jdbcUtil.setSqlAndParameters(sql, new Object[] { userID });

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {
				return rs.getString("catName");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return null;
	}

	/* 카테고리 성공 여부 (14일 중 12일 이상) */
	public boolean isSuccessed(String userID, String catID) {
		String sql = "SELECT missionClearCnt " + "FROM USERCATEGORYINFO " + "WHERE userID = ? AND catID = ?";
		jdbcUtil.setSqlAndParameters(sql, new Object[] { userID, catID });

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {
				int missionClearCnt = rs.getInt(1);
				return (missionClearCnt >= 12 ? true : false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return false;
	}

	/**
	 * 주어진 사용자 ID에 해당하는 사용자가 존재하는지 검사
	 */
	public boolean existingUser(String userID) {
		String sql = "SELECT count(*) FROM MEMBER WHERE userID=?";
		jdbcUtil.setSqlAndParameters(sql, new Object[] { userID });

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return (count == 1 ? true : false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return false;
	}
}