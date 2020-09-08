package ddbjld.api.common.utility;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.common.exceptions.RestApiException;

@Slf4j
public class SpringJdbcUtil {

	public static class MapDataReader {
		
		public Map<String, Object> data;
		public MapDataReader() {}
		public MapDataReader( Map<String, Object> data ) {
			this.data = data;
		}
		
		public UUID uuid( final String key ) {
			return (UUID)column( this.data, key );
		}
		
		public String string( final String key ) {
			return (String)column( this.data, key );
		}
		
		public boolean bool( final String key ) {
			return (boolean)column( this.data, key );
		}
		
		public int integer( final String key ) {
			return (int)column( this.data, key );
		}
		
		public long bigint( final String key ) {
			return (long)column( this.data, key );
		}
		
		public LocalDate date( final String key ) {
			
			Date d = (Date)column( this.data, key );
			return null == d ? null : d.toLocalDate();
		}
		
		public LocalDateTime timestamp( final String key ) {
			
			Timestamp ts = (Timestamp)column( this.data, key );
			return null == ts ? null : ts.toLocalDateTime();
		}
		
		
		public <T> T nvl( final String key, final T alter ) {
			@SuppressWarnings("unchecked")
			T value = (T)column( this.data, key );
			return null == value ? alter : value; 
		}
		
		public boolean nvl( final String key ) {
			Object value = column( this.data, key );
			return null == value ? false : (boolean)value;
		}
	}
	
	public static Object column( 
			final Map<String, Object> data, 
			final String key ) {
		if ( data.containsKey( key ) ) return data.get( key );
		
		log.error( "not exist column[{}] in spring jdbc map.", key );
		
		throw new RestApiException( HttpStatus.INTERNAL_SERVER_ERROR );
	}
	
	
	public static Map<String, Object> top( 
			final List<Map<String, Object>> records ) {
		return 0 == records.size() ? null : records.get( 0 );
	}
	
	public static boolean exists( 
			final List<Map<String, Object>> records ) {
		return 0 == records.size();
	}
	
	public static class MapQuery {
		
		public static boolean exists(
				final JdbcTemplate jdbc,
				final String sql) {
			
			try {
				jdbc.queryForMap( sql );
				return true;
			} catch ( EmptyResultDataAccessException ex ) {
				return false;
			}
		}
		public static boolean exists(
				final JdbcTemplate jdbc,
				final String sql,
				final Object[] args) {
			
			try {
				jdbc.queryForMap( sql, args );
				return true;
			} catch ( EmptyResultDataAccessException ex ) {
				return false;
			}
		}
		
		public static Map<String, Object> one( 
				final JdbcTemplate jdbc, 
				final String sql ) {
			return top( jdbc.queryForList( sql ) );
		}
		public static Map<String, Object> one( 
				final JdbcTemplate jdbc, 
				final String sql, 
				final Object[] args ) {
			return top( jdbc.queryForList( sql, args ) );
		}
		
		public static List<Map<String, Object>> all( 
				final JdbcTemplate jdbc, 
				final String sql ) {
			return jdbc.queryForList( sql );
		}
		public static List<Map<String, Object>> all( 
				final JdbcTemplate jdbc, 
				final String sql, 
				final Object[] args ) {
			return jdbc.queryForList( sql, args );
		}
	}
	
}
