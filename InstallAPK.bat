
echo ============================================
echo Install REF APKs
echo ============================================


for %%i in (*.apk) do .\adb install -r %%i

timeout 1