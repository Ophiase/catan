# -------------------------------
# Auto Compile and Execute script
# -------------------------------

# Compile in bin
echo Compiling ...
javac -d bin Launcher.java

# Execute in bin
echo Executing ...
cd bin
java Launcher

# Reset folder
cd ../