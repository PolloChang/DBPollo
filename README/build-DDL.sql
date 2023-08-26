
CREATE SEQUENCE hibernate_sequence MINVALUE 1000;

-- 建立使用者資料表

CREATE TABLE bs_user (
    id NUMERIC(22,0) NOT NULL,
    version INTEGER,
    man_created VARCHAR(20) NOT NULL,
    date_created TIMESTAMP DEFAULT current_timestamp NOT NULL,
    man_last_updated VARCHAR(50),
    last_updated TIMESTAMP,
    note VARCHAR(50),
    username VARCHAR(20) NOT NULL,
    password VARCHAR(500) NOT NULL,
    enabled boolean DEFAULT true NOT NULL,
    account_expired boolean DEFAULT false NOT NULL,
    account_locked boolean DEFAULT false NOT NULL,
    password_expired boolean DEFAULT false NOT NULL,
    email VARCHAR(100),
    first_name VARCHAR(10),
    last_name VARCHAR(50) NOT NULL,
    CONSTRAINT bs_user_pk PRIMARY KEY (id)
);
COMMENT ON TABLE bs_user IS '系統使用者帳號';
COMMENT ON COLUMN bs_user.id IS 'user_id';
COMMENT ON COLUMN bs_user.version IS '資料版本';
COMMENT ON COLUMN bs_user.man_created IS '建檔人員';
COMMENT ON COLUMN bs_user.date_created IS '建檔時間';
COMMENT ON COLUMN bs_user.man_last_updated IS '最後異動人員';
COMMENT ON COLUMN bs_user.last_updated IS '最後異動時間';
COMMENT ON COLUMN bs_user.note IS '資料註記';
COMMENT ON COLUMN bs_user.username IS '帳號';
COMMENT ON COLUMN bs_user.password IS '密碼';
COMMENT ON COLUMN bs_user.enabled IS '帳戶是否可用';
COMMENT ON COLUMN bs_user.account_expired IS '帳戶是否過期';
COMMENT ON COLUMN bs_user.account_locked IS '帳戶是否被鎖定';
COMMENT ON COLUMN bs_user.password_expired IS '密碼是否過期';
COMMENT ON COLUMN bs_user.email IS '電子信箱';
COMMENT ON COLUMN bs_user.first_name IS '姓氏';
COMMENT ON COLUMN bs_user.last_name IS '名字';


CREATE TABLE bs_user_role (
    id NUMERIC(22,0) NOT NULL,
    version INTEGER,
    man_created VARCHAR(20) NOT NULL,
    date_created TIMESTAMP DEFAULT current_timestamp NOT NULL,
    man_last_updated VARCHAR(50),
    last_updated TIMESTAMP,
    note VARCHAR(50),
    user_id NUMERIC(22,0) NOT NULL,
    role_id NUMERIC(22,0) NOT NULL,
    CONSTRAINT bs_user_role_pk PRIMARY KEY (id)
);;
COMMENT ON TABLE bs_user_role IS '帳號與角色對表';
COMMENT ON COLUMN bs_user_role.id IS 'user_id';
COMMENT ON COLUMN bs_user_role.version IS '資料版本';
COMMENT ON COLUMN bs_user_role.man_created IS '建檔人員';
COMMENT ON COLUMN bs_user_role.date_created IS '建檔時間';
COMMENT ON COLUMN bs_user_role.man_last_updated IS '最後異動人員';
COMMENT ON COLUMN bs_user_role.last_updated IS '最後異動時間';
COMMENT ON COLUMN bs_user_role.note IS '資料註記';
COMMENT ON COLUMN bs_user_role.user_id IS '帳號';
COMMENT ON COLUMN bs_user_role.role_id IS '角色';


CREATE TABLE bs_role (
    id NUMERIC(22,0) NOT NULL,
    version INTEGER,
    man_created VARCHAR(20) NOT NULL,
    date_created TIMESTAMP DEFAULT current_timestamp NOT NULL,
    man_last_updated VARCHAR(50),
    last_updated TIMESTAMP,
    note VARCHAR(50),
    authority CHAR(10) NOT NULL,
    authority_name VARCHAR(20) NOT NULL,
    CONSTRAINT bs_role_pk PRIMARY KEY (id)
);;
COMMENT ON TABLE bs_role IS '系統角色';
COMMENT ON COLUMN bs_role.id IS 'user_id';
COMMENT ON COLUMN bs_role.version IS '資料版本';
COMMENT ON COLUMN bs_role.man_created IS '建檔人員';
COMMENT ON COLUMN bs_role.date_created IS '建檔時間';
COMMENT ON COLUMN bs_role.man_last_updated IS '最後異動人員';
COMMENT ON COLUMN bs_role.last_updated IS '最後異動時間';
COMMENT ON COLUMN bs_role.note IS '資料註記';
COMMENT ON COLUMN bs_role.authority IS '角色';
COMMENT ON COLUMN bs_role.authority_name IS '角色說明';



