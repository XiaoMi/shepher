# Copyright 2017 Xiaomi, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

#!/bin/bash

this="${BASH_SOURCE-$0}"
bin=`dirname "$this"`
bin=`cd "$bin">/dev/null; pwd`
BASE_DIR=`dirname "$bin"`
SHEPHERPID="$BASE_DIR/shepher.pid"

##set other variables
if [ "$JAVA_HOME" != "" ]; then
  JAVA="$JAVA_HOME/bin/java"
else
  JAVA=java
fi
KILL=/bin/kill

##export env variables
export BASE_DIR=$BASE_DIR

##set default shepher config file path
CFGFILE="$BASE_DIR/conf/application-default.properties,$BASE_DIR/conf/application-base.properties"

##set default shepher lib dir path
LIBDIR="$BASE_DIR/lib"

##set default shepher server ip:port
SERVERIP="127.0.0.1"
SERVERPORT="8089"

##parse arguments from commandline
while getopts 'a:c:l:i:p:' OPT;do
    case $OPT in
        c)
            CFGFILE="$OPTARG"
            ;;
        l)
            LIBDIR="$OPTARG"
            ;;
        i)
            SERVERIP="$OPTARG";;
        p)
            SERVERPORT="$OPTARG";;
    esac
done
shift $((OPTIND-1))

##set action: start/stop/restart/status
ACTION=$@

#verify action
case $ACTION in
start)
    echo  -n "Starting shepher..."
    echo "Using config: $CFGFILE" >&2
    echo "Using lib dir: $LIBDIR" >&2
    echo "Server ip: $SERVERIP" >&2
    echo "Server port: $SERVERPORT" >&2

    if [ -f $SHEPHERPID ]; then
      if kill -0 `cat $SHEPHERPID` 1> /dev/null 2>&1; then
         echo $command already running as process `cat $SHEPHERPID`. 
         exit 0
      fi
    fi
    nohup $JAVA -jar $LIBDIR/shepher-web-1.0.jar "--spring.config.location=$CFGFILE" "-Djava.ext.dirs=$LIBDIR" 2>&1 < /dev/null&
    if [ $? -eq 0 ]
    then
      if /bin/echo -n $! > "$SHEPHERPID"
      then
        curl $SERVERIP:$SERVERPORT 1>/dev/null 2>&1
        ret=$?
        while [ $ret -ne '0' ]
        do
            sleep 5
            curl $SERVERIP:$SERVERPORT 1>/dev/null 2>&1
            ret=$?
        done
        echo STARTED
      else
        echo FAILED TO WRITE PID
        exit 1
      fi
    else
      echo SERVER DID NOT START
      exit 1
    fi
    ;;
start-foreground)
    SHEPHER_CMD="exec $JAVA"
    $SHEPHER_CMD -jar $LIBDIR/shepher-web-1.0.jar "--spring.config.location=$CFGFILE" "-Djava.ext.dirs=$LIBDIR" 
    ;;
stop)
    echo -n "Stopping shepher..."
    if [ ! -f "$SHEPHERPID" ]
    then
      echo "no shepher to stop (could not find file $SHEPHERPID)"
    else
      $KILL -9 $(cat "$SHEPHERPID")
      rm "$SHEPHERPID"
      echo STOPPED
    fi
    ;;
restart)
    shift
    sh "$0" stop ${@}
    sleep 3
    sh "$0" start ${@}
    ;;
status)
    if [ -f $SHEPHERPID ]; then
      if kill -0 `cat $SHEPHERPID` 1> /dev/null 2>&1; then
        echo "running" 
        exit 0
      fi
    fi

    echo "Error contacting service. It is probably not running."
    exit 1
    ;;
*)

    echo "Usage: $0 [OPTIONS] {start|start-foreground|stop|restart|status} \n\
          OPTIONS:\n\
          -c Shepher config file path\n\
          -l Shepher lib dir path\n\
          -i Shepher server ip\n\
          -p Shepher server port" >&2
esac
