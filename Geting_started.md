# Aerosol Burst Simulator #

This program has two main purposes: to simulate atmospheric aerosol nucleation bursts and analyze simulation results - compare with the results measured in real life.

# Modeling #

**Simulation with fixed parameters**

To execute a simulation with fixed parameters, user have to set inception parameters to all "Minimum" fields and press "Run" button. User can use program default values (already set when program is launched) or use own values.

When simulation is ongoing, "Run" button goes inactive and "Stop" button becomes active. When simulation is done, again "Run" button is active and "Stop" is inactive.

**Simulation with generated parameters**

To execute a simulation with generated parameters, user have to set inception parameters to all "Minimum" and "Maximum" fields and press "Run" button. A random value between given range will be generated automatically. User can use values suggested from drop-down menus or use own values.

When simulation is ongoing, "Run" button goes inactive and "Stop" button becomes active. When simulation is done, again "Run" button is active and "Stop" is inactive.


**Simulation with combined parameters**

This is a combination from two previous options - some inception parameters are fixed and some shall be generated. To execute a simulation with combined values, user have to set inception parameters to all "Minimum" fields and to those "Maximum" fields, where parameters are not fixed and press "Run" button. User can use values suggested from drop-down menus or use own values.

When simulation is ongoing, "Run" button goes inactive and "Stop" button becomes active. When simulation is done, again "Run" button is active and "Stop" is inactive.


**Simulation with parameters from file**

To execute a simulation with values from file, user have to press a "Take values from file" button, select the file.

When simulation is ongoing, "Run" button goes inactive and "Stop" button becomes active. When simulation is done, again "Run" button is active and "Stop" is inactive.


**Related buttons & features:**

**"Run"** - start simulation(s);

**"Stop"** - finish calculations with ongoing  inception parameters set and stop;

**"# of runs"** - determine how many simulations should be done. Can be used when one or more inception parameters are generated;

**Comment field** - comment field information will be included to a database when simulation is done;

**"Clear"** - clear all "Minimum" and "Maximum" values;

**"Defaults"** - set default values to all "Minimum" fields;

"**Save Parameters**" - save all parameters into a file;

"**Open Parameters**" - load previously saved parameters into fields;

**"Undo"** and **"redo"** - take previous step back or opposite;

# Analysis #

Reference values are in ..\aerosol-simulator-1.0-SNAPSHOT\etc\conf\ref.xl file. First line is columns explanation, second is column "weights" and third and further lines are actual values. User can freely modify those values, including column "weights".

**Finding closest inception parameters sets**

In order to compare reference values with simulated values and determine which inception parameters gave closest results to a reference values, simply press the "Find Best Results" button. When program is done, a dialog window will pop-up with an option to save (up to ten closest) results into a file.

**Set best parameters**

To get best inception parameters set into a program "Minimum" fields, simply click on "Open Best" button.

# Extra features #

You can export all your local program data into a file and import to other computer with same program or make searches in you local database without having any database browser program, such as "SQLite Database Browser" installed into your computer.

**Import**

To import all data, simply click on "Utilities" button and a separate window will pop-up. Click on "Import" and a dialog window will pop, select a file with data and you are done.

**Export**

To export all data, simply click on "Utilities" button and a separate window will pop-up. Click on "Export" and a dialog window will pop, give file a name and location and you are done.

**Export specific result set by id**

To export specific result set, simply click on "Utilities" button and write an id number (e.g .pid number from a file created by "Find Best Results" function) and then click on "Save Results". Now result set of that specific inception parameters will be written into a file of user choice.

**Open specific parameters**

Select "Process" from a drop-down menu, write a pid (id) number and click on "Open order or process by id". Inception parameters of that specific run will be loaded.

If you also know the id of the simulations group (order) you can
select "Order" from drop-down menu, put that id into a text field and press the button again. Simulations group (order) simply has the maximum fields set. If you start simulating based on these min and max values the application might generate totally different simulation parameters than it did in the past, because it takes something random between min and max.