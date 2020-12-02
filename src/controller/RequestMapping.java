package controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.calendar.MyCalendarController;
import controller.category.CreateCategoryController;
import controller.category.GiveupCategoryController;
import controller.home.PrintHomeController;
import controller.mission.ViewMissionController;
import controller.post.RegisterPostController;
import controller.post.SearchPostController;
import controller.post.UpdatePostController;
import controller.reward.ListRewardController;
import controller.user.DeleteUserController;
import controller.user.LoginController;
import controller.user.LogoutController;
import controller.user.PrintMyPageController;
import controller.user.RegisterUserController;
import controller.user.UpdateUserController;

public class RequestMapping {
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	// 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
	private Map<String, Controller> mappings = new HashMap<String, Controller>();

	public void initMapping() {
		// 각 uri에 대응되는 controller 객체를 생성 및 저장
		mappings.put("/", new ForwardController("/user/login.jsp"));
		mappings.put("/user/login", new LoginController());
		mappings.put("/user/logout", new LogoutController());
		mappings.put("/user/register", new RegisterUserController());
		mappings.put("/user/update", new UpdateUserController());
		mappings.put("/user/delete", new DeleteUserController());
		mappings.put("/user/myPage", new PrintMyPageController());
		mappings.put("/user/hello", new ForwardController("/user/hello.jsp"));
		mappings.put("/user/goodbye", new ForwardController("/user/goodbye.jsp"));

		mappings.put("/home", new PrintHomeController());
		mappings.put("/createCategory", new CreateCategoryController());

		mappings.put("/post/search", new SearchPostController());
		mappings.put("/post/register", new RegisterPostController());
		mappings.put("/post/update", new UpdatePostController());

		mappings.put("/mission/print", new ViewMissionController());
		mappings.put("/mission/giveUp", new GiveupCategoryController());

		mappings.put("/calendar/list", new MyCalendarController());

		mappings.put("/reward/list", new ListRewardController());

		logger.info("Initialized Request Mapping!");
	}

	public Controller findController(String uri) {
		// 주어진 uri에 대응되는 controller 객체를 찾아 반환
		return mappings.get(uri);
	}
}