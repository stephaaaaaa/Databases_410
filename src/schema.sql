CREATE DATABASE IF NOT EXISTS todo;
DROP TABLE IF EXISTS task;
USE todo;

CREATE TABLE task (
    task_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    task_label VARCHAR(1000) NOT NULL,
    is_cancelled BIT NOT NULL DEFAULT(0), -- 1 if cancelled, 0 if not cancelled
    is_complete BIT NOT NULL DEFAULT(0), -- 1 if is complete, 0 if not complete
    due_date DATE, --can be null, but can be added at a later time
    tag VARCHAR(500),
    time_stamp TIMESTAMP NOT NULL DEFAULT(TIMESTAMP()), --the time that a task was created
);
