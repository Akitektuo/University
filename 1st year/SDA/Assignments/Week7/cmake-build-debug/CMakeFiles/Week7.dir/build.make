# CMAKE generated file: DO NOT EDIT!
# Generated by "NMake Makefiles" Generator, CMake Version 3.16

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE
NULL=nul
!ENDIF
SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = "C:\Program Files\JetBrains\CLion 2019.3.4\bin\cmake\win\bin\cmake.exe"

# The command to remove a file.
RM = "C:\Program Files\JetBrains\CLion 2019.3.4\bin\cmake\win\bin\cmake.exe" -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = "F:\IT\University\1st year\SDA\Assignments\Week7"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "F:\IT\University\1st year\SDA\Assignments\Week7\cmake-build-debug"

# Include any dependencies generated for this target.
include CMakeFiles\Week7.dir\depend.make

# Include the progress variables for this target.
include CMakeFiles\Week7.dir\progress.make

# Include the compile flags for this target's objects.
include CMakeFiles\Week7.dir\flags.make

CMakeFiles\Week7.dir\App.cpp.obj: CMakeFiles\Week7.dir\flags.make
CMakeFiles\Week7.dir\App.cpp.obj: ..\App.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="F:\IT\University\1st year\SDA\Assignments\Week7\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/Week7.dir/App.cpp.obj"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoCMakeFiles\Week7.dir\App.cpp.obj /FdCMakeFiles\Week7.dir\ /FS -c "F:\IT\University\1st year\SDA\Assignments\Week7\App.cpp"
<<

CMakeFiles\Week7.dir\App.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Week7.dir/App.cpp.i"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe > CMakeFiles\Week7.dir\App.cpp.i @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "F:\IT\University\1st year\SDA\Assignments\Week7\App.cpp"
<<

CMakeFiles\Week7.dir\App.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Week7.dir/App.cpp.s"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoNUL /FAs /FaCMakeFiles\Week7.dir\App.cpp.s /c "F:\IT\University\1st year\SDA\Assignments\Week7\App.cpp"
<<

CMakeFiles\Week7.dir\ExtendedTest.cpp.obj: CMakeFiles\Week7.dir\flags.make
CMakeFiles\Week7.dir\ExtendedTest.cpp.obj: ..\ExtendedTest.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="F:\IT\University\1st year\SDA\Assignments\Week7\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/Week7.dir/ExtendedTest.cpp.obj"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoCMakeFiles\Week7.dir\ExtendedTest.cpp.obj /FdCMakeFiles\Week7.dir\ /FS -c "F:\IT\University\1st year\SDA\Assignments\Week7\ExtendedTest.cpp"
<<

CMakeFiles\Week7.dir\ExtendedTest.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Week7.dir/ExtendedTest.cpp.i"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe > CMakeFiles\Week7.dir\ExtendedTest.cpp.i @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "F:\IT\University\1st year\SDA\Assignments\Week7\ExtendedTest.cpp"
<<

CMakeFiles\Week7.dir\ExtendedTest.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Week7.dir/ExtendedTest.cpp.s"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoNUL /FAs /FaCMakeFiles\Week7.dir\ExtendedTest.cpp.s /c "F:\IT\University\1st year\SDA\Assignments\Week7\ExtendedTest.cpp"
<<

CMakeFiles\Week7.dir\ShortTest.cpp.obj: CMakeFiles\Week7.dir\flags.make
CMakeFiles\Week7.dir\ShortTest.cpp.obj: ..\ShortTest.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="F:\IT\University\1st year\SDA\Assignments\Week7\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_3) "Building CXX object CMakeFiles/Week7.dir/ShortTest.cpp.obj"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoCMakeFiles\Week7.dir\ShortTest.cpp.obj /FdCMakeFiles\Week7.dir\ /FS -c "F:\IT\University\1st year\SDA\Assignments\Week7\ShortTest.cpp"
<<

CMakeFiles\Week7.dir\ShortTest.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Week7.dir/ShortTest.cpp.i"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe > CMakeFiles\Week7.dir\ShortTest.cpp.i @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "F:\IT\University\1st year\SDA\Assignments\Week7\ShortTest.cpp"
<<

CMakeFiles\Week7.dir\ShortTest.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Week7.dir/ShortTest.cpp.s"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoNUL /FAs /FaCMakeFiles\Week7.dir\ShortTest.cpp.s /c "F:\IT\University\1st year\SDA\Assignments\Week7\ShortTest.cpp"
<<

CMakeFiles\Week7.dir\SMMIterator.cpp.obj: CMakeFiles\Week7.dir\flags.make
CMakeFiles\Week7.dir\SMMIterator.cpp.obj: ..\SMMIterator.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="F:\IT\University\1st year\SDA\Assignments\Week7\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_4) "Building CXX object CMakeFiles/Week7.dir/SMMIterator.cpp.obj"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoCMakeFiles\Week7.dir\SMMIterator.cpp.obj /FdCMakeFiles\Week7.dir\ /FS -c "F:\IT\University\1st year\SDA\Assignments\Week7\SMMIterator.cpp"
<<