CREATE TABLE bs_request_map (
    id NUMERIC(22,0) NOT NULL,
    version INTEGER,
    man_created VARCHAR(20) NOT NULL,
    date_created TIMESTAMP DEFAULT current_timestamp NOT NULL,
    man_last_updated VARCHAR(20),
    last_updated TIMESTAMP,
    note VARCHAR(50),
    app_no CHAR(5) NOT NULL,
    app_name VARCHAR(10) NOT NULL,
    idx INTEGER DEFAULT 0 NOT NULL,
    is_show BOOLEAN DEFAULT true NOT NULL,
    config_attribute VARCHAR(100) NOT NULL,
    http_method VARCHAR(50),
    url VARCHAR(50) NOT NULL,
    controller varchar(10),
    action varchar(10),
    CONSTRAINT bs_request_map_pk PRIMARY KEY (id)
);;
COMMENT ON TABLE bs_request_map IS '程式清單暨系統角色可使用程式對應檔';
COMMENT ON COLUMN bs_request_map.id IS 'pk';
COMMENT ON COLUMN bs_request_map.version IS '資料版本';
COMMENT ON COLUMN bs_request_map.man_created IS '建檔人員';
COMMENT ON COLUMN bs_request_map.date_created IS '建檔時間';
COMMENT ON COLUMN bs_request_map.man_last_updated IS '最後異動人員';
COMMENT ON COLUMN bs_request_map.last_updated IS '最後異動時間';
COMMENT ON COLUMN bs_request_map.note IS '資料註記';
COMMENT ON COLUMN bs_request_map.app_no IS '程式代號:前面兩個英文字母加三個阿拉伯數字組成[EN][###]';
COMMENT ON COLUMN bs_request_map.app_name IS '程式名稱';
COMMENT ON COLUMN bs_request_map.idx IS '排序';
COMMENT ON COLUMN bs_request_map.is_show IS '顯示';
COMMENT ON COLUMN bs_request_map.config_attribute IS '可使用角色';
COMMENT ON COLUMN bs_request_map.http_method IS '限制http_method';
COMMENT ON COLUMN bs_request_map.url IS 'url';
COMMENT ON COLUMN bs_request_map.controller IS 'controller';
COMMENT ON COLUMN bs_request_map.controller IS 'action';



CREATE TABLE bs_app_list_group (
    id NUMERIC(22,0) NOT NULL,
    version INTEGER,
    man_created VARCHAR(20) NOT NULL,
    date_created TIMESTAMP DEFAULT current_timestamp NOT NULL,
    man_last_updated VARCHAR(20),
    last_updated TIMESTAMP,
    note VARCHAR(50),
    app_no CHAR(10) NOT NULL,
    app_group_no CHAR(10) NOT NULL,
    CONSTRAINT bs_app_list_group_pk PRIMARY KEY (id)
);;
COMMENT ON TABLE bs_app_list_group IS '程式群組清單';
COMMENT ON COLUMN bs_app_list_group.id IS 'pk';
COMMENT ON COLUMN bs_app_list_group.version IS '資料版本';
COMMENT ON COLUMN bs_app_list_group.man_created IS '建檔人員';
COMMENT ON COLUMN bs_app_list_group.date_created IS '建檔時間';
COMMENT ON COLUMN bs_app_list_group.man_last_updated IS '最後異動人員';
COMMENT ON COLUMN bs_app_list_group.last_updated IS '最後異動時間';
COMMENT ON COLUMN bs_app_list_group.note IS '資料註記';
COMMENT ON COLUMN bs_app_list_group.app_no IS '程式代號:前面兩個英文字母加三個阿拉伯數字組成[EN][###]';
COMMENT ON COLUMN bs_app_list_group.app_group_no IS '程式群組代號';
CREATE UNIQUE INDEX bs_app_list_group_idx1 ON bs_app_list_group ( app_no, app_group_no );

