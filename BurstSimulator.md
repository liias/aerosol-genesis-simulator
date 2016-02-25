This page includes instructions to get the latest source code for the Pascal/Delphi source and how to compile it.

## Download Free Pascal Compiler ##

If you use Ubuntu, install it with command
```
sudo apt-get install fpc
```

If you use any other OS you can download the right versopm for your OS and architecture from http://www.freepascal.org/download.var.

Or use these direct links for version 2.6.0:
[win32](http://sourceforge.net/projects/freepascal/files/Win32/2.6.0/fpc-2.6.0.i386-win32.exe/download).

## Download the source code for Burst Simulator ##
You can download Burst Simulator package which includes the source code from here: http://ael.physic.ut.ee/tammet/burstsimulator/Burstsimulator.zip or download latest version which we support from our [downloads Section](http://code.google.com/p/aerosol-genesis-simulator/downloads/detail?name=burst_simulator.dpr)

## Compile ##
You need to tell Free Pascal Compiler that the source code is in Deplhi format, so run this command to produce the binary executable:
```
fpc -Sd burst_simulator.dpr
```