package model.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.CategoryDTO;
import model.dao.CategoryDAO;

public class CategoryDAOImpl implements CategoryDAO {

	private JDBCUtil jdbcUtil = null;

	public CategoryDAOImpl() {
		jdbcUtil = new JDBCUtil();
	}

	public List<CategoryDTO> getCategoryList() {
		String searchQuery = "SELECT CATEGORY.catName AS CATEGORY_NAME, category.catID AS catID  FROM Category";
		jdbcUtil.setSqlAndParameters(searchQuery, null); // JDBCUtil 에 query 설정

		try {
			ResultSet rs = jdbcUtil.executeQuery(); // query 문 실행
			List<CategoryDTO> list = new ArrayList<CategoryDTO>(); // StudentDTO 객체들을 담기위한 list 객체
			while (rs.next()) {
				CategoryDTO dto = new CategoryDTO(); // 하나의 StudentDTO 객체 생성 후 정보 설정
				dto.setCatName(rs.getString("CATEGORY_NAME"));
				dto.setCatID(rs.getInt("catID"));

				list.add(dto); // list 객체에 정보를 설정한 StudentDTO 객체 저장
			}
			return list; // 학생정보를 저장한 dto 들의 목록을 반환
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close(); // ResultSet, PreparedStatement, Connection 반환
		}
		return null;
	}

	public int createUserCategory(String userID, int catID) {
		String sql = "INSERT INTO USERCATEGORYINFO (userID, catID, ucatID, missionClearCNT, giveUp) VALUES (?, ?, SEQ_USERCAT.NEXTVAL, 0, 0)";
		Object[] param = new Object[] { userID, catID };
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

	public int chooseCategory(String userID, int catID) {
		String sql = "UPDATE usercategoryinfo "
				+ "SET catselectedday = to_char(sysdate,'yyyy/mm/dd'), catID = ?, missionClearCNT = 0, giveUP = 0 "
				+ "WHERE userID = ?";

		Object[] param = new Object[] { catID, userID };
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

	// 카테고리를 시작하고 지난 날짜들 12일 이상이면 카테고리 성공
	public int categoryPassedDay(String userID) {
		String sql = "select TRUNC(SYSDATE - catselectedday) AS passedDay FROM (select * from (select * from usercategoryinfo where userID = ? order by catselectedday desc) where rownum = 1)";
		Object[] param = new Object[] { userID };

		jdbcUtil.setSqlAndParameters(sql, param);

		try {
			ResultSet rs = jdbcUtil.executeQuery();

			if (rs.next()) {
				return rs.getInt("passedDay");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return 0;

	}

	// 미션 성공 갯수 리턴
	public int getMissionSuccess(String userID) {
		String sql = "select missionclearcnt FROM (select * from (select * from usercategoryinfo where userID = ? order by catselectedday desc) where rownum = 1)";
		Object[] param = new Object[] { userID };

		jdbcUtil.setSqlAndParameters(sql, param);

		try {
			ResultSet rs = jdbcUtil.executeQuery();

			if (rs.next()) {
				return rs.getInt("missionclearcnt");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return 0;
	}

	// 맨 첫 화면에서 로그인 후 카테고리의 유무
	public int hasUserCategory(String userID) {
		String query = "select * from usercategoryinfo WHERE userID = ?";
		Object[] param = new Object[] { userID };

		jdbcUtil.setSqlAndParameters(query, param);

		try {
			ResultSet rs = jdbcUtil.executeQuery();

			if (rs.next()) {
				return 1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return 0;
	}

	// 카테고리 포기, 성공 여부 리턴
	public int giveUpCategory(String userID) {
		String sql = "UPDATE USERCATEGORYINFO SET giveUp = 1 where ucatID = (select ucatID from (select * from usercategoryinfo where userID = ? order by catselectedday desc) where rownum = 1)";
		Object[] param = new Object[] { userID };

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

	public int isGiveupCategory(String userID) {
		String query = "SELECT giveUp FROM (select * from (select * from usercategoryinfo where userID = ? order by catselectedday desc) where rownum = 1) WHERE userID = ?";
		Object[] param = new Object[] { userID, userID };

		jdbcUtil.setSqlAndParameters(query, param);

		try {
			ResultSet rs = jdbcUtil.executeQuery();

			if (rs.next()) {
				return rs.getInt("giveUp");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return -1;
	}
}