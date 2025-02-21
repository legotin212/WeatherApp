
create table users(
                      ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      Login VARCHAR(256) NOT NULL,
                      Password TEXT NOT NULL
);

create table locations (
                           ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           Name VARCHAR,
                           UserId INT NOT NULL,
                           Latitude DECIMAL NOT NULL ,
                           Longitude DECIMAL NOT NULL,
                           CONSTRAINT fk_user FOREIGN KEY (USERID) references  Users(ID) ON DELETE CASCADE
);
create table sessions (
                          ID VARCHAR(256) PRIMARY KEY NOT NULL ,
                          UserId INT NOT NULL,
                          ExpiresAt TIMESTAMP NOT NULL,
                          CONSTRAINT fk_user FOREIGN KEY (USERID) references  Users(ID) ON DELETE CASCADE
);