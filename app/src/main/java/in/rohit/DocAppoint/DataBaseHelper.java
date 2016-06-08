package in.rohit.DocAppoint;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper
{
	public DataBaseHelper(Context context, String name,CursorFactory factory, int version) 
    {
	           super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase _db) 
	{
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE);
			
	}

	@Override
	public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) 
	{

			Log.w("TaskDBAdapter", "Upgrading from version " +_oldVersion + " to " +_newVersion + ", which will destroy all old data");
	
	
			// Upgrade the existing database to conform to the new version. Multiple
			// previous versions can be handled by comparing _oldVersion and _newVersion
			// values.
			// The simplest case is to drop the old table and create a new one.
			_db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");
			// Create a new one.
			onCreate(_db);
	}
	

}
