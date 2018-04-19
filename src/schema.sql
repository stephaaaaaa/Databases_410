CREATE TABLE user (
    BroncoUserID INT NOT NULL PRIMARY KEY CHECK (BroncoUserID <= 999999999),
    BroncoPassword VARCHAR NOT NULL,
    SandboxUserID INT NOT NULL PRIMARY KEY,
    SandboxPassword VARCHAR NOT NULL,
    yourPortNumber INT NOT NULL -- not sure if this is an INT or not
);

CREATE TABLE task (
    task_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    task_label VARCHAR(1000) NOT NULL,
    is_cancelled BIT NOT NULL, -- 1 if cancelled, 0 if not cancelled
    is_complete BIT NOT NULL, -- 1 if is complete, 0 if not complete
    due_date DATE, --can be null, but can be added at a later time
    tag VARCHAR(500),

    time_stamp TIMESTAMP NOT NULL DEFAULT(TIMESTAMP()), --the time that a task was created
    BroncoUserID INT NOT NULL, --will join by BroncoUserID to grab only lists pertaining to a certain user
    next_id INT, --points to the next element in the list. Otherwise, we can sort in Java by DATE
    FOREIGN KEY (BroncoUserID) REFERENCES user(BroncoUserID),
    FOREIGN KEY (next_id) REFERENCES task(task_id),
);
