# Release Notes for Milestone 2 #

This is the first real release.
Implemented is everything from the previous Aerosol Genesis Simulator, except the comparing part with the best result and setting the best result.

Also, it doesn't create the control-file form selected values yet (except the time parameter).


## Changelog ##
  * Add logging
  * Execution of burstsimulator.exe should call callback method
  * Add creation date for order and process
  * Add comment field for order
  * Add number of runs to order and ui
  * Generate processes from order
  * Fix Maven Shade integration with project, application does not start standalone
  * Add Burst Simulator application to resources
  * Fill the parameters.json
  * Add optional forest default values to parameters.json
  * Add ability to open/save order form values (min & max) from/to file
  * Move buttons to toolbar
  * Min and Max instead of exact, min, max
  * Fix values in dropdown box overflowing
  * Create maven goal to create correct jar and directory structure
  * Add Continuous Integration
  * Fix GUI rows being stacked on eachother
  * Support the latest burstsimulator version
  * Add empty value for parameter combobox
  * Read in burstsimulator.exe's result file and parse it to processresult and persist to database
  * Consider loading Spring's application context elsewhere
  * Add some error handling when burstsimulator.exe should fail somehow
  * Allow setting exact values for parameter value combobox options