package com.neu.autoparams.mvc.controller;

import com.neu.autoparams.mvc.entity.Algorithm;
import com.neu.autoparams.mvc.entity.OptAlgorithm;
import com.neu.autoparams.mvc.service.AlgorithmService;
import com.neu.autoparams.util.GlobalResponseCode;
import com.neu.autoparams.util.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;


@Controller
public class AlgorithmController {
    private static Logger logger = LoggerFactory.getLogger(AlgorithmController.class);

    @Resource
    AlgorithmService algorithmService;

    /**
     * 获取所有算法库
     *
     * @return
     */
    @RequestMapping(value = "/api/task/submit/algo/lib", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAlgoLib(HttpServletRequest request) {
        try {
            List<String> algoLibList = algorithmService.getAlgoLibList();
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(algoLibList).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    /**
     * 获取所有算法类型
     *
     * @return
     */
    @RequestMapping(value = "/api/task/submit/algo/lib/type", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAlgoType(HttpServletRequest request) {
        try {
            String algoLib = request.getParameter("algoLib");
            List<String> algoTypeList = algorithmService.getAlgoTypeList(algoLib);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(algoTypeList).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }
    /**
     * 获取所有某一类别下的所有算法
     *
     * @return
     */
    @RequestMapping(value = "/api/task/submit/algo/lib/type/names", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAlgoNameList(HttpServletRequest request) {
        try {
            String algoLib = request.getParameter("algoLib");
            String algoType = request.getParameter("algoType");
            List<Algorithm> algoNameList = algorithmService.getAlgoNameList(algoLib, algoType);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(algoNameList).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    /**
     * 获取所有某类算法的所有评估类型
     *
     * @return
     */
    @RequestMapping(value = "/api/task/submit/algo/evaType", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAlgoEvaTypeList(HttpServletRequest request) {
        try {
            String algoLib = request.getParameter("algoLib");
            String algoType = request.getParameter("algoType");
            List<String> evaTypeList = algorithmService.getEvaTypeList(algoLib, algoType);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(evaTypeList).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    /**
     * 获取某个算法的所有参数
     *
     * @return
     */
    @RequestMapping(value = "/api/task/submit/algo/params", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAlgoParams(HttpServletRequest request) {
        try {
            Integer algoId = Integer.valueOf(request.getParameter("algoId"));
            String algoParams = algorithmService.getAlgoParams(algoId);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(algoParams).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    /**
     * 获取所有优化算法的类别
     *
     * @return
     */
    @RequestMapping(value = "/api/task/submit/opt/algo/names", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getOptAlgoType() {
        try {
            List<OptAlgorithm> optAlgoNameList = algorithmService.getOptAlgoNameList();
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(optAlgoNameList).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    /**
     * 获取某个优化算法的所有参数
     *
     * @return
     */
    @RequestMapping(value = "/api/task/submit/opt/algo/params", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getOptAlgoParams(HttpServletRequest request) {
        try {
            Integer optAlgoId = Integer.valueOf(request.getParameter("optAlgoId"));
            String optAlgoParams = algorithmService.getOptAlgoParams(optAlgoId);
            return RestResponse.create(GlobalResponseCode.SUCCESS).putData(optAlgoParams).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.create(GlobalResponseCode.ERROR).build();
        }
    }

    @RequestMapping(value = {"/api/task/submit/download/model", "/api/task/manage/download/model"}, method = RequestMethod.POST)
    public Map<String, Object> downloadFile(HttpServletRequest request, HttpServletResponse response) {

        String modelPath = request.getParameter("modelPath");
        File modelFile = new File(modelPath);
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(modelFile));
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + modelFile.getName());
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


}
