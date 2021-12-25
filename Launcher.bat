@echo off

Rem -------------------------------
Rem Auto Compile and Execute script
Rem -------------------------------

Rem J'aimerai pouvoir supprimer bin
Rem avant compilation
Rem mais la commande rm -rf
Rem n'est pas trop apprécié
Rem par mon antivirus
Rem
Rem Ps: La version .bash 
Rem n'a pas ce problème

Rem Compile in bin
echo Compiling ...
javac -d bin Launcher.java

Rem La version windows demande
Rem l'autorisation de copier
Rem ce qui est enervant
Rem Par conséquent je ne copie
Rem que lorsque bin n'existe pas
if not exist "bin" (
    Rem Copying Resources
    echo Copying resources ...
    Xcopy /E /I .\resources .\bin\resources
)

Rem Execute in bin
echo Executing ...
cd bin
java Launcher

Rem Reset folder
cd ../