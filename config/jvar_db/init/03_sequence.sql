-- Entry LABEL採番用のシーケンス。
-- VSUB-:nといった形式で文字列化して t_entry.label に設定する。
drop sequence if exists entry_label_seq;
create sequence entry_label_seq
    increment by 1
    maxvalue 999999999
    start with 1
    no cycle
;