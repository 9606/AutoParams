package com.neu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class IniTool {

  static Map<String, Map<String, Object>> iniFile = new LinkedHashMap<>();


  public void write(String name) throws IOException {
    StringBuilder sb = new StringBuilder("");
    for (String section : iniFile.keySet()) {
      sb.append("[").append(section).append("]").append("\n");
      Map<String, Object> map = iniFile.get(section);
      Set<String> keySet = map.keySet();
      for (String key : keySet) {
        sb.append(key).append("=").append(map.get(key)).append("\n");
      }
    }
    File file = new File(name);
    if (!file.exists()) {
      file.createNewFile();
    }
    try {
      OutputStream os = new FileOutputStream(file);
      os.write(sb.toString().getBytes());
      os.flush();
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setValue(String section, String key, Object value) {
    Map<String, Object> sectionMap = iniFile.get(section);
    if (sectionMap == null) {
      sectionMap = new LinkedHashMap<>();
      iniFile.put(section, sectionMap);
    }
    sectionMap.put(key, value);
  }

  public Object getValue(String section, String key) {
    Object obj = null;
    Map<String, Object> item = iniFile.get(section);
    if (item != null) {
      obj = item.get(key);
    }
    return obj;

  }


}