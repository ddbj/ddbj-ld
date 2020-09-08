package ddbjld.api.common.utility.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import ddbjld.api.data.json.ExcelSheetData;
import ddbjld.api.data.json.ExcelSheetData.MetadataItems;
import ddbjld.api.data.json.ExcelSheetData.MetadataObjects;

public class MetadataAccessor {
	
	// ■データ構造メモ■
	// ExcelSheetData[] : ExcelToolがポストしてくるデータ構造。
	// ExcelSheetData : 1SHEET ぶんのデータを持ったオブジェクト、ただし、常に要素数１の無駄な配列構造を持っている。
	// ExcelSheetData.data[0]
	// = MetadataObjects : Excel的に表現すると DataRow[] に相当。行データのID項目値をキーとしたMAP構造になっている。
	// MetadataObjects[key]
	// = MetadataItems : Excel的に表現すると DataRow に相当。データ行のカラムIDをキーとしたMAP構造になっている。
	// MetadataItems[column]
	// = String[] : セル値。（※）
	// 通常はExcel的に表現すると Cell値 に相当し、普通はString（またはObject）であるが、String[] になっている。
	// これは、ddbj用のExcelTool及びデータ仕様に依存するもので、
	// 単一のIDに対して複数の値を紐付けられる（つまり非正規化データ）という仕様に基づいている。
	
	/**
	 * @author sugaryo
	 * @see ddbjld.api.data.json.ExcelSheetData
	 * @see ddbjld.api.data.json.ExcelSheetData.MetadataObjects
	 */
	public static class SheetAccessor {

		private static final ImmutableList<MetadataItems> EMPTY = ImmutableList.of();
		
		/** sheet_id */
		public final String id;
		
		/** WARPするSHEETデータの本体 */
		public final ExcelSheetData sheet;
		
		/** 実際のSHEET単位データオブジェクト（＝SHEET.ROWS＝ROW[] ※実際にはMAP形式） */
		private final ExcelSheetData.MetadataObjects data; // sheet.data[0] -> data = rows = metadataのコレクション
		
		/** SHEETの各データ行のID配列 */
		private final String[] keys;

		public SheetAccessor( final String id, final ExcelSheetData sheet ) {
			this.id = id;
			
			// Accessor は null の場合も使えるようにしておく。
			if ( null == sheet ) {
				this.sheet = null;
				this.data = null;
				this.keys = new String[] {};
			}
			// 無駄な配列要素が噛まされているので解り難いのをカプセル化する。
			// （本当はJSONデータ構造を変更すべきだがExcelVBAの方を弄る事になるので運用回避）
			else {
				this.sheet = sheet;
				this.data = this.sheet.data[0];
				this.keys = this.data.keySet().toArray( String[]::new );
			}
		}

		// SHEET データの有無
		public boolean has() {
			return 0 < this.keys.length;
		}
		public boolean none() {
			return 0 == this.keys.length;
		}
		
		// SHEET 内のデータ行数
		public int size() {
			return this.keys.length;
		}
		
		// ▼ここから先は has チェック済み前提のAPI▼
		public String rowId( final int index ) {
			return this.keys[index];
		}
		public MetadataItems row( final int index ) {
			return this.row( this.keys[index] );
		} 
		public MetadataItems row( final String rowId ) {
			return this.data.get( rowId );
		}
		
		public MetadataObjects objects() {
			return this.data;
		}
		public Collection<MetadataItems> rows() {
			return null == this.data ? EMPTY : this.data.values();
		}
	}
	
	public static class DataRowReader {
		// 行オブジェクトに対するアクセス機能を提供。
		// シートは最大５３くらいなので都度newでも良いが、
		// 全シート全行となると流石に多くなり過ぎるので、
		// DataRowReader はインスタンス一個作って共有する構造にする。
		private MetadataItems row;
		
		public DataRowReader bind( final MetadataItems row ) {
			this.row = row;
			return this;
		}
		
		
		public boolean has( final String columnId ) {
			return this.row.containsKey( columnId );
		}
		
		
		public String string( final String columnId ) {
			return DataRowReader.string( this.row, columnId );
		}
		public String string( final String columnId, final String alt ) {
			return DataRowReader.string( this.row, columnId, alt );
		}
		
		public String[] strings( final String columnId ) {
			return DataRowReader.strings( this.row, columnId );
		}
		public String[] strings( final String columnId, final String[] alt ) {
			return DataRowReader.strings( this.row, columnId, alt );
		}
		
//		public Object[] values( final String columnId ) {
//			return DataRowReader.values( this.row, columnId );
//		}
//		public Object[] values( final String columnId, final Object[] alt ) {
//			return DataRowReader.values( this.row, columnId, alt );
//		}
		
		
		// static utility
		
		// TODO：altなしのAPIでは例外にしたほうが良いかも。
		
		public static String string( final MetadataItems row, final String columnId) {
			return string( row, columnId, null );
		}
		public static String string( final MetadataItems row, final String columnId, String alt ) {
			if ( row.containsKey( columnId ) ) {
				String[] values = row.get( columnId );
				return (String)values[0];
			} else {
				return alt;
			}
		}
		public static String[] strings( final MetadataItems row, final String columnId ) {
			return strings( row, columnId, null );
		}
		public static String[] strings( final MetadataItems row, final String columnId, String[] alt ) {
			if ( row.containsKey( columnId ) ) {
				Object[] values = row.get( columnId );
				return Arrays.stream( values, 0, values.length ).map( x -> (String)x ).toArray( String[]::new );
			} else {
				return alt;
			}
		}
//		public static Object[] values( final MetadataItems row, final String columnId ) {
//			return values( row, columnId, null );
//		}
//		public static Object[] values( final MetadataItems row, final String columnId, final Object[] alt ) {
//			return row.containsKey( columnId ) ? row.get( columnId ) : alt;
//		}
	}
	
	private final Map<String, ExcelSheetData> book;

	private MetadataAccessor( final Map<String, ExcelSheetData> book ) {
		this.book = book;
	}
	
	public boolean has( final String sheetId ) {
		return this.book.containsKey( sheetId );
	}

	public SheetAccessor sheet( final String sheetId ) {
		return new SheetAccessor( sheetId, this.book.get( sheetId ) );
	}
	
	
	
	public static MetadataAccessor compile( final ExcelSheetData[] datasource ) {
		Map<String, ExcelSheetData> book = new LinkedHashMap<>();
		
		for ( ExcelSheetData data : datasource ) {
			book.put( data.sheet_id, data );
		}
		
		return new MetadataAccessor( book );
	}
}
