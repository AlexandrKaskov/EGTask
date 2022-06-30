create database EGTask;
create user 'EGTaskUser' identified by 'EGTaskPassword';
GRANT ALL PRIVILEGES ON EGTask.* TO 'EGTaskUser'@'%';
FLUSH PRIVILEGES;

