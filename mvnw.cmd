@REM Maven wrapper batch script for Windows
@REM This script enables building without Maven installed

@echo off
setlocal enabledelayedexpansion

REM Check if JAVA_HOME is set
if not defined JAVA_HOME (
    echo Error: JAVA_HOME environment variable is not set.
    echo Please install Java and set JAVA_HOME to your JDK installation directory.
    exit /b 1
)

REM Parse command line arguments
set MAVEN_ARGS=%*

REM Check if Maven is available in PATH
where mvn >nul 2>&1
if %errorlevel% equ 0 (
    echo Using Maven from PATH...
    mvn %MAVEN_ARGS%
    exit /b %errorlevel%
)

REM Maven not in PATH - provide instructions
echo.
echo ============================================================
echo Maven not found in PATH
echo ============================================================
echo.
echo To build this project, you need Maven installed.
echo.
echo Option 1: Install Maven globally
echo   - Download from: https://maven.apache.org/download.cgi
echo   - Extract to a folder (e.g., C:\Apache\maven)
echo   - Add [installation]\bin to your PATH environment variable
echo   - Restart your terminal and run this script again
echo.
echo Option 2: Use Java's built-in compiler (javac)
echo   - See BUILD_WITH_JAVAC.md for instructions
echo.
echo Option 3: Use an IDE
echo   - Import this project into IntelliJ IDEA, Eclipse, or VS Code
echo   - IDE will handle compilation and execution
echo.
exit /b 1
