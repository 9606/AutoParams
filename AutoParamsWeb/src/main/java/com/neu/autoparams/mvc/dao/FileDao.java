package com.neu.autoparams.mvc.dao;

import com.neu.autoparams.mvc.model.FileMeta;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Service
public class FileDao {
    @Resource
    JdbcTemplate jdbcTemplate;

    private static final String insertFile = "INSERT INTO train_file (u_id, f_path, f_type, col_name, clu_label, origin_name, f_size, upload_time) VALUE (?,?,?,?,?,?,?,?);";
    private static final String getUserHistoryFileList = "SELECT * FROM train_file where u_id=? ORDER BY upload_time DESC ;";
    private static final String getFileInfo = "SELECT * from train_file where f_id=?;";
    private static final String findFileMetaListQuery = "SELECT f.* FROM train_file AS f WHERE f.u_id=?";
    private static final String updateFileInfo = "UPDATE train_file SET f_type=?,col_name=?,clu_label=?,origin_name=? WHERE f_id=?";
    private static final String deleteFile = "DELETE FROM train_file WHERE f_id=?";

    // 用户上传文件之后将文件信息插入数据库
    public int insertFile(Integer userId, FileMeta fileMeta) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertFile, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setString(2, fileMeta.getFilePath());
            ps.setString(3, fileMeta.getFileType());
            ps.setBoolean(4, fileMeta.getColName());
            ps.setBoolean(5, fileMeta.getCluLabel());
            ps.setString(6, fileMeta.getOriginName());
            ps.setString(7, fileMeta.getFileSize());
            ps.setTimestamp(8, new java.sql.Timestamp(fileMeta.getUploadTime()));
            return ps;
        }, keyHolder);

        int generatedId = keyHolder.getKey().intValue();
        fileMeta.setFileId(generatedId);
        return generatedId;

    }

    public List<FileMeta> getUserHistoryFileList(Integer userId) {
        List<FileMeta> historyFileList = jdbcTemplate.query(getUserHistoryFileList, new Object[]{userId}, FileMeta.getRowMapper());
        return historyFileList;
    }
    public FileMeta getFileInfo(Integer fileId) {
        FileMeta fileInfo = jdbcTemplate.queryForObject(getFileInfo, new Object[]{fileId}, FileMeta.getRowMapper());
        return fileInfo;
    }

    public int updateFileInfo(FileMeta fileMeta) {
        return jdbcTemplate.update(updateFileInfo, fileMeta.getFileType(), fileMeta.getColName(), fileMeta.getCluLabel(),fileMeta.getOriginName(), fileMeta.getFileId());
    }

    public List<FileMeta> getFileList(Integer userId) {
        return jdbcTemplate.query(findFileMetaListQuery, new Object[]{userId}, FileMeta.getRowMapper());
    }

    public int deleteFile(Integer fileId){
        FileMeta fileInfo = jdbcTemplate.queryForObject(getFileInfo, new Object[]{fileId}, FileMeta.getRowMapper());
        int deleteNum = 0;
        if (fileInfo != null){
            File file = new File(fileInfo.getFilePath());
            if (file.exists()){
                deleteNum = jdbcTemplate.update(deleteFile, new Object[]{fileId});
                file.delete();
            }else {
                deleteNum = 0;
            }
        }else {
            deleteNum = 0;
        }
        return deleteNum;
    }

    public int batchDeleteFile(List<Integer> fileIds){
        int deleteNum = 0;
        for (Integer fileId : fileIds) {
            FileMeta fileInfo = jdbcTemplate.queryForObject(getFileInfo, new Object[]{fileId}, FileMeta.getRowMapper());
            if (fileInfo != null){
                File file = new File(fileInfo.getFilePath());
                if (file.exists()){
                    deleteNum += jdbcTemplate.update(deleteFile, new Object[]{fileId});
                    file.delete();
                }else {
                    deleteNum += 0;
                }
            }else {
                deleteNum += 0;
            }
        }
        return deleteNum;
    }
}
