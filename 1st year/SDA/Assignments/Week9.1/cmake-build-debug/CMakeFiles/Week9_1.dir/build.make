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
CMAKE_SOURCE_DIR = "F:\IT\University\1st year\SDA\Assignments\Week9.1"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "F:\IT\University\1st year\SDA\Assignments\Week9.1\cmake-build-debug"

# Include any dependencies generated for this target.
include CMakeFiles\Week9_1.dir\depend.make

# Include the progress variables for this target.
include CMakeFiles\Week9_1.dir\progress.make

# Include the compile flags for this target's objects.
include CMakeFiles\Week9_1.dir\flags.make

CMakeFiles\Week9_1.dir\main.cpp.obj: CMakeFiles\Week9_1.dir\flags.make
CMakeFiles\Week9_1.dir\main.cpp.obj: ..\main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="F:\IT\University\1st year\SDA\Assignments\Week9.1\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/Week9_1.dir/main.cpp.obj"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1426~1.288\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoCMakeFiles\Week9_1.dir\main.cpp.obj /FdCMakeFiles\Week9_1.dir\ /FS -c "F:\IT\University\1st year\SDA\Assignments\Week9.1\main.cpp"
<<

CMakeFiles\Week9_1.dir\main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Week9_1.dir/main.cpp.i"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1426~1.288\bin\Hostx86\x86\cl.exe > CMakeFiles\Week9_1.dir\main.cpp.i @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "F:\IT\University\1st year\SDA\Assignments\Week9.1\main.cpp"
<<

CMakeFiles\Week9_1.dir\main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Week9_1.dir/main.cpp.s"
	C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1426~1.288\bin\Hostx86\x86\cl.exe @<<
 /nologo /TP $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) /FoNUL /FAs /FaCMakeFiles\Week9_1.dir\main.cpp.s /c "F:\IT\University\1st year\SDA\Assignments\Week9.1\main.cpp"
<<

# Object files for target Week9_1
Week9_1_OBJECTS = \
"CMakeFiles\Week9_1.dir\main.cpp.obj"

# External object files for target Week9_1
Week9_1_EXTERNAL_OBJECTS =

Week9_1.exe: CMakeFiles\Week9_1.dir\main.cpp.obj
Week9_1.exe: CMakeFiles\Week9_1.dir\build.make
Week9_1.exe: CMakeFiles\Week9_1.dir\objects1.rsp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir="F:\IT\University\1st year\SDA\Assignments\Week9.1\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable Week9_1.exe"
	"C:\Program Files\JetBrains\CLion 2019.3.4\bin\cmake\win\bin\cmake.exe" -E vs_link_exe --intdir=CMakeFiles\Week9_1.dir --rc=C:\PROGRA~2\WI3CF2~1\10\bin\100183~1.0\x86\rc.exe --mt=C:\PROGRA~2\WI3CF2~1\10\bin\100183~1.0\x86\mt.exe --manifests  -- C:\PROGRA~2\MICROS~4\2019\COMMUN~1\VC\Tools\MSVC\1426~1.288\bin\Hostx86\x86\link.exe /nologo @CMakeFiles\Week9_1.dir\objects1.rsp @<<
 /out:Week9_1.exe /implib:Week9_1.lib /pdb:"F:\IT\University\1st year\SDA\Assignments\Week9.1\cmake-build-debug\Week9_1.pdb" /version:0.0  /machine:X86 /debug /INCREMENTAL /subsystem:console  kernel32.lib user32.lib gdi32.lib winspool.lib shell32.lib ole32.lib oleaut32.lib uuid.lib comdlg32.lib advapi32.lib 
<<

# Rule to build all files generated by this target.
CMakeFiles\Week9_1.dir\build: Week9_1.exe

.PHONY : CMakeFiles\Week9_1.dir\build

CMakeFiles\Week9_1.dir\clean:
	$(CMAKE_COMMAND) -P CMakeFiles\Week9_1.dir\cmake_clean.cmake
.PHONY : CMakeFiles\Week9_1.dir\clean

CMakeFiles\Week9_1.dir\depend:
	$(CMAKE_COMMAND) -E cmake_depends "NMake Makefiles" "F:\IT\University\1st year\SDA\Assignments\Week9.1" "F:\IT\University\1st year\SDA\Assignments\Week9.1" "F:\IT\University\1st year\SDA\Assignments\Week9.1\cmake-build-debug" "F:\IT\University\1st year\SDA\Assignments\Week9.1\cmake-build-debug" "F:\IT\University\1st year\SDA\Assignments\Week9.1\cmake-build-debug\CMakeFiles\Week9_1.dir\DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles\Week9_1.dir\depend

