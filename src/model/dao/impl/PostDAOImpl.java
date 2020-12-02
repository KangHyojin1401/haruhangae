package model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.MissionDTO;
import model.PostDTO;
import model.dao.DAOFactory;
import model.dao.MissionDAO;
import model.dao.PostDAO;

public class PostDAOImpl implements PostDAO {

	private JDBCUtil jdbcUtil = null;

	public PostDAOImpl() {
		jdbcUtil = new JDBCUtil();
	}

	@Override
	public List<PostDTO> findPostList(String userID, String date) {
		String query = "SELECT p.postID AS postID, u.alias AS alias, p.location AS location, p.rating AS rating, "
				+ "TO_CHAR(p.pdate, 'YYYY\"년\" MM\"월\" DD\"일\" HH24\"시\" mm\"분\" ss\"초\"') AS pdate, "
				+ "p.content AS content, p.isPrivate AS isPrivate, p.photoAddr AS photoAddr, "
				+ "m.missionContent AS missionContent, t.tagName AS tagName "

				+ "FROM member u, post p, mission m, postTag pt, tag t "

				+ "WHERE u.userID = p.userID " + "AND m.missionID = p.missionID AND m.catid = p.catid "
				+ "AND pt.tagID = t.tagID(+) AND pt.postID(+) = p.postID " // Tag가 없는 Post도 포함하여 검색하기 위해 outer join

				+ "AND p.isPrivate != 1 AND NOT p.userID = ? AND TO_CHAR(p.pdate, 'YYYYMMDD') = ? "
				+ "AND p.catid = ? AND p.missionid = ?";

		DAOFactory factory = new DAOFactory();
		MissionDAO missionDAO = factory.getMissionDAO();
		MissionDTO mission = missionDAO.getTodayMission(userID);
		int catID = mission.getCatID();
		int missionID = mission.getMissionID();

		Object[] param = new Object[] { userID, date, catID, missionID };
		jdbcUtil.setSqlAndParameters(query, param);

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			List<PostDTO> list = new ArrayList<PostDTO>();
			PostDTO post = null;
			ArrayList<String> tags = null;

			int prevPID = -1;
			int flag = 0;

			while (rs.next()) {
				if (prevPID != rs.getInt("postID")) {
					if (flag == 1) {
						post.setTags(tags);
						list.add(post); // postID가 두 번째로 바뀔 때부터 적용
					}

					post = new PostDTO();
					tags = new ArrayList<String>();

					post.setAlias(rs.getString("alias"));
					post.setLocation(rs.getString("location"));
					post.setRating(rs.getInt("rating"));
					post.setDate(rs.getString("pdate"));
					post.setContent(rs.getString("content"));
					post.setIsPrivate(rs.getInt("isPrivate"));
					post.setPhotoAddr(rs.getString("photoAddr"));
					post.setMissionContent(rs.getString("missionContent"));

					tags.add(rs.getString("tagName"));
				} else {
					tags.add(rs.getString("tagName"));
				}
				prevPID = rs.getInt("postID");
				flag = 1;
			}

			if (post != null) {
				post.setTags(tags);
				list.add(post); // 맨 마지막 포스트DTO
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return null;
	}

	@Override
	public PostDTO findMyPost(String userID, String date) {

		String query = "SELECT p.postID AS postID, u.alias AS alias, p.location AS location, p.rating AS rating, "
				+ "TO_CHAR(p.pdate, 'YYYY\"년\" MM\"월\" DD\"일\" HH24\"시\" mm\"분\" ss\"초\"') AS pdate, "
				+ "p.content AS content, p.isPrivate AS isPrivate, p.photoAddr AS photoAddr, "
				+ "m.missionContent AS missionContent, t.tagName AS tagName "

				+ "FROM member u, post p, mission m, postTag pt, tag t "

				+ "WHERE u.userID = p.userID " + "AND m.missionID = p.missionID AND m.catID = p.catID "
				+ "AND pt.tagID = t.tagID(+) AND pt.postID(+) = p.postID " // Tag가 없는 Post도 포함하여 검색하기 위해 outer join

				+ "AND p.userID = ? AND TO_CHAR(p.pdate, 'YYYYMMDD') = ? ";

		Object[] param = new Object[] { userID, date };
		jdbcUtil.setSqlAndParameters(query, param);

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			PostDTO post = null;
			ArrayList<String> tags = new ArrayList<String>();

			int prevPID = -1;

			while (rs.next()) {
				if (prevPID != rs.getInt("postID")) {
					post = new PostDTO();

					post.setPostID(rs.getInt("postID"));
					post.setAlias(rs.getString("alias"));
					post.setLocation(rs.getString("location"));
					post.setRating(rs.getInt("rating"));
					post.setDate(rs.getString("pdate"));
					post.setContent(rs.getString("content"));
					post.setIsPrivate(rs.getInt("isPrivate"));
					post.setPhotoAddr(rs.getString("photoAddr"));
					post.setMissionContent(rs.getString("missionContent"));

					tags.add(rs.getString("tagName"));
				} else {
					tags.add(rs.getString("tagName"));
				}
				prevPID = rs.getInt("postID");
			}

			if (post != null) {
				post.setTags(tags);
			}

			return post;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return null;
	}

	@Override
	public List<PostDTO> SearchPostListbyContents(MissionDTO mission, String content) {
		String query = "SELECT u.alias AS alias, p.postID AS postID, p.location AS location, p.rating AS rating, "
				+ "p.content AS content, TO_CHAR(p.pdate, 'YYYYMMDD') AS pdate, p.isPrivate AS isPrivate, "
				+ "p.photoAddr AS photoAddr, m.missionContent AS missionContent, t.tagName AS tagName "

				+ "FROM post p, member u, mission m, postTag pt, tag t "

				+ "WHERE u.userID = p.userID AND m.missionID = p.missionID "
				+ "AND pt.tagID = t.tagID(+) AND pt.postID(+) = p.postID " + "AND p.catid = m.catid "

				+ "AND p.isPrivate != 1 AND p.content LIKE ? AND p.catid = ? AND p.missionid = ?";

		String con = "%" + content + "%";

		Object[] param = new Object[] { con, mission.getCatID(), mission.getMissionID() };
		jdbcUtil.setSqlAndParameters(query, param);

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			List<PostDTO> list = new ArrayList<PostDTO>();
			PostDTO post = null;
			ArrayList<String> tags = null;

			int prevPID = -1;
			int flag = 0;

			while (rs.next()) {
				if (prevPID != rs.getInt("postID")) {

					if (flag == 1) { // rs의 postID가 처음으로 바뀔 때부터
						if (post != null) {
							post.setTags(tags);
							list.add(post);
						}
					}

					post = new PostDTO();
					tags = new ArrayList<String>();

					post.setAlias(rs.getString("alias"));
					post.setLocation(rs.getString("location"));
					post.setRating(rs.getInt("rating"));
					post.setContent(rs.getString("content"));
					post.setDate(rs.getString("pdate"));
					post.setIsPrivate(rs.getInt("isPrivate"));
					post.setPhotoAddr(rs.getString("photoAddr"));
					post.setMissionContent(rs.getString("missionContent"));

					tags.add(rs.getString("tagName"));
				} else {
					tags.add(rs.getString("tagName"));
				}
				prevPID = rs.getInt("postID");
				flag = 1;
			}

			if (post != null) {
				post.setTags(tags);
				list.add(post);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return null;
	}

	@Override
	public List<PostDTO> SearchPostListbyTag(MissionDTO mission, String tag) {
		String query = "SELECT u.alias AS alias, p.postID AS postID, p.location AS location, p.rating AS rating, "
				+ "p.content AS content, TO_CHAR(p.pdate, 'YYYYMMDD') AS pdate, p.isPrivate AS isPrivate, "
				+ "p.photoAddr AS photoAddr, m.missionContent AS missionContent, t.tagName AS tagName "

				+ "FROM post p, member u, mission m, postTag pt, tag t "

				+ "WHERE u.userID = p.userID AND m.missionID = p.missionID "
				+ "AND pt.tagID = t.tagID AND pt.postID = p.postID " + "AND p.catid = m.catid "

				+ "AND p.isPrivate != 1 AND t.tagName = ? AND p.catid = ? AND p.missionid = ?";

		Object[] param = new Object[] { tag, mission.getCatID(), mission.getMissionID() };

		jdbcUtil.setSqlAndParameters(query, param);

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			List<PostDTO> list = new ArrayList<PostDTO>();
			PostDTO post = null;
			ArrayList<String> tags = null;

			int prevPID = -1;
			int flag = 0;

			while (rs.next()) {
				if (prevPID != rs.getInt("postID")) {
					if (flag == 1) { // rs의 postID가 처음으로 바뀔 때부터
						if (post != null) {
							post.setTags(tags);
							list.add(post);
						}
					}

					post = new PostDTO();
					tags = new ArrayList<String>();

					post.setAlias(rs.getString("alias"));
					post.setLocation(rs.getString("location"));
					post.setRating(rs.getInt("rating"));
					post.setDate(rs.getString("pdate"));
					post.setContent(rs.getString("content"));
					post.setIsPrivate(rs.getInt("isPrivate"));
					post.setPhotoAddr(rs.getString("photoAddr"));
					post.setMissionContent(rs.getString("missionContent"));

					tags.add(rs.getString("tagName"));
				} else {
					tags.add(rs.getString("tagName"));
				}
				prevPID = rs.getInt("postID");
				flag = 1;
			}

			if (post != null) {
				post.setTags(tags);
				list.add(post);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return null;
	}

	@Override
	public int createPost(PostDTO post) {
		int result = 0;

		// post추가
		String insertQuery = "INSERT INTO post(postID, location, rating, pdate, content, isPrivate, photoAddr, userID, missionID, catID) "
				+ "VALUES (seq_post.nextval, ?, ?, sysdate, ?, ?, ?, ?, ?, ?)";

		DAOFactory factory = new DAOFactory();
		MissionDAO missionDAO = factory.getMissionDAO();
		MissionDTO mission = missionDAO.getTodayMission(post.getUserID());
		int catID = mission.getCatID();
		int missionID = mission.getMissionID();

		Object[] param = new Object[] { post.getLocation(), post.getRating(), post.getContent(), post.getIsPrivate(),
				post.getPhotoAddr(), post.getUserID(), missionID, catID };
		jdbcUtil.setSqlAndParameters(insertQuery, param);

		try {
			result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		String selectQuery = "SELECT uci.missionclearcnt " + "FROM usercategoryinfo uci " + "WHERE ucatID = "
				+ "(select ucatID "
				+ "from (select * from usercategoryinfo where userid = ? order by catselectedday desc) "
				+ "where rownum = 1)";
		param = new Object[] { post.getUserID() };
		int missionClearCnt = 10;

		jdbcUtil.setSqlAndParameters(selectQuery, param);

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			if (rs.next()) {
				missionClearCnt = rs.getInt("MISSIONCLEARCNT");
				System.out.println(missionClearCnt);
			}
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}

		String updateQuery = "UPDATE USERCATEGORYINFO " + "SET missionClearcnt = ? " + "where ucatID = "
				+ "(select ucatID " + "from (select * from usercategoryinfo order by catselectedday desc) "
				+ "where rownum = 1)";
		missionClearCnt++;
		param = new Object[] { missionClearCnt };
		System.out.println(missionClearCnt);

		jdbcUtil.setSqlAndParameters(updateQuery, param);

		try {
			jdbcUtil.executeUpdate();
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}

		return result;
	}

	public int insertTag(String tagName) {

		int result = 0;

		// 이미 존재하면 0, 존재하지 않으면 1 반환
		String insertQuery = "INSERT INTO tag(tagID, tagName) " + "VALUES (seq_tag.nextval, ?)";
		Object[] param = new Object[] { tagName };
		jdbcUtil.setSqlAndParameters(insertQuery, param);

		try {
			result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return result;
	}

	public int insertPostTag(int postID, String tagName) {
		// 먼저 insertTag 수행하고 수행.
		int result = 0;

		String insertQuery = "INSERT INTO postTag(ptagID, tagID, postID) "
				+ "VALUES (seq_tag.nextval, (SELECT tagID FROM tag WHERE tagName = ?), ?)"; // seq가 퐁당퐁당으로 추가될 수 밖에 없음

		Object[] param = new Object[] { tagName, postID };
		jdbcUtil.setSqlAndParameters(insertQuery, param);

		try {
			result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return result;
	}
	
	public int deletePostTag(int postID) {
		// 먼저 insertTag 수행하고 수행.
		// postID를 이용해 기존의 태그들을 모두 지우고 새로 insert
		int result = 0;

		String deleteQuery = "DELETE postTag WHERE postID = ?";

		Object[] param = new Object[] { postID };
		jdbcUtil.setSqlAndParameters(deleteQuery, param);

		try {
			result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		
		return result;
	}
	
	public int updatePostTag(int postID, String tagName) {
		// 먼저 insertTag 수행하고 수행.
		// deletePostTag 수행 후 새로 insert
		int result = 0;

		String insertQuery = "INSERT INTO postTag(ptagID, tagID, postID) "
				+ "VALUES (seq_tag.nextval, (SELECT tagID FROM tag WHERE tagName = ?), ?)"; 

		Object[] param = new Object[] { tagName, postID };
		jdbcUtil.setSqlAndParameters(insertQuery, param);

		try {
			result += jdbcUtil.executeUpdate();
			jdbcUtil.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return result;
	}

	@Override
	public int updatePost(PostDTO post) {
		System.out.println(post.getPostID());
		int result = 0;
		String updateQuery = "UPDATE post "
						+ "SET location = ?, rating = ?, content = ?, isPrivate = ?, photoAddr = ? "
						+ "WHERE postID = ?";

		Object[] param = new Object[] { post.getLocation(), (int)post.getRating(), post.getContent(), (int)post.getIsPrivate(),
				post.getPhotoAddr(), (int)post.getPostID() };
		jdbcUtil.setSqlAndParameters(updateQuery, param);

		try {
			result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return result;
	}

	@Override
	public List<PostDTO> SearchPostListbyContentsAndTag(MissionDTO mission, String content, String tag) {
		String query = "SELECT u.alias AS alias, p.postID AS postID, p.location AS location, p.rating AS rating, "
				+ "p.content AS content, TO_CHAR(p.pdate, 'YYYYMMDD') AS pdate, p.isPrivate AS isPrivate, "
				+ "p.photoAddr AS photoAddr, m.missionContent AS missionContent, t.tagName AS tagName "

				+ "FROM post p, member u, mission m, postTag pt, tag t "

				+ "WHERE u.userID = p.userID AND m.missionID = p.missionID "
				+ "AND pt.tagID = t.tagID AND pt.postID = p.postID " + "AND p.catid = m.catid "

				+ "AND p.isPrivate != 1 AND p.content LIKE ? AND t.tagName = ? AND p.catid = ? AND p.missionid = ?";

		String con = "%" + content + "%";

		Object[] param = new Object[] { con, tag, mission.getCatID(), mission.getMissionID() };

		jdbcUtil.setSqlAndParameters(query, param);

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			List<PostDTO> list = new ArrayList<PostDTO>();
			PostDTO post = null;
			ArrayList<String> tags = null;

			int prevPID = -1;
			int flag = 0;

			while (rs.next()) {
				if (prevPID != rs.getInt("postID")) {
					if (flag == 1) {
						if (post != null) {
							post.setTags(tags);
							list.add(post);
						}
					}

					post = new PostDTO();
					tags = new ArrayList<String>();

					post.setAlias(rs.getString("alias"));
					post.setLocation(rs.getString("location"));
					post.setRating(rs.getInt("rating"));
					post.setDate(rs.getString("pdate"));
					post.setContent(rs.getString("content"));
					post.setIsPrivate(rs.getInt("isPrivate"));
					post.setPhotoAddr(rs.getString("photoAddr"));
					post.setMissionContent(rs.getString("missionContent"));

					tags.add(rs.getString("tagName"));
				} else {
					tags.add(rs.getString("tagName"));
				}
				prevPID = rs.getInt("postID");
				flag = 1;
			}

			if (post != null) {
				post.setTags(tags);
				list.add(post);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtil.close();
		}

		return null;
	}

}