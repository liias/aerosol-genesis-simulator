## Prerequisits ##
  * Java 6+
  * Maven
  * Windows/Linux/Mac
  * SVN


To download the source code from our SVN repository, compile it and then run it, execute these lines:
```
svn checkout http://aerosol-genesis-simulator.googlecode.com/svn/trunk/ ags
cd ags
mvn compile exec:exec
```


Also read about [CodeStyle](CodeStyle.md) and [CreatingUI](CreatingUI.md)