set SCRIPT_DIR=%~dp0
java -Xmx256M -XX:MaxPermSize=256M -noverify -jar "%SCRIPT_DIR%sbt-launch.jar" %*
