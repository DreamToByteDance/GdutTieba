package www.raven.sw.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import www.raven.sw.entity.PageVO;
import www.raven.sw.entity.po.Chats;
import www.raven.sw.result.Result;
import www.raven.sw.service.ChatsService;

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

	/**
	 * 获取历史聊天记录
	 *
	 * @param toUserId 接收者ID
	 * @param page     页码
	 * @param size     页大小
	 */
	@GetMapping("/history")
	public Result<PageVO<Chats>> historyChat(
			@RequestParam Long toUserId,
			@RequestParam Integer page,
			@RequestParam Integer size) {
		return Result.operateSuccess("查询成功", chatsService.historyChat(toUserId, page, size));
	}

}
