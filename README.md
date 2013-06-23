SeapalAndroidApp
================

Tooling and Dependencies
------------------------



Building Project
----------------

Additional Apps for Emulator: https://www.dropbox.com/sh/0t128z94941s5i2/R59PpMLOPZ

install on Emulator: [android-sdk]\platform-tools\adb.exe -e install PATH\TO\com.android.vending-1.apk



Logbook
----------------

The Logbook consists of three components. **Boat**, **Trip** and **Waypoint** (Waypoint not yet implemented). 


### Boat

The **BoatActivity** in *SeapalAndroidApp/src/de/htwg/seapal/aview/gui/activity/BoatActivity.java* includes
two fragments for listview and detailview (BoatListFragment, BoatDetailFragment). So you have the possibility
to view both fragments on a tablet and only one fragment on a smartphone.

#### BoatActivity

This activity: 
				+ extends BaseDrawerActivity 
				+ implements IObserver
				+ implements BoatListFragment.ListSelectedCallback  

The BoatActivity gets an injected BoatController from *com.google.inject.Inject*. You have to add this controller
to Observer to get the notify method from Observable. 

The main view of the activity is the **boat.xml**. This xml-file is in the *layout* folder for smartphones and in the 
*layout-xlarge* for tablets. It consists of one respectively two FrameLayouts. The FrameLayout is only a place holder 
for the Fragments. 

