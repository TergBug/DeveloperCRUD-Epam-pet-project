insert into skills(id, name)
values (unhex(replace('e893a876-a583-42d4-99c1-a99ef99723db', '-', '')), 'Java'),
       (unhex(replace('77351156-9d20-405e-a0c5-24e17b4b0ad0', '-', '')), 'C#'),
       (unhex(replace('ad34f1da-43cd-4742-948c-f2e59a975839', '-', '')), 'JDBC'),
       (unhex(replace('a8f6eab7-f1c9-4e3a-85fb-e4a1e0504de3', '-', '')), 'JSON');
commit;
insert into accounts(id, name, status)
values (unhex(replace('77845a88-c171-4cd0-a73a-3045bdbe4f30', '-', '')), 'LiXiao', 'ACTIVE'),
       (unhex(replace('e86937cd-1535-4c0e-9026-e6ae27adc992', '-', '')), 'Din', 'DELETED'),
       (unhex(replace('9743c5ef-4e76-4fc1-9b1b-a88f352cb22e', '-', '')), 'Geek', 'BANNED'),
       (unhex(replace('effaa7cc-373e-47f8-8ff6-7d227f950cfe', '-', '')), 'Ford', 'ACTIVE');
commit;
insert into customers(id, name)
values (unhex(replace('2936f525-5b05-47e2-8c08-f681c9ab1139', '-', '')), 'Customer1'),
       (unhex(replace('08e5f3bd-4c50-41d2-b769-1d2af49adb63', '-', '')), 'Customer2'),
       (unhex(replace('3025faf1-dc64-40ae-840b-58d5f711eeb6', '-', '')), 'Customer3');
commit;
insert into projects(id, name, status, customer_id)
values (unhex(replace('e07748c4-88ba-4c69-a994-8bad8890b2b8', '-', '')), 'Dinot', 'DESIGN',
        unhex(replace('2936f525-5b05-47e2-8c08-f681c9ab1139', '-', ''))),
       (unhex(replace('febdbac8-af6a-4ee0-bbc1-c401497a53ef', '-', '')), 'Redf', 'IMPLEMENTATION',
        unhex(replace('3025faf1-dc64-40ae-840b-58d5f711eeb6', '-', ''))),
       (unhex(replace('958c58da-dfae-4fcc-85bd-8d4a7454cfb4', '-', '')), 'Pejh', 'TESTING',
        unhex(replace('08e5f3bd-4c50-41d2-b769-1d2af49adb63', '-', '')));
commit;
insert into developers(id, first_name, last_name, account_id, project_id)
values (unhex(replace('9a4f7c3f-fe35-4b45-82f1-128b65cb3a92', '-', '')), 'Din', 'Ford',
        unhex(replace('e86937cd-1535-4c0e-9026-e6ae27adc992', '-', '')),
        unhex(replace('e07748c4-88ba-4c69-a994-8bad8890b2b8', '-', ''))),
       (unhex(replace('cff6549f-b728-4ed5-857e-3027e7e6a370', '-', '')), 'Xiaoming', 'Li',
        unhex(replace('77845a88-c171-4cd0-a73a-3045bdbe4f30', '-', '')),
        unhex(replace('958c58da-dfae-4fcc-85bd-8d4a7454cfb4', '-', ''))),
       (unhex(replace('673c347a-ba46-4c5f-9f13-6d1a1aa29b81', '-', '')), 'Gird', 'Long',
        unhex(replace('9743c5ef-4e76-4fc1-9b1b-a88f352cb22e', '-', '')),
        unhex(replace('febdbac8-af6a-4ee0-bbc1-c401497a53ef', '-', ''))),
       (unhex(replace('6bc3db84-1930-438a-b230-63379ac93ded', '-', '')), 'Nord', 'Cir',
        null,
        null);
commit;
insert into developer_skill(developer_id, skill_id)
values (unhex(replace('9a4f7c3f-fe35-4b45-82f1-128b65cb3a92', '-', '')),
        unhex(replace('e893a876-a583-42d4-99c1-a99ef99723db', '-', ''))),
       (unhex(replace('9a4f7c3f-fe35-4b45-82f1-128b65cb3a92', '-', '')),
        unhex(replace('ad34f1da-43cd-4742-948c-f2e59a975839', '-', ''))),
       (unhex(replace('cff6549f-b728-4ed5-857e-3027e7e6a370', '-', '')),
        unhex(replace('77351156-9d20-405e-a0c5-24e17b4b0ad0', '-', ''))),
       (unhex(replace('673c347a-ba46-4c5f-9f13-6d1a1aa29b81', '-', '')),
        unhex(replace('e893a876-a583-42d4-99c1-a99ef99723db', '-', ''))),
       (unhex(replace('673c347a-ba46-4c5f-9f13-6d1a1aa29b81', '-', '')),
        unhex(replace('77351156-9d20-405e-a0c5-24e17b4b0ad0', '-', '')));
commit;