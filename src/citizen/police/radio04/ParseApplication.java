package citizen.police.radio04;


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		  Parse.initialize(this, "fGEF9XpnhFamWkL1Vien174yoqlDqcuglbNPFrOZ", "16U6DxhGf7QywQAWbe272Zdgpz0ZQ6HTUvd4Hkx7");
		ParseACL defaultACL = new ParseACL();
	    
		// If you would like all objects to be private by default, remove this line.
		defaultACL.setPublicReadAccess(true);
		
		ParseACL.setDefaultACL(defaultACL, true);
	}

}
