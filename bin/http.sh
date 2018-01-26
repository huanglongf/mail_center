#!/bin/bash

source /home/yang/workspace_gpm/mail_center/bin/common.sh

httpclient -u http://api.md.bi.pro.gomeplus.com/api/vshop/gp? \
           -p mshopIds=21429 \
           -p date="2017-06-27" \
           -k testkey \
           -t data.list
