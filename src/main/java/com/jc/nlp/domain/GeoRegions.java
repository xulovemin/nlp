package com.jc.nlp.domain;

import com.jc.core.persistence.entity.DataEntity;

import java.util.ArrayList;
import java.util.List;

public class GeoRegions extends DataEntity<GeoRegions> {

	private int code;
	private String name;
	private String startcode;
    private String endcode;
	private List<String> abbrsList = new ArrayList<String>();
	private String abbr;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAbbrsList() {
		return abbrsList;
	}

	public void setAbbrsList(List<String> abbrsList) {
		this.abbrsList = abbrsList;
	}
	
	public String getStartcode() {
		return startcode;
	}

	public void setStartcode(String startcode) {
		this.startcode = startcode;
	}

	public String getEndcode() {
		return endcode;
	}

	public void setEndcode(String endcode) {
		this.endcode = endcode;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
}
