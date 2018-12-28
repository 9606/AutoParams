package com.neu.autoparams.mvc.dao;

import com.neu.autoparams.mvc.entity.SubmitParams;
import com.neu.autoparams.mvc.entity.Task;
import com.neu.autoparams.mvc.entity.TaskDetail;
import com.neu.autoparams.mvc.entity.TaskStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskDao {
    @Resource
    JdbcTemplate jdbcTemplate;
    private static final String getRunningTaskId = "select t_id from task where u_id = ? and t_status=0;";
    private static final String insertTask = "insert into task (u_id, f_id, t_status, algo_id, algo_params, opt_algo_id, opt_algo_params, eva_type) values (?,?,?,?,?,?,?,?);";
    private static final String updateFullTask = "update task set t_status=?, task_result=?, done_time=? where t_id=?;";
    private static final String insertTaskDetail = "insert into task_detail (task_id, detail, detail_type, detail_time) values (?,?,?,?);";
    // private static final String getUserHistoryTask = "select * from task, train_file, algorithm, opt_algorithm where u_id=? and task.algo_id=algorithm.algo_id and task.opt_algo_id=opt_algorithm.opt_algo_id;";
    private static final String getUserHistoryTask =
            "SELECT t.t_id,t.u_id,t.f_id,origin_name,t_status,t.algo_id,algo_lib,algo_name,algo_params,t.opt_algo_id,opt_algo_name,opt_algo_params,task_result,submit_time,done_time " +
            "FROM task as t, algorithm, opt_algorithm, train_file as tf " +
                    "WHERE t.u_id=? and t.f_id=tf.f_id and " +
                    "t.algo_id=algorithm.algo_id and t.opt_algo_id=opt_algorithm.opt_algo_id;";
    private static final String getUserHistoryTaskDetail = "SELECT * FROM task_detail where task_id=?";
    private static final String deleteTask = "delete from task where t_id = ?;";
    private static final String deleteTaskDetail = "delete from task_detail where task_id = ?;";
    private static final String getSubmitParams =
            "SELECT t_id,algo_params,opt_algo_params,eva_type,tf.*,algorithm.*,opt_algorithm.*" +
                    "FROM task as t, algorithm, opt_algorithm, train_file as tf " +
                    "WHERE t.t_id=? and t.f_id=tf.f_id and " +
                    "t.algo_id=algorithm.algo_id and t.opt_algo_id=opt_algorithm.opt_algo_id;";

    public int insertTask(int userId, int fileId, TaskStatus taskStatus, int algoId, String algoParams, int optAlgoId, String optAlgoParams, String evaType) throws DataAccessException{
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertTask, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setInt(2, fileId);
            ps.setInt(3, taskStatus.ordinal());
            ps.setInt(4, algoId);
            ps.setString(5, algoParams);
            ps.setInt(6, optAlgoId);
            ps.setString(7, optAlgoParams);
            ps.setString(8, evaType);

            return ps;
        }, keyHolder);

        int generatedId = keyHolder.getKey().intValue();
        return generatedId;

    }

    public int getRunningTaskId(int userId) throws DataAccessException{
        List<Integer> taskIds = jdbcTemplate.query(getRunningTaskId, new Object[]{userId}, (rs, rowNum) -> rs.getInt(1));
        return taskIds.size() == 0 ? -1 : taskIds.get(0);

    }

    public int updateFullTask(TaskStatus taskStatus, String taskResult, long doneTime, int taskId) throws DataAccessException {
        return jdbcTemplate.update(updateFullTask, taskStatus.ordinal(), taskResult, new Timestamp(doneTime), taskId);
    }

    public void batchInsertTaskDetail(List<TaskDetail> taskDetailList) throws DataAccessException{

        jdbcTemplate.batchUpdate(insertTaskDetail, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, taskDetailList.get(i).getTaskId());
                preparedStatement.setString(2, taskDetailList.get(i).getDetailText());
                preparedStatement.setInt(3, taskDetailList.get(i).getTaskDetailType().ordinal());
                preparedStatement.setTimestamp(4, new Timestamp(taskDetailList.get(i).getDetailTime()));
            }
            @Override
            public int getBatchSize() {
                return taskDetailList.size();
            }
        });
    }

    public List<Task> getUserHistoryTask(int userId) {
        List<Task> historyTaskList = jdbcTemplate.query(getUserHistoryTask, new Object[]{userId}, Task.getRowMapper());
        return historyTaskList;
    }

    public SubmitParams getSubmitParams(int taskId) {
        List<SubmitParams> submitParamsList = jdbcTemplate.query(getSubmitParams, new Object[]{taskId}, SubmitParams.getRowMapper());
        return submitParamsList.size() == 0 ? null : submitParamsList.get(0);
    }

    public List<TaskDetail> getTaskDetail(int taskId) {
        List<TaskDetail> taskDetailList = jdbcTemplate.query(getUserHistoryTaskDetail, new Object[]{taskId}, TaskDetail.getRowMapper());
        return taskDetailList;
    }

    public List<Integer> deleteTask(int taskId) {
        List<Integer> deleteResult = new ArrayList<>();
        deleteResult.add(jdbcTemplate.update(deleteTask, taskId));
        deleteResult.add(jdbcTemplate.update(deleteTaskDetail, taskId));
        return deleteResult;
    }
    public List<Integer> batchDeleteTask(List<Integer> taskIds){
        List<Integer> deleteResult = new ArrayList<>();
        int taskResult = 0;
        int taskDetailResult = 0;
        for (Integer taskId:taskIds) {
            taskResult += jdbcTemplate.update(deleteTask, taskId);
            taskDetailResult += jdbcTemplate.update(deleteTaskDetail, taskId);
        }
        deleteResult.add(taskResult);
        deleteResult.add(taskDetailResult);
        return deleteResult;
    }
}
