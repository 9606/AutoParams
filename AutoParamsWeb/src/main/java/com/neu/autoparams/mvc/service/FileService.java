package com.neu.autoparams.mvc.service;

import com.neu.autoparams.mvc.dao.FileDao;
import com.neu.autoparams.mvc.model.FileMeta;
import com.neu.autoparams.util.ConfigurationUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {


    @Resource
    FileDao fileDao;

    public int insertFile(Integer userId, FileMeta fileMeta) {
         return fileDao.insertFile(userId, fileMeta);
    }

    public FileMeta uploadFile(MultipartHttpServletRequest request){
        String fileType = request.getParameter("fileType");
        Boolean colName = Boolean.valueOf(request.getParameter("colName"));
        Boolean cluLabel = Boolean.valueOf(request.getParameter("cluLabel"));
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf;
        FileMeta fileMeta = null;

        while(itr.hasNext()){

            mpf = request.getFile(itr.next());
            System.out.println(mpf.getOriginalFilename() +" uploaded! "+mpf.getSize());


            fileMeta = new FileMeta();
            fileMeta.setOriginName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
            // fileMeta.setFileType(mpf.getContentType());
            fileMeta.setFileType(fileType);
            fileMeta.setColName(colName);
            fileMeta.setCluLabel(cluLabel);
            fileMeta.setUploadTime(new java.util.Date().getTime());

            try {
                fileMeta.setBytes(mpf.getBytes());
                File dir = new File(ConfigurationUtil.INSTANCE.getFilePath());
                if(!dir.exists()){
                    dir.mkdirs();
                }
                fileMeta.setFilePath(dir.getAbsolutePath() + "/" + UUID.randomUUID().toString());

                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(fileMeta.getFilePath()));

                int userId = (Integer) request.getSession().getAttribute("userId");
                int insertResult = fileDao.insertFile(userId, fileMeta);
                if (insertResult < 1){
                    fileMeta = null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                fileMeta = null;
            }
        }

        return fileMeta;
    }

    public List<FileMeta> getUserHistoryFileList(Integer userId) {
        return fileDao.getUserHistoryFileList(userId);
    }

    public FileMeta getFileInfo(Integer fileId) {
        return fileDao.getFileInfo(fileId);
    }
    public int updateFileInfo(FileMeta fileMeta) {
        return fileDao.updateFileInfo(fileMeta);
    }

    public List<FileMeta> getFileList(Integer userId) {
        return fileDao.getFileList(userId);
    }

    public int deleteFile(Integer fileId){
        return fileDao.deleteFile(fileId);
    }

    public int batchDeleteFile(List<Integer> fileIds){
        return fileDao.batchDeleteFile(fileIds);
    }
}
