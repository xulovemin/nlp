package com.jc.nlp.domain;

import com.jc.core.persistence.entity.DataEntity;

public class AddrCoor extends DataEntity<AddrCoor> {

	private static final long serialVersionUID = 9019850549761698213L;
	
	private String geoname;

	private String city;

	private String lng;

	private String lat;
	
	private String accurateFlag;

	public String getGeoname() {
		return geoname;
	}

	public void setGeoname(String geoname) {
		this.geoname = geoname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getAccurateFlag() {
		return accurateFlag;
	}

	public void setAccurateFlag(String accurateFlag) {
		this.accurateFlag = accurateFlag;
	}

}
