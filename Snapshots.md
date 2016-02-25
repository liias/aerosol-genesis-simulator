New snapshot build (zip) is created after every commit.

You can download the latest snapshot ZIP file from here:

http://ec2-54-246-2-243.eu-west-1.compute.amazonaws.com:8080/job/ags/lastSuccessfulBuild/ee.ut.physic.aerosol.simulator$aerosol-simulator/

  1. Download the one ending with .zip
  1. Extract it anywhere where you have write access.
  1. Double-click the jar file. Done.

For debugging run it from terminal with "java -jar thejarfile.jar"


## Old method ##

Previously there was no zip built. But I leave the old text just in case it contains something useful:

You need to download three files:

  1. [Dependencies archive](http://code.google.com/p/aerosol-genesis-simulator/downloads/detail?name=ags_dependencies.zip&can=2&q=)
  1. [Libraries archive](http://code.google.com/p/aerosol-genesis-simulator/downloads/detail?name=ags_libraries.zip&can=2&q=)
  1. [Application jar](http://ec2-54-246-2-243.eu-west-1.compute.amazonaws.com:8081/nexus/content/repositories/snapshots/ee/ut/physic/aerosol/simulator/aerosol-simulator/1.0-SNAPSHOT/)


Extract all the archives to the same directory, so you will have such directory structure:

  * etc/
  * lib/
  * aerosol-simulator-VERSION.jar

Double-click the jar. TADAA!

Also, make sure the user you run it from has write access to the folder where you extracted these files.