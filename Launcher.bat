@echo off

Rem -------------------------------
Rem Auto Compile and Execute script
Rem -------------------------------

Rem J'aimerai pouvoir supprimer bin
Rem avant compilation
Rem mais la commande rm -rf
Rem n'est pas trop apprécié
Rem par les antivirus

Rem Compile in bin
echo Compiling ...
javac -d bin Launcher.java

Rem Execute in bin
echo Executing ...
cd bin
java Launcher

Rem Reset folder
cd ../