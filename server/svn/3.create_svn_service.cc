#!/bin/bash
# build this file in /etc/rc.d/init.d/svn
# chmod 755 /etc/rc.d/init.d/svn
# centos�¿����������������svn: service svn start(restart/stop)
SVN_HOME=/opt/svn/repository
if [ ! -f "/opt/svn/subversion/bin/svnserve" ]
then
    echo "svnserver startup: cannot start"
    exit
fi
case "$1" in
    start)
        echo "Starting svnserve..."
        /opt/svn/subversion/bin/svnserve -d --listen-port 3690 -r $SVN_HOME
        echo "Finished!"
        ;;
    stop)
        echo "Stoping svnserve..."
        killall svnserve
        echo "Finished!"
        ;;
    restart)
        $0 stop
        $0 start
        ;;
    *)
        echo "Usage: svn { start | stop | restart } "
        exit 1
esac