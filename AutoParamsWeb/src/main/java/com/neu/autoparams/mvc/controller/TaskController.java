package com.neu.autoparams.mvc.controller;

import com.neu.autoparams.mvc.entity.SubmitParams;
import com.neu.autoparams.mvc.entity.Task;
import com.neu.autoparams.mvc.entity.TaskDetail;
import com.neu.autoparams.mvc.service.TaskService;
import com.neu.autoparams.util.GlobalResponseCode;
import com.neu.autoparams.util.RestResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class TaskController {
    private static Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Resource
    TaskService taskService;



    /**
     * 获取任务离线期间消息
     *
     * @return
     */
    @RequestMapping(value = "/api/task/submit/task/detail/offline", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getOfflineTaskDetail(HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getSession(false).getAttribute("userId");
            Integer taskId = Integer.valueOf(request.getParameter("taskId"));
            taskService.getOfflineTaskDetail(userId, taskId);
            return RestResponse.create(GlobalResponseCode.SUCCESS).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    /**
     * 获取所有算法的类别
     *
     * @return
     */
    @RequestMapping(value = "/api/task/submit/running/task", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getRunningTaskId(HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getSession(false).getAttribute("userId");
            int taskId = taskService.getRunningTaskId(userId);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(taskId).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }


    /**
     * 获取所有算法的类别
     *
     * @return
     */
    @RequestMapping(value = "/api/task/submit/start", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> startTask(@RequestBody JSONObject submitTaskParam, HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getSession(false).getAttribute("userId");
            int taskId = taskService.startTask(submitTaskParam, userId);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(taskId).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    /**
     * 获取所某个用户所有历史任务
     *
     * @return
     */
    @RequestMapping(value = "/api/task/manage/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAlarmList(HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getSession(false).getAttribute("userId");
            List<Task> alarmList = taskService.getUserHistoryTask(userId);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(alarmList).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    /**
     * 根据任务id查询任务详细输出
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/api/task/manage/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getTaskDetailByTaskId(@PathVariable(name = "id") int taskId) {
        try {
            List<TaskDetail> taskDetailList = taskService.getTaskDetail(taskId);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(taskDetailList).build();
        } catch (Exception e) {
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    /**
     * 删除任务信息和任务详细信息
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/api/task/manage/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteTask(@PathVariable(name = "id") int taskId) {
        try {
            List<Integer> deleteResult = taskService.deleteTask(taskId);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(deleteResult).build();
        } catch (Exception e) {
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    /**
     * 批量删除任务信息和任务详细信息
     *
     * @param taskIds
     * @return
     */
    @RequestMapping(value = "/api/task/manage/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> batchDeleteTask(@RequestBody List<Integer> taskIds) {
        try {
            List<Integer> deleteResult = taskService.batchDeleteTask(taskIds);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(deleteResult).build();
        } catch (Exception e) {
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    /**
     * 重新运行任务
     *
     * @return
     */
    @RequestMapping(value = "/api/task/manage/restart/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> restartTask(@PathVariable(name = "id") int taskId) {
        try {
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData("/task/submit/restart/" + taskId).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    /**
     * 重新运行任务
     *
     */
    @RequestMapping(value = "/task/submit/restart/{id}", method = RequestMethod.GET)
    public ModelAndView handleRequestInternal(@PathVariable(name = "id") int taskId)throws Exception{

        ModelMap model = new ModelMap();
        model.addAttribute("isRestart", true);
        model.addAttribute("taskId", taskId);
        return new ModelAndView("submit_task", model);
    }

    /**
     * 重新运行任务
     *
     */
    @RequestMapping(value = "/api/task/submit/params/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSubmitParams(@PathVariable(name = "id") int taskId) {
        try {
            SubmitParams submitParams = taskService.getSubmitParams(taskId);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(submitParams).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }
}
