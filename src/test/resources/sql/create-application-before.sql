delete from applications;

INSERT INTO applications(id, room_id, user_id, capacity, roomtype, checkin, checkout, booked, paid)
VALUES (1, 1, 1, 1, 'BUSINESS', '2021-07-31', '2021-08-01', false, false),
 (2, 2, 1, 2, 'ECONOM', '2021-07-31', '2021-08-01', false, false),
 (3, 3, 2, 3, 'LUXURY', '2021-07-31', '2021-08-01', false, false),
 (4, 4, 2, 1, 'LUXURY', '2021-07-31', '2021-08-01', false, false),
 (5, 5, 2, 2, 'BUSINESS', '2021-07-31', '2021-08-01', false, false);