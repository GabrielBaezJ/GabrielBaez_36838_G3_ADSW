@echo off
cd /d "%~dp0"
if not exist out mkdir out
javac -encoding UTF-8 --release 17 -d out @sources.txt
if errorlevel 1 exit /b 1
java -cp out pruebas.PruebaCrudEstudiantes
pause
