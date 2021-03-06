package ddbjld.api.data.json.elasticsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ddbjld.api.data.json.AggregateJson;
import ddbjld.api.data.json.ListdataJson;
import ddbjld.api.data.values.ProjectIds;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ESProjectIndex {

	/** プロジェクトID（UUID/ID） */
	public ProjectIds ids;
	
	
	/** メタデータ（キーワード抽出） */
	// 各メタデータシートのデータを直列化したキーワード配列。
	public String[] metadata;
	
	/** 集計データJSON */
	public AggregateJson aggregate;
	
	/** 一覧データJSON */
	public ListdataJson listdata;
	
	/** 個別検索用抽出データJSON */
	public FacetJson facet;
	
	/** リリース日（初回公開日時;t_project.first_published_at の年月日） */
	public String publishedDate;

	
	ESProjectIndex() { 
		// jackson用のデフォコン
	}
	public ESProjectIndex( 
			ProjectIds ids, 
			String[] metadata, 
			AggregateJson aggregate,
			ListdataJson listdata,
			FacetJson facet,
			String publishedDate ) {
		this.ids = ids;
		this.metadata = metadata;
		this.aggregate = aggregate;
		this.listdata = listdata;
		this.facet = facet;
		this.publishedDate = publishedDate;
	}	
}
