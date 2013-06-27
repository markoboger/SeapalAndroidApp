Contents
======== 

 1. __SeapalAndroidApp__

 2. __Activities__

 3. __Database__
 
 4. __Architecture__

 4. __Text User Interface__
 


The `Documentation.pptx` and `Documentation.pdf` provide documentation for the functionality of the current version.

_______________________________________

SeapalAndroidApp
================

Tooling and Dependencies
------------------------

#### 1.  TouchDB and Ektorp

  + https://github.com/couchbaselabs/TouchDB-Android
  + TouchDB is used as Database.
  + TouchDB-Android and TouchDB-Android-Ektorp have to be included as android library projects. 

#### 2. Roboguice

  + https://github.com/roboguice/roboguice
  + Rubuguice is used as Dependency Injection Framework
  + These jars are needed in the libs folder of the Project:
	`guice-3.0-no_aop.jar, javax.inject-1.jar, roboguice-2.0.jar` 
	
	
#### 3. Google Play Services Lib
  + http://developer.android.com/google/play-services/setup.html
  + The Google Play Services Lib has to be included as an android library project.

	
#### 4. Seapal Core Project
  + https://github.com/bsautermeister/de.htwg.seapal.core.git
  + This Project contains the Model and Controller Layer for Seapal. It is used both for
  the server and the android version. See the ReadMe of the SeapalCore for further details.
  + The (with java 6) compiled jar has to be in the libs folder.




Building Project
----------------

To build the Project just import the Repository to Eclipse, then first clean and then build it as an android Project.

If there are Problems during the buildprocess you can try the following:

>	+ Create missing `gen` sourcefolders
>	+ Run `project --> Android Tools --> Fix Project Properties`
>	+ First remove and then add android library Projects again


Testing
----------

In the SeapalAndriodApp project is an android test project integrated. The project is in the `/test` folder.
It the Repository is imported to Eclipse there will be a separate test project. To execute the tests you just have
to run the project as an android jUnit test on either an emulator or a real device.

Activities
==========

BaseDrawerActivity
------------------

This Activity is responsible for the drawer navigation menu. All other Activities should extend this Activity 
in order to enable the drawer navigation.

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
calculate this in the activity. Until now the elapsed days, hours, minutes and second are displayed.


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
==============

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

##### Querying and Views

In CouchDB all the querying is done in the background. You do not have to do SQL-like statements to query for Objects.
You can either get all IDs of your document or Views. This Views can improve your searchspeed and
effiency by creating Views. 

This example creates a View for Trips, which are sorted by the BoatID to which they belong:

	private static final String DDOCNAME = "Trip";
 	private static final String VIEWNAME = "by_boat";
 	TDView view = tdDB.getViewNamed(String.format("%s/%s", DDOCNAME,
				VIEWNAME));
		view.setMapReduceBlocks(new TDViewMapBlock() {
			@Override
			public void map(Map<String, Object> document,
					TDViewMapEmitBlock emitter) {
				Object Boat = document.get("boat");
				if (Boat != null) {
					emitter.emit(document.get("boat"), document.get("_id"));
				}

			}
		}, null, "1.0");


Architecture
============

The architecture used in this project is the model view controller (MVC) architecture pattern. The most important 
goal of this architecture is the separation of the user interaction with the app and the business logic. Also the 
persistent data storage of the model is separated from the view and the controller.

The model and the controller layer are extracted to a separate project, the seapal.core project. Especially the model 
has to be independent from the app, because it is used together with the seapal.server project. Changes on the model 
have to be discussed with the server team, because the common used database has to be adapted to the new model.

Text User Interface
===================

This app has a text user interface (TUI). The TUI is accessible over the drawer navigation from every Activity.
It provides all functionalities available through the controllers. It's possible to create, read, edit and delete 
everything in the model. This is achieved through the controller in the MVC.

The TUI itself is implemented with the State pattern, so its easily maintainable and extendible.
