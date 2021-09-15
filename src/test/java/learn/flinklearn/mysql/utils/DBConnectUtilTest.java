package learn.flinklearn.mysql.utils;

import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/15 6:22 下午
 */
public class DBConnectUtilTest extends TestCase {

    String data = "{\"ipcc_recordnos\":0,\"shop_code\":\"\",\"ipcc_commit\":1,\"starttime\":1594699587754,\"type\":0,\"evaluation\":1,\"ipcc_callid\":\"\",\"push_msg_id\":0,\"staff_id\":4,\"ipcc_sstime\":\"\",\"first_end_direction\":0,\"file_recognizer_task_id\":0,\"id\":1,\"fromtitle\":\"未知页面名称\",\"quality_remark\":\"\",\"fromstaff\":0,\"fromsubtype\":\"Win32 - Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36\",\"lastcontenttime\":1594699592255,\"treated_time\":0,\"biz_code\":\"\",\"ipcc_charge_begin_time\":0,\"send_worksheet\":0,\"first_reply_cost\":0,\"re_consult_time\":0,\"avg_answer_cost\":0,\"parent_corp_id\":0,\"client_first_message_time\":1594699592209,\"fromgroup\":0,\"ipcc_endtime\":\"\",\"connection_end_time\":0,\"ipcc_recordid\":\"\",\"evaluation_remarks\":\"test46199\",\"version\":60,\"processing_complete_time\":0,\"entryid\":0,\"push_msg_status\":0,\"quality_time\":0,\"user_id\":1,\"quality_forbidden\":0,\"fromtype\":\"WEB\",\"close_reason\":4,\"vip_level\":0,\"corp_id\":4,\"status\":4,\"ipcc_key_records\":\"\",\"push_batch_id\":0,\"ipcc_direction\":1,\"ipcc_ivr\":\"\",\"evaluation_type\":2,\"connection_begine_time\":0,\"platformid\":0,\"file_recognizer_status\":0,\"related_session_id\":0,\"user_visit_range\":14826434,\"alarm_times\":1,\"catalogue2\":0,\"fromip\":\"172.25.242.163\",\"catalogue1\":0,\"lastmsgtype\":1,\"ipcc_from\":\"\",\"session_type\":0,\"catalogue3\":0,\"guest_feature\":\"\",\"ipcc_status\":0,\"ipcc_to\":\"\",\"resolved\":1,\"ipcc_callduration\":0,\"related_session_type\":0,\"push_batch_send_ids\":\"\",\"createtime\":1594699587754,\"address\":\"[局域网, 局域网, , , ]\",\"ipcc_starttime\":\"\",\"frompage\":\"\",\"ipcc_ivr_id\":0,\"quality_score\":0.0,\"endtime\":1594704920023,\"ipcc_recordurl\":\"\",\"staff_first_reply_time\":0,\"lastcontent\":\"fadsf\",\"foreign_id\":\"\",\"wait_in_queue_time\":0,\"visit_times\":0,\"interaction\":0,\"assign_staff_time\":0,\"platformtype\":1,\"ipcc_setime\":\"\",\"quality_staff_id\":0,\"source_id\":\"\",\"updatetime\":1600964588860,\"ipcc_db_id\":0,\"remarks\":\"\"}";

    public void testParseSqlByData() {
        JSONObject jsonObject = JSONObject.parseObject(data);
        String sql = DBConnectUtil.parseSqlByData(jsonObject.keySet(), "a", "q");
        System.out.println("sql = " + sql);
    }
}