package com.ddbj.ld.data.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.LinkedHashMap;

//シート単位でのデータ構造
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcelSheetData {

	// ■参考データ：
	// {
	//     "sheet_name": "Project",
	//     "data": [
	//         {
	//             "RMM00002": {
	//                 "Project": [
	//                     "RMM00002"
	//                 ],
	//                 "project ID": [
	//                     "RMM00002"
	//                 ],
	//                 "title": [
	//                     "Characterization of Lipid Profiles after Dietary Intake of Polyunsaturated Fatty Acids Using Integrated Untargeted and Targeted Lipidomics"
	//                 ],
	//                 "description": [
	//                     "Illuminating the comprehensive lipid profiles after dietary supplementation of polyunsaturated fatty acids (PUFAs) is crucial to revealing the tissue distribution of PUFAs in living organisms, as well as to providing novel insights into lipid metabolism. Here, we performed lipidomic analyses on mouse plasma and nine tissues, including the liver, kidney, brain, white adipose, heart, lung, small intestine, skeletal muscle, and spleen, with the dietary intake conditions of arachidonic acid (ARA), eicosapentaenoic acid (EPA), and docosahexaenoic acid (DHA) as the ethyl ester form. We incorporated targeted and untargeted approaches for profiling oxylipins and complex lipids such as glycerol (phospho) lipids, sphingolipids, and sterols, respectively, which led to the characterization of 1026 lipid molecules from the mouse tissues. The lipidomic analysis indicated that the intake of PUFAs strongly impacted the lipid profiles of metabolic organs such as the liver and kidney, while causing less impact on the brain. Moreover, we revealed a unique lipid modulation in most tissues, where phospholipids containing linoleic acid were significantly decreased in mice on the ARA-supplemented diet, and bis(monoacylglycero)phosphate (BMP) selectively incorporated DHA over ARA and EPA. We comprehensively studied the lipid profiles after dietary intake of PUFAs, which gives insight into lipid metabolism and nutrition research on PUFA supplementation."
	//                 ],
	//                 "creator": [
	//                     "MikikoTakahashi"
	//                 ],
	//                 "contact person": [
	//                     "AtsushiFukushima"
	//                 ],
	//                 "principal investigator": [
	//                     "HiroshiTsugawa",
	//                     "SatokoNaoe",
	//                     "MakotoArita"
	//                 ],
	//                 "submitter": [
	//                     "MikikoTakahashi"
	//                 ],
	//                 "references": [
	//                     "pmid:31640217"
	//                 ],
	//                 "funding source": [
	//                     "JSPS KAKENHI (15H05897, 15H05898, 18H02432, 18K19155)",
	//                     "JST National Bioscience Database Center (NB"
	//                 ]
	//             }
	//         }
	//     ]
	// }
	// 
	// ■データ構造：
	// // １シートに対してこのオブジェクトが１件含まれる。
	// {
	//     // シートIDとなる `sheet_name` と、含まれるデータ行配列に相当する "data" オブジェクト配列を含む。
	//     "sheet_name" : "Project",
	//     "data" : [
	//         // 1行目のデータ
	//         {
	//             // メタデータのID項目の値をキーにしたオブジェクトとして入力データが含まれる。
	//             "メタデータID" : {
	//                 // 項目ID毎に値配列が対応する <String, Object[]> 形式のMapデータとして格納。
	//                 // 数値系が来る項目もあるので String[] でなく Object[] で受け取る。
	//                 // なお、項目IDは template.json のスキーマ定義に於ける `label_name` に相当する。
	//                 "項目ID" : [ "value", "value", "value"... ],
	//                 "項目ID" : [ 1000, 2000, 3000... ],
	//                 "項目ID" : [ 0.01, 0.02, 0.03... ],
	//             }
	//         },
	//         // 2行目のデータ
	//         {},
	//         // 3行目...
	//         {},
	//     ]
	// }
	
	@SuppressWarnings("serial")
	public static class MetadataObjects // EXCEL::ROW[]
			// KEY : メタデータのID
			// VAL : メタデータの <項目ID : 値[]> 形式のマップデータ
			extends LinkedHashMap<String, MetadataItems> {
		
		// 継承してMapデータ構造を表現しているだけなので、特に実装はない。
	}
	
	@SuppressWarnings("serial")
	public static class MetadataItems // EXCEL::ROW
			// KEY : item-id (label_name) = Excel COLUMN に相当。
			// VAL : value[] 値配列。一つのカラムに対して複数の値を紐付けたいケースがある。（非正規系データ構造)
			// TODO：LinkedHashMapにする必要があるかも？（要確認）
			extends HashMap<String, String[]> {
		
		// 継承してMapデータ構造を表現しているだけなので、特に実装はない。
	}
	
	
	public String sheet_id;
	
	// # Excelシート入力データ【重要】
	// 無駄な配列構造になっているが、必ず data[0] にアクセスする。（ExcelTool側のJSON構造が宜しくない）
	// 実質的に data = data[0] で、持っているのは MetadataObjects 一個。
	// MetadataObjects が EXCEL で言う所の SHEET=ROWS 単位のデータ構造。
	// ROW.ID をキーとした MAP形式 でデータが POST されて来るのでこの形で受ける。
	// この辺のデータ構造はかなり解り難いが、ExcelTool側の実装都合に引き摺られているのため、安易に変更できない。
	// 仕方ないので、解り難い部分を MetadataAccessor 部品でカプセル化する事で表面上の実装コードは誤魔化しておく。
	public MetadataObjects[] data;
	
}
