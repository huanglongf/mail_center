#!/bin/bash

source /home/yang/workspace_gpm/mail_center/bin/common.sh

jdbcclient -file $SQL_DIR/kylin.sql \
        -driver org.apache.kylin.jdbc.Driver \
        -url "jdbc:kylin://10.58.72.29:7070/session_thermodynamic_chart" \
        -user ADMIN \
        -pwd KYLIN \
        -k kylinkey

