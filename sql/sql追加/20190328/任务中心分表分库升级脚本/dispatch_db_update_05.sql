SET FOREIGN_KEY_CHECKS=0;

UPDATE guiyu_callcenter.call_out_plan_0 O,guiyu_dispatch.plan_uuid_create P
set O.plan_uuid=P.planuuid_new
WHERE O.plan_uuid=P.planuuid_old  and O.plan_uuid is not null and P.planuuid_new>0;

UPDATE guiyu_callcenter.call_out_plan_1 O,guiyu_dispatch.plan_uuid_create P
set O.plan_uuid=P.planuuid_new
WHERE O.plan_uuid=P.planuuid_old  and O.plan_uuid is not null and P.planuuid_new>0;

-- tts_wav_his

UPDATE guiyu_robot.tts_wav_his O,guiyu_dispatch.plan_uuid_create P
set O.seq_id=P.planuuid_new
WHERE O.seq_id=P.planuuid_old  and O.seq_id is not null and P.planuuid_new>0;

-- billing_acct_charging_record
UPDATE guiyu_billing.billing_acct_charging_record O,guiyu_dispatch.plan_uuid_create P
set O.planuuid=P.planuuid_new
WHERE O.planuuid=P.planuuid_old  and O.planuuid is not null and P.planuuid_new>0;


ALTER TABLE guiyu_base.sys_organization
ADD COLUMN `usable`  int(1) NOT NULL DEFAULT 0 COMMENT '可用状态：0-不可用；1-可用' AFTER `botstence`;


UPDATE guiyu_base.sys_organization t SET t.usable = 1;

UPDATE push_records O,guiyu_dispatch.plan_uuid_create P
set O.planuuid=P.planuuid_new
WHERE O.planuuid=P.planuuid_old  and O.planuuid is not null and P.planuuid_new>0;












