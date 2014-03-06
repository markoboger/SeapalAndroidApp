Contents
======== 

 1. __SeapalAndroidApp__

 2. __Architecture__

 3. __Activities__

 4. __Database__
 

SeapalAndroidApp
================

Tooling and Dependencies
------------------------
 
The Dependancies are managed by gradle the there is now real setup of libraries needed.

Building Project
----------------

To build the project you just have to typ in one command

  > + ./gradlew build
  > + ./gradlew.bat build

gradlew is a gradle wrapper which will download the needed gradle version for you and offers you the ability to install
the apk directly to the device.

  > + ./gradlew installDebug
  > + ./gradlew.bat installDebug



Architecture
============

Using the core library is one part of the Architekture. Which offers the Models and Controllers that are used to access
the different Databases.

The new internal achritektur of the app uses an event driven actitecture. So it resolves the coupling of classes and
keeps the code small. 

There are Managers and States. Mostly the states define the state in which the map is in.
Managers help the states to do there work. 


There are a bunch of event defined

 > + map events
 > ++ manager events

 > + Logbook events
 > ++  boat events
 > ++  crew events
 > ++  session events
 > ++  trip events




Activities
==========

--- needs to be updated

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
 

### Trip



Mapactivity
----------------




Database
==============

The database contains of one class for each Model where all CRUD-operations are implemented. The TouchDBHelper-Class 
is creates Databases and contains methods for synchronisation with the CouchDB-Server.

Database uses too libraries:
-Couchbase-lite-android
-Couchbase-lite-ektorp

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


