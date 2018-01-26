#!/bin/bash

source /home/yang/workspace_gpm/mail_center/bin/common.sh

jdbcclient -file $SQL_DIR/jdbc.sql \
        -driver com.mysql.jdbc.Driver \
        -url "jdbc:mysql://10.125.31.220:8806/dataplatform?useUnicode=true&characterEncoding=UTF8" \
        -user develop \
        -pwd ZQ2yGUJfE2 \
        -k mysqlkey

