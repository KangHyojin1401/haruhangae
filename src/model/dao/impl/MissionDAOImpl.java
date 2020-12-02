package model.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.CategoryDTO;
import model.MissionDTO;
import model.dao.MissionDAO;

public class MissionDAOImpl implements MissionDAO {

	private JDBCUtil jdbcUtil = null;

	public MissionDAOImpl() {
		jdbcUtil = new JDBCUtil();
	}

	public MissionDTO getTodayMission(String userID) {
		String query = "select uc.catID AS catID, c.todayMissionid AS todayMissionid, c.catName AS catName, m.missionContent AS missionContent "
					+ "from USERCATEGORYINFO uc, CATEGORY c, MISSION m "
					+ "where uc.catID = c.catID AND m.missionid = c.todaymissionid "
					+ "AND c.catID = m.catid  AND uc.userID = ? "
					+ "order by uc.catselectedday desc";

		Object[] param = new Object[] { userID };

		jdbcUtil.setSqlAndParameters(query, param);
		MissionDTO dto = new MissionDTO();

		try {
			ResultSet rs = jdbcUtil.executeQuery();

			if (rs.next()) {
				dto.setCatID(rs.getInt("catID"));
				dto.setMissionID(rs.getInt("todayMissionid"));
				dto.setCatName(rs.getString("catName"));
				dto.setMissionContent(rs.getString("missionContent"));

				return dto;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return null;
	}

	public int countMission(int catID) {
		int count = 0;
		String countQuery = "SELECT COUNT(missionID) FROM MISSION m JOIN CATEGORY c ON m.catID = c.catID WHERE c.catID = ?";
		Object[] param = new Object[] { catID };

		jdbcUtil.setSqlAndParameters(countQuery, param); // JDBCUtil 에 query 설정

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {
				count = rs.getInt("COUNT(missionID)");
			}

			return count;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return -1;
	}

	public List<CategoryDTO> getCategoryList() {
		String searchQuery = "SELECT CATEGORY.catName AS CATEGORY_NAME FROM Category";
		jdbcUtil.setSqlAndParameters(searchQuery, null); // JDBCUtil 에 query 설정

		try {
			ResultSet rs = jdbcUtil.executeQuery(); // query 문 실행
			List<CategoryDTO> list = new ArrayList<CategoryDTO>(); // StudentDTO 객체들을 담기위한 list 객체
			while (rs.next()) {
				CategoryDTO dto = new CategoryDTO(); // 하나의 StudentDTO 객체 생성 후 정보 설정
				dto.setCatName(rs.getString("CATEGORY_NAME"));

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

	// 카테고리 포기, 성공 여부 리턴
	public int giveUpMission(String userID) {
		String sql = "UPDATE USERCATEGORYINFO SET giveUp = 1 where userID = ?";
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

	public int setTodayMission(int catID) { // 카테고리 별로 랜덤으로 미션 지정
		int missionCount = countMission(catID); // 카테고리 별로 미션 갯수를 새오기
		Calendar cal = Calendar.getInstance();

		int day = cal.get(Calendar.DAY_OF_MONTH);

		if (missionCount < day) {
			day = (day % missionCount);
		}

		String sql = "UPDATE Category " + "SET todayMissionID = ? " + "WHERE catID = ?";

		Object[] param = new Object[] { day, catID };
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

}