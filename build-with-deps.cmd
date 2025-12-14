@REM Download dependencies and build Student Management System
@REM This script downloads required JAR files and compiles the project

@echo off
setlocal enabledelayedexpansion

if not defined JAVA_HOME (
    echo Error: JAVA_HOME environment variable is not set.
    exit /b 1
)

set JAVAC=%JAVA_HOME%\bin\javac.exe
set JAR=%JAVA_HOME%\bin\jar.exe
set LIBDIR=target\lib
set CLASSESDIR=target\classes

echo Creating lib directory...
if not exist %LIBDIR% mkdir %LIBDIR%
if not exist %CLASSESDIR% mkdir %CLASSESDIR%

echo.
echo ============================================================
echo Downloading dependencies...
echo ============================================================
echo.

REM Define dependency URLs
set DEPS[0]=https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar
set DEPS[1]=https://repo1.maven.org/maven2/ch/qos/logback/logback-classic/1.4.11/logback-classic-1.4.11.jar
set DEPS[2]=https://repo1.maven.org/maven2/ch/qos/logback/logback-core/1.4.11/logback-core-1.4.11.jar
set DEPS[3]=https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.2.0/mysql-connector-j-8.2.0.jar
set DEPS[4]=https://repo1.maven.org/maven2/org/postgresql/postgresql/42.6.0/postgresql-42.6.0.jar

REM Download each dependency
for /L %%i in (0,1,4) do (
    set URL=!DEPS[%%i]!
    for %%F in (!URL!) do set FILENAME=%%~nxF
    
    echo Downloading !FILENAME!...
    powershell -NoProfile -Command "try { Invoke-WebRequest -Uri '!URL!' -OutFile '%LIBDIR%\!FILENAME!' -TimeoutSec 30; Write-Host 'OK' } catch { Write-Host 'FAILED'; exit 1 }" >nul 2>&1
    
    if !errorlevel! neq 0 (
        echo Warning: Could not download !FILENAME!
        echo Using in-memory mode without database support
    )
)

echo.
echo ============================================================
echo Compiling source files...
echo ============================================================
echo.

REM Build classpath
set CLASSPATH=%CLASSESDIR%
for %%F in (%LIBDIR%\*.jar) do set CLASSPATH=!CLASSPATH!;%%F

REM Compile
"%JAVAC%" -d %CLASSESDIR% -cp "!CLASSPATH!" -encoding UTF-8 ^
    src\main\java\com\sms\App.java ^
    src\main\java\com\sms\dao\*.java ^
    src\main\java\com\sms\model\Student.java ^
    src\main\java\com\sms\model\StudentStatus.java ^
    src\main\java\com\sms\service\StudentService.java ^
    src\main\java\com\sms\service\ServiceException.java ^
    src\main\java\com\sms\ui\*.java ^
    src\main\java\com\sms\util\DatabaseUtil.java ^
    src\main\java\com\sms\util\ValidationUtil.java 2>&1

if %errorlevel% neq 0 (
    echo.
    echo Error during compilation!
    echo Please check the errors above.
    exit /b 1
)

echo.
echo ============================================================
echo Copying resources...
echo ============================================================
echo.

if not exist %CLASSESDIR%\com\sms mkdir %CLASSESDIR%\com\sms
xcopy src\main\resources\*.* %CLASSESDIR% /Y /Q >nul 2>&1

echo.
echo ============================================================
echo Creating JAR file...
echo ============================================================
echo.

REM Create manifest
(
    echo Manifest-Version: 1.0
    echo Main-Class: com.sms.App
) > %CLASSESDIR%\MANIFEST.MF

REM Create JAR
"%JAR%" cfm target\StudentManagementSystem.jar %CLASSESDIR%\MANIFEST.MF -C %CLASSESDIR% .

if %errorlevel% equ 0 (
    echo.
    echo ============================================================
    echo BUILD SUCCESSFUL!
    echo ============================================================
    echo.
    echo JAR created: target\StudentManagementSystem.jar
    echo.
    echo To run the application:
    echo   java -jar target\StudentManagementSystem.jar
    echo.
) else (
    echo Build failed!
    exit /b 1
)
