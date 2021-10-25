package com.ddbj.ld.data.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ddbj.ld.common.utility.JsonMapper;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListdataJson {
	
	// 個々のデータ形式は全部 Map<String, String[]> で持つ。（ExcelTool metadata 形式）

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PersonData {
		/** person.id */
		private String id;
		/** person.属性値 */
		Map<String, String[]> data = new HashMap<>();	
	}
	
	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ProjectPersons {
		/** 作成者 */
		private PersonData creator = new PersonData();
		/** 連絡窓口 */
		private PersonData contact = new PersonData();
		/** 研究責任者 */
		private PersonData principalInvestigator = new PersonData();
		/** 投稿者 */
		private PersonData submitter = new PersonData();
		
		
		// TODO：場合によって、これ自体を更にMapに包んで、どの人物項目のデータを抜いてくるか外部定義化する事も検討。
		// TODO：人物シートからどの項目を抜いてくるかも外部定義化を検討。
	}
	
	/** title, title_ja, title_xx のシリーズ（titleの前方一致を全部抜く） */
	private Map<String, String[]> titles;
	
	/** @see ProjectPersons */
	private ProjectPersons persons;
	
	// ctor
	
	public ListdataJson() {
		this.titles = new HashMap<>();
		this.persons = new ProjectPersons();
	}
	public ListdataJson( Map<String, String[]> titles, ProjectPersons persons ) {
		this.titles = titles;
		this.persons = persons;
	}
	
	// json
	
	public String stringify() {
		return JsonMapper.stringify( this );
	}
	
	public static ListdataJson parse( final String json ) {
		return null == json ? null : JsonMapper.parse( json, ListdataJson.class );
	}
}
