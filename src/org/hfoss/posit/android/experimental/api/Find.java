package org.hfoss.posit.android.experimental.api;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.http.message.BasicNameValuePair;
import org.hfoss.posit.android.experimental.Constants;
import org.hfoss.posit.android.experimental.plugin.acdivoca.AcdiVocaFind;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

/**
 * Represents a specific find for a project, with a unique identifier.
 * 
 */

public class Find implements FindInterface {

	public static final String TAG = "Find";

	// Db Column names
	public static final String ORM_ID = "id";
	public static final String GUID = "guid";
	public static final String PROJECT_ID = "project_id";
	
	public static final String NAME = "name";
	public static final String CLASS_NAME = "class_name";   // Either Find or some subclass

	public static final String DESCRIPTION = "description";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String TIME = "timestamp";
	public static final String MODIFY_TIME = "modify_time";

	public static final String IS_ADHOC = "is_adhoc";
	public static final String DELETED = "deleted";
	
	public static final String REVISION = "revision";
	public static final String ACTION = "action";
	
	public static final String EXTENSION = "extension";
	
	// For syncing.  Operation will store what operation is being performed
	// on this record--posting, updating, deleting.  Status will
	// record the state of the sync--transacting or done.
	public static final String SYNC_OPERATION = "sync_operation";
	public static final String STATUS= "status";
	public static final int IS_SYNCED = 1;
	public static final int IS_NOT_SYNCED = 0;


	// Instance variables, automatically mapped to DB columns
	@DatabaseField(columnName = ORM_ID, generatedId = true)
	protected int id;
	@DatabaseField(columnName = GUID)
	protected String guid;
	@DatabaseField(columnName = PROJECT_ID)
	protected int project_id;
	@DatabaseField(columnName = NAME)
	protected String name;
	@DatabaseField(columnName = DESCRIPTION)
	protected String description;
	@DatabaseField(columnName = LATITUDE)
	protected double latitude;
	@DatabaseField(columnName = LONGITUDE)
	protected double longitude;
	@DatabaseField(columnName = TIME, canBeNull = false)
	protected Date time = new Date();
	@DatabaseField(columnName = MODIFY_TIME)
	protected Date modify_time;
	@DatabaseField(columnName = IS_ADHOC)
	protected int is_adhoc;
	@DatabaseField(columnName = DELETED)
	protected int deleted;
	
	@DatabaseField(columnName = REVISION)
	protected int revision;
	@DatabaseField(columnName = ACTION)
	protected String action;
	
	@DatabaseField(columnName = SYNC_OPERATION)
	protected int syncOperation;
	@DatabaseField(columnName = STATUS)
	protected int status;

