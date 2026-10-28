@echo off
echo ============================================
echo   Maze Solver - Compile and Run
echo ============================================
echo.

:: Clean old build
if exist out rmdir /s /q out
mkdir out

echo [1/2] Compiling...
javac -d out src\mazesolver\model\*.java src\mazesolver\solver\*.java src\mazesolver\gui\*.java src\mazesolver\Main.java

if %ERRORLEVEL% neq 0 (
    echo.
    echo  *** Compilation FAILED. Check errors above. ***
    pause
    exit /b 1
)

echo [2/2] Running...
echo.
java -cp out mazesolver.Main

pause
