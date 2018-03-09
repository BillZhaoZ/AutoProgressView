# AutoProgressView

Introduce
      
Installation

    Step 1: Add the JitPack repository to your root build.gradle file

	        allprojects {
	
		        repositories {
			        ...
			        maven { url 'https://jitpack.io' }
		        }
	        } 
    
    Step 2: Add the dependency in your build.gradle file

	        dependencies {	
                    compile 'com.github.BillZhaoZ:AutoProgressView:v1.0'	       
	        }

Usage
        
        1、in your xml 
        
        <?xml version="1.0" encoding="utf-8"?>
        <LinearLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:tools="http://schemas.android.com/tools"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="#DCDCDC"
            android:orientation="vertical"
            tools:context="com.zhao.bill.autoprogressview.MainActivity">
        
            <!--自动加载进度条-->
            <com.zhao.bill.autoprogressview.AutoProgressView
                android:id="@+id/auto_process"
                android:layout_width="match_parent"
                android:layout_height="55dp"
                android:layout_gravity="center_vertical"
                android:layout_marginTop="30dp"/>
        
        </LinearLayout>
        
        2、in your activity or adapter
           you can set all of the attributes in activity
        
             AutoProgressView autoProgressView = findViewById(R.id.auto_process);
        
             autoProgressView.setProgress(0.75);
             autoProgressView.setRateBackgroundColor("#e4f6eb");
             autoProgressView.setOrientation(LinearLayout.HORIZONTAL);
             autoProgressView.setAnimRate((int) (0.75 * 30));