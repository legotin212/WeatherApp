alter table locations
    add constraint unique_user_longitude_latitude unique (userid, longitude, latitude);