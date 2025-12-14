@REM Build Student Management System using javac (no Maven required)
@REM This script compiles the Java source files and creates a runnable JAR

@echo off
setlocal enabledelayedexpansion

REM Check if JAVA_HOME is set
if not defined JAVA_HOME (
    echo Error: JAVA_HOME environment variable is not set.
    echo Please install Java and set JAVA_HOME to your JDK installation directory.
    echo.
    echo Example for Windows:
    echo   set JAVA_HOME=C:\Program Files\Java\jdk-17.0.1
    exit /b 1
)

set JAVAC=%JAVA_HOME%\bin\javac.exe
set JAR=%JAVA_HOME%\bin\jar.exe

if not exist "%JAVAC%" (
    echo Error: javac not found at %JAVAC%
    echo Please check your JAVA_HOME setting.
    exit /b 1
)

echo Building Student Management System...
echo.

REM Create output directories
if not exist target mkdir target
if not exist target\classes mkdir target\classes

REM Compile main source files
echo [1/4] Compiling source files...
"%JAVAC%" -d target\classes -encoding UTF-8 ^
    src\main\java\com\sms\*.java ^
    src\main\java\com\sms\dao\*.java ^
    src\main\java\com\sms\model\*.java ^
    src\main\java\com\sms\service\*.java ^
    src\main\java\com\sms\ui\*.java ^
    src\main\java\com\sms\util\*.java

if %errorlevel% neq 0 (
    echo Error: Compilation failed!
    exit /b 1
)

REM Copy resources
echo [2/4] Copying resources...
if not exist target\classes\com\sms mkdir target\classes\com\sms
xcopy src\main\resources\*.* target\classes /Y >nul 2>&1

REM Create manifest file
echo [3/4] Creating manifest...
(
    echo Manifest-Version: 1.0
    echo Main-Class: com.sms.App
    echo Class-Path: . mysql-connector-java-8.0.33.jar postgresql-42.6.0.jar slf4j-api-2.0.9.jar logback-classic-1.4.11.jar logback-core-1.4.11.jar
) > target\MANIFEST.MF

REM Create JAR file
echo [4/4] Creating JAR file...
"%JAR%" cfm target\StudentManagementSystem.jar target\MANIFEST.MF -C target\classes .

if %errorlevel% equ 0 (
    echo.
    echo ============================================================
    echo BUILD SUCCESSFUL
    echo ============================================================
    echo JAR created: target\StudentManagementSystem.jar
    echo.
    echo To run the application:
    echo   java -jar target\StudentManagementSystem.jar
    echo.
) else (
    echo Error: JAR creation failed!
    exit /b 1
)
