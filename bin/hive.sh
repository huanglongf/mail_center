#!/bin/bash

source /home/yang/workspace_gpm/mail_center/bin/common.sh

jdbcclient -file $SQL_DIR/hive.sql \
        -driver org.apache.hive.jdbc.HiveDriver \
        -url "jdbc:hive2://10.58.72.33:10000/" \
        -user hdfs \
        -pwd ""\
        -k hivekey

