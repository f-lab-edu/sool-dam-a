#!/usr/bin/env bash

source ~/.bash_profile

APP_NAME=sooldama
CURRENT_PID=$(pgrep -f $APP_NAME)
if [ -z $CURRENT_PID ]; then
  echo ">>>> java process not found."
else
  echo ">>>> kill current PID: $CURRENT_PID."
  kill -15 $CURRENT_PID
  sleep 15
fi