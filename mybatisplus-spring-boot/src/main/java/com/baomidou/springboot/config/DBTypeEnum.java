package com.baomidou.springboot.config;

public enum DBTypeEnum {
	one("datasource1"), two("datasource2");
	private String value;

	DBTypeEnum(String value) {
	    this.value = value;
	}

	public String getValue() {
	    return value;
	}
}
