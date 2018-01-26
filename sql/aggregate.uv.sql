SELECT count(DISTINCT(visitor_id)) uvA FROM KYLIN_REPORT_DB.session_behavior_channel_oms WHERE visit_date >='${start_date}' AND visit_date <='${end_date}'
