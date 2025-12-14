@echo off
echo ==========================================
echo Deploying Student Management System (Console)
echo ==========================================

REM 1. Build the project
call build-with-deps.cmd
if %errorlevel% neq 0 (
    echo Build failed. Deployment aborted.
    pause
    exit /b 1
)

REM 2. Create Distribution Directory
echo.
echo Creating 'dist' directory...
if exist dist rd /s /q dist
mkdir dist
mkdir dist\lib
mkdir dist\config
mkdir dist\database
mkdir dist\logs

REM 3. Copy Application Files
echo Copying files...
copy target\StudentManagementSystem.jar dist\ >nul
copy target\lib\*.jar dist\lib\ >nul

REM 4. Copy Configuration
REM We assume the user might want to edit this, so we copy it to a config folder
copy src\main\resources\application.properties dist\config\ >nul

REM 5. Copy Database Scripts
copy database\*.sql dist\database\ >nul

REM 6. Copy Documentation
copy FINAL_INSTRUCTIONS.md dist\README.txt >nul

REM 7. Create Launch Script
echo Creating launch script...
(
    echo @echo off
    echo title Student Management System
    echo echo Starting Student Management System...
    echo.
    echo REM Add config folder to classpath to override internal properties
    echo java -cp "config;StudentManagementSystem.jar;lib\*" com.sms.App
    echo pause
) > dist\run_app.bat

echo.
echo ==========================================
echo DEPLOYMENT SUCCESSFUL
echo ==========================================
echo.
echo A standalone version of the application has been created in the 'dist' folder.
echo You can zip this folder and share it.
echo.
echo To run the deployed app:
echo   cd dist
echo   run_app.bat
echo.
pause
