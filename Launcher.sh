#!/bin/bash

# -------------------------------
# Auto Compile and Execute script
# -------------------------------

do_compile_flag='true'
do_copy_flag='true'

print_usage() {
  printf "-a ignore compilation"
  printf "-b ignore copying files"
}

while getopts 'ab' flag; do
  case "${flag}" in
    a) do_compile_flag='false' ;;
    b) do_copy_flag='false' ;;
    *) print_usage
       exit 1 ;;
  esac
done

# --------------------------------

# Clean bin
function function_clean() {
    if [ ! -f "./bin" ]; then
        echo Clean ...
        rm -r bin
    fi
}

# Compile in bin
function function_compile {
    echo Compiling ...
    javac -d bin Launcher.java
}

# Copying resources
function function_copying {
    echo Copying resources ...
    cp -r ./resources ./bin/resources
}

# Execute in bin
function function_execute {
    echo Executing ...
    cd bin
    java Launcher

    # Reset folder
    cd ../
}

# --------------------------------

if $do_compile_flag && $do_copy_flag; then
    function_clean
fi

if $do_compile_flag; then
    function_compile
fi

if $do_copy_flag; then
    function_copying
fi

function_execute
