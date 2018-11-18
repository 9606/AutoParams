package com.neu.autoparams.mvc.controller;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neu.autoparams.mvc.model.FileMeta;
import com.neu.autoparams.mvc.service.FileService;
import com.neu.autoparams.util.ConfigurationUtil;
import com.neu.autoparams.util.GlobalResponseCode;
import com.neu.autoparams.util.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Controller
public class FileController {
    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Resource
    FileService fileService;
    
    /***************************************************
     * URL: /api/file/upload/train, /api/task/submit/upload/train
     * @param request : MultipartHttpServletRequest auto passed
     * @return FileMeta as json format
     ****************************************************/
    @RequestMapping(value={"/api/file/upload/train", "/api/task/submit/upload/train"}, method = RequestMethod.POST)
    public @ResponseBody FileMeta uploadFile(MultipartHttpServletRequest request) {
        return fileService.uploadFile(request);
    }

    /**
     * 获取某个用户的上传文件历史记录
     *
     * @return
     */
    @RequestMapping(value = "/api/task/submit/file/history", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getFileHistory(HttpServletRequest httpServletRequest, HttpServletResponse response){
        try {
            Integer userID = (Integer) httpServletRequest.getSession(false).getAttribute("userId");
            List<FileMeta> fileList = fileService.getUserHistoryFileList(userID);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(fileList).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    /**
     * 查询文件列表
     *
     * @return
     */
    @RequestMapping(value = "/api/file/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getFileManageList(HttpServletRequest request) {
        try {
            Integer userId = (Integer) request.getSession(false).getAttribute("userId");
            List<FileMeta> fileList = fileService.getFileList(userId);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(fileList).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    /**
     * 查询某个文件的详细信息
     *
     * @return
     */
    @RequestMapping(value = {"/api/file/detail/{id}", "/api/task/manage/file/detail/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getFileManageList(@PathVariable(name = "id") int fileId) {
        try {
            FileMeta fileMeta = fileService.getFileInfo(fileId);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(fileMeta).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    /**
     * 更新文件信息
     *
     * @return
     */
    @RequestMapping(value = "/api/file/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateFileInfo(@RequestBody FileMeta fileMeta) {
        try {
            int result = fileService.updateFileInfo(fileMeta);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(result).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR_Exception_ParseException).build();
        }
    }

    /**
     * 文件下载
     */
    @RequestMapping(value = {"/api/file/download", "/api/task/manage/download/file"}, method = RequestMethod.POST)
    public Map<String, Object> downloadFile(HttpServletRequest request, HttpServletResponse response) {

        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("filePath");
        File file = new File(filePath);
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentLength(buffer.length);

            response.getOutputStream().write(buffer);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
        return RestResponse.create(GlobalResponseCode.SUCCESS).build();
    }

    /**
     * 删除文件
     *
     * @param fileId
     * @return
     */
    @RequestMapping(value = "/api/file/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteTask(@PathVariable(name = "id") int fileId) {
        try {
            int deleteResult = fileService.deleteFile(fileId);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(deleteResult).build();
        } catch (Exception e) {
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    /**
     * 批量删除文件
     *
     * @param fileIds
     * @return
     */
    @RequestMapping(value = "/api/file/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> batchDeleteFile(@RequestBody List<Integer> fileIds) {
        try {
            int deleteResult = fileService.batchDeleteFile(fileIds);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(deleteResult).build();
        } catch (Exception e) {
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }
}