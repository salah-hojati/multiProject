@echo off
setlocal enabledelayedexpansion

set SERVER=82.115.24.110
set USER=administrator
set PASSWORD=Yonn-mq1#9d7rHUn
set PLINK=C:\Tools\plink.exe
set PORT=9090

:loop
echo Connecting to %SERVER% at %time% ...
start "" /wait "%PLINK%" -ssh %USER%@%SERVER% -pw %PASSWORD% -batch -D %PORT%
echo Connection lost at %time%. Reconnecting in 3 seconds...
timeout /t 20 >nul
goto loop
