package www.raven.sw.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import www.raven.sw.entity.PageVO;
import www.raven.sw.entity.po.Chats;
import www.raven.sw.entity.po.Sessions;
import www.raven.sw.result.Result;
import www.raven.sw.service.ChatsService;
import www.raven.sw.service.SessionsService;

/**
 * 聊天管理
 *
 * @author Rawven
 * @date 2024/11/07
 */
@RestController
@RequestMapping("api/chat/")
@ResponseBody
public class ChatController {
	@Resource
	private ChatsService chatsService;
	@Resource
	private SessionsService sessionService;

	/**
	 * 获取用户的会话列表记录
	 *
	 * @param page 
	 * @param size 
	 */
	@GetMapping("/sessions")
	public Result<PageVO<Sessions>> getSessions(@RequestParam Integer page, @RequestParam Integer size) {
		return Result.operateSuccess("查询成功", sessionService.getSessions(page, size));
	}

	/**
	 * 发起会话
	 *
	 * @param toUserId 对方用户ID
	 * @return {@link Result }<{@link Sessions }>
	 */
	@PostMapping("/start")
	public Result<Sessions> startChat(@RequestParam Integer toUserId) {
		return Result.operateSuccess("发起会话成功", sessionService.startChat(toUserId));
	}

	/**
	 * 获取历史聊天记录
	 *
	 * @param sessionId 会话ID
	 * @param page      页码
	 * @param size      页大小
	 */
	@GetMapping("/history")
	public Result<PageVO<Chats>> historyChat(
			@RequestParam Long sessionId,
			@RequestParam Integer page,
			@RequestParam Integer size) {
		return Result.operateSuccess("查询成功", chatsService.historyChat(sessionId, page, size));
	}

}
