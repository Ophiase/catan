@echo off

Rem --------------
Rem Compile Script
Rem --------------

Rem J'aimerai pouvoir supprimer bin
Rem avant compilation
Rem mais la commande rm -rf
Rem n'est pas trop apprécié
Rem par mon antivirus

Rem Compile in bin
echo Compiling ...
javac -d bin Launcher.java

Rem Copying Resources
echo Copying resources ...
Xcopy /E /I .\resources .\bin\resources