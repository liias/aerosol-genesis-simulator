# AGS Release Notes for Milestone 3 #

This version offer the following new features:
  * Successful Burst Simulator(1) input file generation.
  * SQLite instead of H2. Allows multiple connections (e.g browsing the .db file with some DB tool while AGS(2) is running.
  * Stopping the simulation (cancels all results)
  * Global Undo/Redo for parameters input boxes

... and multiple bugfixes.

Also one feature was removed:
  * Removed "Delimiter in the signature file 0) gap 1) tab 2) comma" configuration parameter as it's probably not changed plus it complicated our configuration file generation.

Known Bugs:
  * Finding the best n results aren't fully implemented yet.
  * UI fields are exported/imported as JSON instead of CSV (for Excel)
  * Burst Simulator might not finish when time is set over 1000.


Footnotes:
  1. Burst Simulator is the "blackbox" Delphi application that this Java application runs.