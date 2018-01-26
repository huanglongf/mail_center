#!/bin/bash

APP_DIR=/home/yang/workspace_gpm/mail_center

CONF_DIR=$APP_DIR/conf
LIB_DIR=$APP_DIR/lib
BIN_DIR=$APP_DIR/bin
SQL_DIR=$APP_DIR/sql

CP=.
for f in $LIB_DIR/*.jar; do
  CP=$CP:$f
done

JAR=$APP_DIR/mail_center-1.0.jar

shopt -s expand_aliases

alias httpclient="java -cp $CP:$JAR -Dbase.home=${APP_DIR} come.gomeplus.mailCenter.command.HttpCommand"
alias jdbcclient="java -cp $CP:$JAR -Dbase.home=${APP_DIR} come.gomeplus.mailCenter.command.JdbcCommand"
alias export_excel="java -cp $CP:$JAR -Dbase.home=${APP_DIR} come.gomeplus.mailCenter.command.ExportExcel"
