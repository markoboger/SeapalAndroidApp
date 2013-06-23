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

---

### Boat

The **BoatActivity** in *SeapalAndroidApp/src/de/htwg/seapal/aview/gui/activity/BoatActivity.java* includes
two fragments for listview and detailview (BoatListFragment, BoatDetailFragment). So you have the possibility
to view both fragments on a tablet and only one fragment on a smartphone.


#### BoatActivity

This activity:  

>* extends BaseDrawerActivity 
>* implements IObserver
>* implements BoatListFragment.ListSelectedCallback  

The BoatActivity get an injected BoatController from *com.google.inject.Inject*. You have to add this controller
to Observer to get the notify method from Observable and set this controller instance to BoatListFragment and
BoatListDetail. So it's secured that all components have the same controller object.

The main view of the activity is the **boat.xml**. This xml-file is in the *layout* folder for smartphones and in the 
*layout-xlarge* for tablets. It consists of one respectively two FrameLayouts. The FrameLayout is only a place holder 
for the Fragments. Each Fragment has an own layout (boatlist.xml, boatdetail.xml), which will be dynamically added to 
the main layout. This is implemented with a FragmentTransaction in the onCreate() method. The Android Operation 
System choose the right main layout. 

The method *void selected (UUID boat)* is the callback function of the ListFragment and is called when the user clicked
on a listelement. If you are using a tablet device the visible detail Fragment will be updated. If you are using a 
smartphone device the detail Fragment will be replaced with the list Fragment.

The method *void update(Event event)* is called from the Observable Pattern when something is changed on an object.
This function update the view when the user *add*, *delete* or *change* a boat. There you have to remove all deleted 
fragments from BackStack.


#### BoatListFragment

This fragment:  

>* extends ListFragment

Every list in Android get an own layout, where you declare the view of each list element (boatlist.xml). Also you have 
to implement an Adapter which extends *ArrayAdapter<T>* (BoatListAdapter).

The header of the list has an own layout (boatlistheader) and is dynamically included in the main layout. This is
implemented with the LayoutInflafer. 

When the user changed the justification (portrait, landscape) of the device the *onConfigurationChange* method is 
called. There you must remove the header (this will create new) and call the *onActivityCreated* method, so Android 
pick the new right layout folder. Also you must add to Android Manifest:

> android:configChanges="orientation|screenSize|keyboardHidden"

When only the ListFragment is displayed then only the *new* option button is shown. 
When only the DetailFragment is displayed then only the *save* and the *delete* option buttons are shown.

In the BoatListFragment you have to declare the ListSelectedCallback interface with the selected-function.
 

#### BoatDetailFragment

This fragment:  

>* extends Fragment

Here the user can input informations of the selected boat. The EditText input field are so initialized that
the user only can type numbers, doubles or strings in the specific fields. 
For example:

>length.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

When the user deletes the displayed boat, there are two scenarios:

	1. only BoatFragment is shown (smartphone) -> return to ListFragment and update it.
	2. both fragments are shown (tablet) ->  delete the boat from list and clear the EditText fields.

---

### Trip

For this component there are two activities. **TripListActivity** where you get a ListView of all Trips of
the selected Boat. **TripActivity** where you get all information about the selected Trip. You also have to 
implement an Adapter for the ListView. 

The TripListActivity only listen which Trip is selected. It has no options, because a Trip only can be 
created in the MapActivity (tracking). 

In TripActivity you have to format the date fields, with the Android function *DateFormat*. The date is 
stored as a Unix timestamp (long) in the model. The duration of a trip is not stored, so you have to 
calculate this in the activity. Until now the elapsed days, hout, minutes and second are displayed.
































