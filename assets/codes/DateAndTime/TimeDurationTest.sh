#/bin/bash -ex

# MAKE SURE the NTP is turned off before running the script, otherwise the Date Time of the system might be synchornized during this experiment. 
# For example, in Ubuntu 20.04, 
# `systemctl stop systemd-timesyncd`
# replace the classpath with the correct ones
java -Dfile.encoding=UTF-8 -classpath ~/Time:~/.m2/repository/com/google/guava/guava/18.0/guava-18.0.jar TimeDurationTest &
java_pid=$!

sleep 1

now=`date +%s` #epoch in milliseconds

newTime=`expr $now - 10`

echo "time before setting" `date`

sudo date -s @$newTime

echo "time after setting" `date`

wait $java_pid