	/**
	 * Creates the table for this class.
	 * 
	 * @param connectionSource
	 */
	public static void createTable(ConnectionSource connectionSource) {
		Log.i(TAG, "Creating Finds table");
		try {
			TableUtils.createTable(connectionSource, Find.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Changed to "public"--objections?
	 */
	public Find() {
	}

	/**
	 * This constructor is used for a new Find. Its ID will be automatically
	 * created.
	 * 
	 * @param context
	 *            is the Activity
	 */
	public Find(Context context) {
	}

	/**
	 * This constructor is used for an existing Find. Its id is used to retrieve
	 * it
	 * 
	 * @param context
	 *            is the Activity
	 * @param guid
	 *            is a globally unique identifier, used by the server and other
	 *            devices
	 */
	public Find(Context context, int id) {

	}

	/**
	 * This constructor is used for an existing Find. The Find's id is
	 * automagically generated but not its GUID.
	 * 
	 * @param context
	 *            is the Activity
	 * @param guid
	 *            is a globally unique identifier, used by the server and other
	 *            devices
	 */
	public Find(Context context, String guid) {
	}
	
	/**
	 * Creates a find object from content values.
	 * @param content ContentValues object that contains all the fields of a Find
	 */
	public Find(ContentValues cv) {
//		updateObject(cv);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getModify_time() {
		return modify_time;
	}

	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}

	public int getIs_adhoc() {
		return is_adhoc;
	}

	public void setIs_adhoc(int is_adhoc) {
		this.is_adhoc = is_adhoc;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int projectId) {
		this.project_id = projectId;
	}
	
	public int getRevision() {
		return revision;
	}
	
	public void setRevision(int revision){
		this.revision = revision;
	}

	public int getSyncOperation() {
		return syncOperation;
	}

	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getStatusAsString() {
		switch (getStatus()) {
		case Constants.POSTING:
			return "posting";
		case Constants.TRANSACTING:
			return "transacting";
		case Constants.SUCCEEDED:
			return "synced";
		default:
			return "unsynced";
		}
	}
	
	public void setSyncOperation(int syncOperation) {
		this.syncOperation = syncOperation;
		
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void sync(String protocol) {
		// TODO Auto-generated method stub
	}

	/**
	 * Converts a value to the type that matches a given field in this class
	 * NOTE: This method is incomplete. It needs more cases for both field's and val's type.
	 * @param field, the field's typed as represented by a Class object
	 * @param val, the value being converted
	 * @return an Object whose dynamic type is Integer or Boolean or ...
	 */
	protected Object convertValueTypeForField(Class field, Object val) {
		String oType = field.getName();
		Object result = null;
		Log.i(TAG, "Convert argument for " + oType + " field for value " + val);
		try {
			if (oType.equals("java.lang.Integer") || oType.equals("int")) {
				result = Integer.parseInt((String)val);
			} else if (oType.equals("java.lang.Boolean") || oType.equals("boolean"))  {
				result = Boolean.parseBoolean((String) val);
			} else if (oType.equals("java.lang.Double") || oType.equals("double"))
				result = Double.parseDouble((String) val);
			else if (oType.equals("java.lang.String")) {
				result = val.toString();
			} 
			else  {
				result = val;
			}
			Log.i(TAG, "Returning " + result + " of type " + result.getClass());
			return result;
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Uses reflection to copy data from a ContentValues object to this Find object.
	 * This should also work for subclasses of Find.
	 * 
	 * NOTE: This is a little ugly.  Can it be simplified?  The main complication is
	 * that it seems like you have to handel the cases of the derived fields and the
	 * Find fields separately.  
	 * 
	 * @param cv, the ContentValues (key=val, key2=val2, ...)
	 */
	public void updateObject(ContentValues cv) {
		Set <Entry <String, Object>> cvSet = cv.valueSet();
		Iterator it = cvSet.iterator();
		
		// For each key/val pair
		while (it.hasNext()) {
			Entry<String, Object> entry = (Entry<String,Object>)it.next();
			String key = entry.getKey();
			Object val = entry.getValue();
			Log.i(TAG, "Key = " + key + " val = " + val + " " + val.getClass().getName());
				
			Field field = null;
			try {
				// Find a field with the same name as the key.  This will throw and exception
				// when the field is declared in the (Find) superclass for a derived object.
				field = this.getClass().getDeclaredField(key);
				
				// Get the field's type.
				Class fieldType = field.getType();
				
				// Make the field accessible so that it can be referenced here. 
				field.setAccessible(true);

				// If there's no type conflict
				if (fieldType.getSimpleName().equals(val.getClass().getSimpleName())) 
					field.set(this, val);
				else {
					Log.i(TAG, "field type = " + fieldType.getSimpleName() + " val type = " + val.getClass().getSimpleName());
					// Convert the value, val, to object of the same type as the field's type
					Object obj = convertValueTypeForField(fieldType, val.toString());
					//Log.i(TAG, "obj = " + obj.toString() + " of type " + obj.getClass());

					// Set the field's value
					field.set(this, obj);
				}
				Log.i(TAG, ">>>>>>> Set" + field + "=" + val);
			}  catch (NoSuchFieldException e) {
				try {
					// This will handle the case where the field is declared in the superclass
					// and the current (dynamic) object is a derived Find.
					Log.i(TAG, "#####Exception: no such field " + key + " in " + this.getClass());
					
					// Get the superclass field
					field = this.getClass().getSuperclass().getDeclaredField(key);
					
					// Set its value -- for the Find class, the type's should match.
					field.set(this, val);
					Log.i(TAG, ">>>>>>> Set" + field + "=" + val);					
				} catch (NoSuchFieldException ex) {
					Log.i(TAG, "Exception: no such field " + key + " in " + this.getClass().getSuperclass());
					e.printStackTrace();
				} catch (IllegalArgumentException ex) {
					e.printStackTrace();
				} catch (IllegalAccessException ex) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IllegalArgumentException e) {
				Log.i(TAG, "Illegal Argument " + field.getName() + " in " + this.getClass());
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				Log.i(TAG, "Illegal Access " + field.getName() + " in " + this.getClass());
				e.printStackTrace();
			}
		}
	}



	@Override
	public String toString() {
		return "Find [id=" + id + ", guid=" + guid + ", project_id=" + project_id + ", name=" + name + ", description="
				+ description + ", latitude=" + latitude + ", longitude=" + longitude + ", time=" + time
				+ ", modify_time=" + modify_time + ", is_adhoc=" + is_adhoc + ", deleted=" + deleted + ", revision="
				+ revision + ", syncOperation=" + syncOperation + ", status=" + status + "]";
	}
}