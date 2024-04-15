#!/usr/bin/env bash

# JAR 파일 경로 설정
REPOSITORY=/home/ubuntu/meritz
cd $REPOSITORY

APP_NAME=meritz
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

# 실행 중인 애플리케이션이 있으면 종료
if [[ -n $CURRENT_PID ]]; then
  echo "종료할 프로세스 PID: $CURRENT_PID"
  sudo kill $CURRENT_PID

  # 프로세스가 종료될 때까지 대기
  while ps -p $CURRENT_PID &>/dev/null; do
    sleep 1
  done
fi

# 애플리케이션 실행
nohup java -jar $JAR_PATH > output.log 2> error.log &