package com.jc.nlp.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FileUtil {

	public static String readFileByLines(String fileName, String name) {
		String result = "";
		try {
			File file = new File(ResourceUtils.getURL(fileName).getFile()
					.toString());
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					file), "UTF-8");
			BufferedReader reader = null;
			reader = new BufferedReader(isr);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				System.out.println(tempString);
				if (tempString.contains(name)) {
					result = tempString;
				}
			}
			reader.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void writeFileByLines(String fileName, String content) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			BufferedWriter writer = null;
			writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(content + "\t\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}