CREATE TABLE bs_app_group (
    id NUMERIC(22,0) NOT NULL,
    version INTEGER,
    man_created VARCHAR(20) NOT NULL,
    date_created TIMESTAMP DEFAULT current_timestamp NOT NULL,
    man_last_updated VARCHAR(20),
    last_updated TIMESTAMP,
    note VARCHAR(50),
    app_group_no CHAR(10) NOT NULL,
    app_group_name VARCHAR(10) NOT NULL,
    CONSTRAINT bs_app_group_pk PRIMARY KEY (id)
);
COMMENT ON TABLE bs_app_group IS '程式群組';
COMMENT ON COLUMN bs_app_group.id IS 'pk';
COMMENT ON COLUMN bs_app_group.version IS '資料版本';
COMMENT ON COLUMN bs_app_group.man_created IS '建檔人員';
COMMENT ON COLUMN bs_app_group.date_created IS '建檔時間';
COMMENT ON COLUMN bs_app_group.man_last_updated IS '最後異動人員';
COMMENT ON COLUMN bs_app_group.last_updated IS '最後異動時間';
COMMENT ON COLUMN bs_app_group.note IS '資料註記';
COMMENT ON COLUMN bs_app_group.app_group_no IS '程式群組代號';
COMMENT ON COLUMN bs_app_group.app_group_name IS '程式群組名稱';

CREATE UNIQUE INDEX bs_app_group_idx1 ON bs_app_group ( app_group_no );

CREATE TABLE db_config (
    id bigint NOT NULL generated always as identity,
    version INTEGER,
    man_created VARCHAR(20) NOT NULL,
    date_created TIMESTAMP DEFAULT current_timestamp NOT NULL,
    man_last_updated VARCHAR(20),
    last_updated TIMESTAMP,
    name VARCHAR(20),
    type VARCHAR(20),
    host VARCHAR(20),
    port INTEGER,
    dbname VARCHAR(20),
    username VARCHAR(20),
    password VARCHAR(200),
    CONSTRAINT db_config_pk PRIMARY KEY (id)
);
COMMENT ON TABLE db_config IS '程式群組';
COMMENT ON COLUMN db_config.id IS 'pk';
COMMENT ON COLUMN db_config.version IS '資料版本';
COMMENT ON COLUMN db_config.man_created IS '建檔人員';
COMMENT ON COLUMN db_config.date_created IS '建檔時間';
COMMENT ON COLUMN db_config.man_last_updated IS '最後異動人員';
COMMENT ON COLUMN db_config.last_updated IS '最後異動時間';
COMMENT ON COLUMN db_config.name IS '資料庫連線名稱';
COMMENT ON COLUMN db_config.type IS '資料庫類型';
COMMENT ON COLUMN db_config.host IS '資料庫位址';
COMMENT ON COLUMN db_config.port IS '連線阜';
COMMENT ON COLUMN db_config.dbname IS '資料庫名稱';
COMMENT ON COLUMN db_config.username IS '使用者名稱';
COMMENT ON COLUMN db_config.password IS '使用者密碼';

CREATE TABLE sql_history (
      id bigint NOT NULL generated always as identity,
      version INTEGER,
      man_created VARCHAR(20) NOT NULL,
      date_created TIMESTAMP DEFAULT current_timestamp NOT NULL,
      man_last_updated VARCHAR(20),
      last_updated TIMESTAMP,
      db_config_id bigint,
      execute_type boolean,
      sql_content text,
      execute_message VARCHAR,
      CONSTRAINT sql_history_pk PRIMARY KEY (id)
);
COMMENT ON TABLE sql_history IS '程式群組';
COMMENT ON COLUMN sql_history.id IS 'pk';
COMMENT ON COLUMN sql_history.version IS '資料版本';
COMMENT ON COLUMN sql_history.man_created IS '建檔人員';
COMMENT ON COLUMN sql_history.date_created IS '建檔時間';
COMMENT ON COLUMN sql_history.man_last_updated IS '最後異動人員';
COMMENT ON COLUMN sql_history.last_updated IS '最後異動時間';
comment on column sql_history.db_config_id is '連接資料庫';
comment on column sql_history.execute_type is '執行狀況';
comment on column sql_history.sql_content is '執行SQL內容';
comment on column sql_history.execute_message is '執行後的訊息';