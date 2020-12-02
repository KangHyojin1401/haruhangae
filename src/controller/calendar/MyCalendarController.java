package controller.calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.PostDTO;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.service.PostNotFoundException;

// 달력의 날짜를 클릭해 그 날의 게시물을 열람
public class MyCalendarController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		DAOFactory factory = new DAOFactory();
		PostDAO postDAO = factory.getPostDAO();

		String userID = (String) request.getSession().getAttribute("userID");
		String year = request.getParameter("year");
		String month = Integer.toString(Integer.parseInt(request.getParameter("month")) + 1);
		String day = request.getParameter("day");
		day = day.length() == 1 ? "0" + day : day;
		String date = year + month + day;

		try {
			PostDTO myPost = postDAO.findMyPost(userID, date);

			if (myPost == null) {
				request.setAttribute("exception", new PostNotFoundException(date + "에는 인증글을 올리지 않았어요."));
			}

			request.setAttribute("myPost", myPost);
			request.setAttribute("day", day);
			request.setAttribute("selectedDate", date);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/calendarPage.jsp";
	}

}