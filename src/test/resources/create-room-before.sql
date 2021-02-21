delete from rooms;

insert into rooms(id, capacity, price, status, roomtype, img_name)
VALUES (1, 1, 111, 'FREE', 'ECONOM', 'test1'),
       (2, 2, 222, 'FREE', 'ECONOM', 'test2'),
       (3, 2, 333, 'FREE', 'BUSINESS', 'test3'),
       (4, 3, 444, 'FREE', 'BUSINESS', 'test4'),
       (5, 3, 555, 'FREE', 'LUXURY', 'test5');
