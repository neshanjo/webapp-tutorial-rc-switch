set /p pw=<password.txt
"c:\Program Files\7-Zip\7z.exe" a %1.zip %1 -P%pw%