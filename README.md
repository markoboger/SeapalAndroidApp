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


Mapactivity
----------------

The **Mapactivity** contains the GUI for the Map. The map has an DialogFragment which is implemented in
**MapDialogFragment**. Both classes currently provide the known mapfunctions like setMark, setRoute, setDistance, 
which can be selected in the dialog. Right now Marks and Routes are only created on the map and not stored in the DB.

**Problems with emulating Map**

Google does not deliver a working emulator at the moment. There for following additional APKs have to installed and
google play services.(https://www.dropbox.com/sh/0t128z94941s5i2/R59PpMLOPZ)
Furthermore every developer has to use the same debug.keystore, otherwise google will not accept the Maps API key
when building the project. 



Database
----------------

The database contains of one class for each Model where all CRUD-operations are implemented. The TouchDBHelper-Class 
is creates Databases and contains methods for synchronisation with the CouchDB-Server.

Database uses too libraries:
-TouchDB-Android
-TouchDB-Ektorp

TouchDB will create a local CouchDB on the smartphone. Data will be stored there and synchronized with the Server.
The DB-structure on the server and on the device must be the same. Otherwise synchronization will not work and 
the app will crash. 

DB-Access is done via Ektorp, which delivers an API to use CouchDBs REST API via java methods.
For further Information about ektorp visit http://ektorp.org/reference_documentation.html

#### CouchDB

Each Model-Object has an own UUID and a Revision in the database and are stored as JSONs. Ektorp makes it possible
to store complete objects. It will create JSONs out of it and store it in the database. To get objects out of the DB 
we just need the ID and the object.class. It is also possible to get a certain revision of an object if needed.

Synchronisation with the DB-Server is very easy and simple. Ektorp provides push and pull methods, where you only have
to set the link to the DB-Server, the DB-name which is to be replicated and a user if needed. After calling this
methods ektorp will do all the work. It is possible to have a continous replication in an own thread. Right now this
is not implemented push/pulls are made on start or when changes are made. 

Right now all data is stored on http://roroettg.iriscouch.com/ which is a "free" Web CouchDB. 































