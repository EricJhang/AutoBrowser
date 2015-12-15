

echo ============================================
echo Push package_list
echo ============================================
set currentPath=%cd%
set Local=%currentPath%\weburl_list.txt
set Local_a=%currentPath%\youtubeList.txt
set Local_b=%currentPath%\youkuList.txt
set package_Folder=/sdcard/

adb push %Local% %package_Folder%
adb push %Local_a% %package_Folder%
adb push %Local_b% %package_Folder%
timeout 1
pause