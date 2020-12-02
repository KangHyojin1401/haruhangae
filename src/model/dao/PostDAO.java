package model.dao;

import java.util.List;

import model.MissionDTO;
import model.PostDTO;

public interface PostDAO {
	public List<PostDTO> findPostList(String userID, String date);

	public PostDTO findMyPost(String userID, String date);

	public int createPost(PostDTO post);

	public int updatePost(PostDTO post);

	public int insertTag(String tagName);

	public int insertPostTag(int cnt, String tagName);
	
	public int updatePostTag(int postID, String tagName);

	List<PostDTO> SearchPostListbyTag(MissionDTO mission, String tag);

	List<PostDTO> SearchPostListbyContents(MissionDTO mission, String content);

	public List<PostDTO> SearchPostListbyContentsAndTag(MissionDTO mission, String contentKeyword, String tag);

	public int deletePostTag(int postID);
}