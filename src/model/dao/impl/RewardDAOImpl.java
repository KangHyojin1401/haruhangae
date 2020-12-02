
package model.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.RewardDTO;
import model.dao.RewardDAO;

public class RewardDAOImpl implements RewardDAO {

   private JDBCUtil jdbcUtil = null;

   public RewardDAOImpl() {
      jdbcUtil = new JDBCUtil();
   }

   public List<RewardDTO> getRewardList(String userID) {
      String query = "select r.rewardname, r.rewardContents, r.rewardcondition, ur.achievement, r.iconAddr from reward r JOIN userreward ur ON r.rewardID = ur.rewardID where ur.userID = ?";
      Object[] param = new Object[] { userID };

      jdbcUtil.setSqlAndParameters(query, param);

      try {
         ResultSet rs = jdbcUtil.executeQuery(); // query ¹® ½ÇÇà
         List<RewardDTO> list = new ArrayList<RewardDTO>();
         while (rs.next()) {

            RewardDTO dto = new RewardDTO();

            dto.setName(rs.getString("rewardname"));
            dto.setContent(rs.getString("rewardContents"));
            dto.setCondition(rs.getString("rewardcondition"));
            dto.setAchievement(rs.getString("achievement"));
            dto.setIconAddr(rs.getString("iconAddr"));

            list.add(dto);
         }
         return list;

      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         jdbcUtil.close();
      }
      return null;
   }

   public int earningReward(String userID) {
      String sql = "select missionclearcnt, catID "
            + "FROM (select * "
               + "from (select * from usercategoryinfo where userID = ? order by catselectedday desc) "
               + "where rownum = 1)";
      Object[] param = new Object[] { userID };
      int missionCNT = 0;
      int catID = 0;

      jdbcUtil.setSqlAndParameters(sql, param);

      try {
         ResultSet rs = jdbcUtil.executeQuery();
         if (rs.next()) {
            missionCNT = rs.getInt("missionclearcnt");
            catID = rs.getInt("catID");
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         jdbcUtil.close();
      }

      sql = "select rewardID from reward where catID = ?";
      param = new Object[] { catID };
      int rewardID = 0;

      jdbcUtil.setSqlAndParameters(sql, param);

      try {
         ResultSet rs = jdbcUtil.executeQuery();
         if (rs.next()) {
            rewardID = rs.getInt("rewardID");
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         jdbcUtil.close();
      }

      if (missionCNT >= 12) {
         sql = "INSERT INTO USERREWARD (urewardID, rewardID, userID)\r\n" + "SELECT SEQ_USERREWARD.NEXTVAL, ?, ?\r\n"
               + "FROM DUAL A\r\n" + "WHERE NOT EXISTS ( SELECT 0\r\n" + "                    FROM USERREWARD\r\n"
               + "                    WHERE rewardID = ?\r\n" + "                    AND userID = ?\r\n"
               + "                    )";

         param = new Object[] { rewardID, userID, rewardID, userID };
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
      }

      return 0;
   }
}