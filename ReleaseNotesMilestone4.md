# AGS Release Notes for Milestone 4 #

**Rev 300** offer all planned core functionality and some extra features.

# Overview #

1. All simulation options are now fully working. User can set fixed, combined or generated values or taken them from file;

2. Templates - saving / loading / clearing /setting default values to GUI is working;

3. Specific order/process inception parameters can now be loaded by id number to GUI ("Open Order or process by id") and specific process according results can now be saved into a file ("Save results");

4. Export/Import - working. All DB entries can be exported to a file and imported to other computer with same program version;

5. Comparison - fully working. User can define reference values and compare them with simulation results, save 10 best results into a file and set the best inception values to a GUI.


**Changes in scope**

1. A core functionality - export local DB entries into outer DB was discarded.


# Updates from previous release #

1. Extra "Stop" button to cancel ongoing process and move to next;

2. Added a function to load values from file;

3. Updated "Help" description.


**Known Bugs:**

1. HT program itself may sometimes crash, random Initial/ halftime and final concentration of background aerosol values may trigger it. Also Nadykto-Yu both two parameters may cause HT crash. This is not in our concern! To avoid these cases, use the following background aerosol particles values: 1500, 7000, 15000 and Nadykto-Yu fact. 1.0 and factor 1.0 or use "Stop current process" function.

2. Comparison function results shall be saved without file extension, otherwise faulty information will be saved;

3. Yet no background color system implemented for abnormal parameter values.


**Fixed bugs**

1. "Allow taking multiple simulation processes from CSV-like file" now fully working;

2. "Allow importing/exporting from database" - now fully working.

**Footnotes:**

Burst Simulator is the "blackbox" Delphi application that this Java application runs.