package cn.edu.ycm.union.dto;

public class TaskRow {
	
	//统计名称
	private String key;
	//统计值
	private String value;
	//统计值类型
	private String valueType;
	//统计值类型内容
	private String valueTypeText;
	//统计提示信息
	private String tip;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getValueTypeText() {
		return valueTypeText;
	}
	public void setValueTypeText(String valueTypeText) {
		this.valueTypeText = valueTypeText;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	
}
