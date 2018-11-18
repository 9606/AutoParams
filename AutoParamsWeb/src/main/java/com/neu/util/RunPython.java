package com.neu.util;

import org.ini4j.Ini;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class RunPython {
    public static void main(String[] args){
        try{
            String algor_label = "21001";
            String iniPath = "/Users/enbo/IdeaProjects/AutoParams/AutoParamsWeb/src/main/resources/algor_params.ini";
            String file_path = "/Users/enbo/IdeaProjects/AutoParams/AutoSklearn/src/data/classfication/adult.csv";
            Ini ini = new Ini(new File(iniPath));
            Ini.Section params = ini.get("params");
            Ini.Section algor = ini.get("algor");
            String algor_params_str = params.get("automodel.params." + algor_label);
            String algorithm_type = algor.get("automodel.algor." + algor_label).split(":")[0];
            String other_params = "{\"data_format\":\"csv\",\"is_labeled\":\"False\",\"na_symbol\":[\"NA\",\"?\"]}";
            String allParams = algorithm_type + " " + file_path + " "  + algor_params_str + " "  + other_params;

            System.out.println("start");
            Process pr = Runtime.getRuntime().exec("python /Users/enbo/IdeaProjects/AutoParams/AutoSklearn/src/main.py" + " " + allParams);
            BufferedReader errStream = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            while ((line = errStream.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            errStream.close();
            pr.waitFor();
            System.out.println("end");
            // System.out.println("start");
            // Process pr = Runtime.getRuntime().exec("python /Users/enbo/IdeaProjects/AutoParams/AutoSklearn/test/test_algorithm.py");
            //
            // BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            // String line;
            // while ((line = in.readLine()) != null) {
            //     System.out.println(line);
            // }
            // in.close();
            // pr.waitFor();
            // System.out.println("end");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
