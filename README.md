# DBPollo

在資料庫裡的一隻雞

## 環境部屬

### 建立稽核資料庫

1. 登入資料庫

```bash
sudo -u postgres psql
```

2. 建立資料庫

```sql
-- 新增資料庫
CREATE DATABASE dbpollodb;
```

3. 建立DBA角色

```sql
-- 新增資料庫角色
CREATE ROLE dbadmin NOINHERIT;
-- 授予 dbadmin 角色可連線至 dbpollodb 資料庫
GRANT CONNECT ON DATABASE dbpollodb TO dbadmin;
-- 授予 dbadmin 在 DATABASE 所有權限
GRANT ALL ON DATABASE dbpollodb to dbadmin;
```

4. 新增AP專用帳號

```sql
-- 新增 dbpollo_ap 使用者
CREATE USER dbpollo_ap WITH PASSWORD 'ap-password';
-- 把 dbpollo_ap (user role) 加入 dict (group role) 中
GRANT dbpollo_ap TO dbadmin;
```

5. 新增人員操作帳號

```sql
-- 新增 dict_sa 使用者
CREATE USER dbpollo_user WITH PASSWORD 'user-password';

-- 把 dict_sa (user role) 加入 dict (group role) 中
GRANT dbpollo_user TO dbadmin;
```