CMakeFiles\Week7.dir\SMMIterator.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Week7.dir/SMMIterator.cpp.i"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe > CMakeFiles\Week7.dir\SMMIterator.cpp.i @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "F:\IT\University\1st year\SDA\Assignments\Week7\SMMIterator.cpp"
<<

CMakeFiles\Week7.dir\SMMIterator.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Week7.dir/SMMIterator.cpp.s"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoNUL /FAs /FaCMakeFiles\Week7.dir\SMMIterator.cpp.s /c "F:\IT\University\1st year\SDA\Assignments\Week7\SMMIterator.cpp"
<<

CMakeFiles\Week7.dir\SortedMultiMap.cpp.obj: CMakeFiles\Week7.dir\flags.make
CMakeFiles\Week7.dir\SortedMultiMap.cpp.obj: ..\SortedMultiMap.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="F:\IT\University\1st year\SDA\Assignments\Week7\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_5) "Building CXX object CMakeFiles/Week7.dir/SortedMultiMap.cpp.obj"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoCMakeFiles\Week7.dir\SortedMultiMap.cpp.obj /FdCMakeFiles\Week7.dir\ /FS -c "F:\IT\University\1st year\SDA\Assignments\Week7\SortedMultiMap.cpp"
<<

CMakeFiles\Week7.dir\SortedMultiMap.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Week7.dir/SortedMultiMap.cpp.i"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe > CMakeFiles\Week7.dir\SortedMultiMap.cpp.i @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "F:\IT\University\1st year\SDA\Assignments\Week7\SortedMultiMap.cpp"
<<

CMakeFiles\Week7.dir\SortedMultiMap.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Week7.dir/SortedMultiMap.cpp.s"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoNUL /FAs /FaCMakeFiles\Week7.dir\SortedMultiMap.cpp.s /c "F:\IT\University\1st year\SDA\Assignments\Week7\SortedMultiMap.cpp"
<<

# Object files for target Week7
Week7_OBJECTS = \
"CMakeFiles\Week7.dir\App.cpp.obj" \
"CMakeFiles\Week7.dir\ExtendedTest.cpp.obj" \
"CMakeFiles\Week7.dir\ShortTest.cpp.obj" \
"CMakeFiles\Week7.dir\SMMIterator.cpp.obj" \
"CMakeFiles\Week7.dir\SortedMultiMap.cpp.obj"

# External object files for target Week7
Week7_EXTERNAL_OBJECTS =

Week7.exe: CMakeFiles\Week7.dir\App.cpp.obj
Week7.exe: CMakeFiles\Week7.dir\ExtendedTest.cpp.obj
Week7.exe: CMakeFiles\Week7.dir\ShortTest.cpp.obj
Week7.exe: CMakeFiles\Week7.dir\SMMIterator.cpp.obj
Week7.exe: CMakeFiles\Week7.dir\SortedMultiMap.cpp.obj
Week7.exe: CMakeFiles\Week7.dir\build.make
Week7.exe: CMakeFiles\Week7.dir\objects1.rsp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir="F:\IT\University\1st year\SDA\Assignments\Week7\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_6) "Linking CXX executable Week7.exe"
	"C:\Program Files\JetBrains\CLion 2019.3.4\bin\cmake\win\bin\cmake.exe" -E vs_link_exe --intdir=CMakeFiles\Week7.dir --rc=C:\PROGRA~2\WI3CF2~1\10\bin\100183~1.0\x86\rc.exe --mt=C:\PROGRA~2\WI3CF2~1\10\bin\100183~1.0\x86\mt.exe --manifests  -- C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1425~1.286\bin\Hostx86\x86\link.exe /nologo @CMakeFiles\Week7.dir\objects1.rsp @<<
 /out:Week7.exe /implib:Week7.lib /pdb:"F:\IT\University\1st year\SDA\Assignments\Week7\cmake-build-debug\Week7.pdb" /version:0.0  /machine:X86 /debug /INCREMENTAL /subsystem:console  kernel32.lib user32.lib gdi32.lib winspool.lib shell32.lib ole32.lib oleaut32.lib uuid.lib comdlg32.lib advapi32.lib 
<<

# Rule to build all files generated by this target.
CMakeFiles\Week7.dir\build: Week7.exe

.PHONY : CMakeFiles\Week7.dir\build

CMakeFiles\Week7.dir\clean:
	$(CMAKE_COMMAND) -P CMakeFiles\Week7.dir\cmake_clean.cmake
.PHONY : CMakeFiles\Week7.dir\clean

CMakeFiles\Week7.dir\depend:
	$(CMAKE_COMMAND) -E cmake_depends "NMake Makefiles" "F:\IT\University\1st year\SDA\Assignments\Week7" "F:\IT\University\1st year\SDA\Assignments\Week7" "F:\IT\University\1st year\SDA\Assignments\Week7\cmake-build-debug" "F:\IT\University\1st year\SDA\Assignments\Week7\cmake-build-debug" "F:\IT\University\1st year\SDA\Assignments\Week7\cmake-build-debug\CMakeFiles\Week7.dir\DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles\Week7.dir\depend

